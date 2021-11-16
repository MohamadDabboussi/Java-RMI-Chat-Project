package Controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chat;
import model.DataLayer;
import model.Group1;
import model.User;

public class ServerImp extends UnicastRemoteObject implements IServer {

    HashMap<String, IClient> listConnected = new HashMap();
    HashMap<Integer, Object[]> user_chatopen = new HashMap();

    public ServerImp() throws RemoteException {

    }

    @Override
    public void reconnect(User u, IClient client) throws RemoteException {
        DataLayer dl = new DataLayer();
        try {
            // make user online
            u.Update_online(dl);
            listConnected.put(u.getUsername(), client);
            System.out.println(u.getUsername() + " is now online");
            // notifier client
            //update chats
            ArrayList<User> users_groups = u.GetRecentUserGroup(dl);
            Object[] obj = getRecentChats(u, users_groups);
            client.notifier(obj);

            for (HashMap.Entry<Integer, Object[]> entry : user_chatopen.entrySet()) {
                int userchatwith = (int) entry.getValue()[0];
                boolean user__or__group = (boolean) entry.getValue()[1];
                if (userchatwith == u.getId() && user__or__group == false) {
                    // make it appear offline
                    User user = new User(entry.getKey());
                    user.GetUserDataByID(dl);
                    IClient cl = listConnected.get(user.getUsername());
                    cl.setLastSeen("online");
                }
            }

        } catch (Exception e) {
            System.out.println("Error while reconnecting");
        }
    }

    @Override
    public void disconnect(User user) throws RemoteException {
        // make user offline and update last seen
        DataLayer dl = new DataLayer();
        user.Update_lastseen(dl);
        listConnected.remove(user.getUsername());
        System.out.println(user.getUsername() + " is now offline");
        try {
            for (HashMap.Entry<Integer, Object[]> entry : user_chatopen.entrySet()) {
                int userchatwith = (int) entry.getValue()[0];
                boolean user__or__group = (boolean) entry.getValue()[1];
                if (userchatwith == user.getId() && user__or__group == false) {
                    // make it appear offline
                    User u = new User(entry.getKey());
                    u.GetUserDataByID(dl);
                    IClient cl = listConnected.get(u.getUsername());
                    cl.setLastSeen(user.getLastseen().toString());
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void getChat(User u1, int u2, boolean user_group, IClient client) throws RemoteException {
        DataLayer dl = new DataLayer();
        ArrayList<Chat> chats = u1.GetRecentChatMessages(dl, user_group, u2);
        try {
                client.showChat(chats, u1.getUsername());
        } catch (Exception e) {
        }
    }

    @Override
    public Object getChatUser_Group(int user_group_id, boolean user_group) throws RemoteException {
        DataLayer dl = new DataLayer();
        Object u;
        if (user_group == false) {
            u = User.GetUserDataByID(dl, user_group_id);
        } else {
            u = Group1.GetGroupDataByID(dl, user_group_id);
        }
        return u;
    }

    @Override
    public boolean UserIsOnline(String username) throws RemoteException {
        return listConnected.get(username) != null;
    }

    @Override
    public IClient GetClient(String username) throws RemoteException {
        return listConnected.get(username);
    }

    @Override
    public void send(String msg, int from_id, int to_id, boolean user_group) throws RemoteException {
        //get if client online
        ArrayList<User> users_groups;
        Object[] obj;
        User user2;
        DataLayer dl = new DataLayer();
        User user1 = new User(from_id);
        user1.GetUserDataByID(dl);
        // create message in database
        Chat c = new Chat(from_id, to_id, msg, user_group);
        c.CreateMessage(dl);
        //update chats
        IClient client= listConnected.get(user1.getUsername());
        users_groups = user1.GetRecentUserGroup(dl);
        obj = getRecentChats(user1, users_groups);
        getChat(user1, to_id, user_group, client);
        client.notifier(obj);
        if (user_group == false) {
            user2 = new User(to_id);
            user2.GetUserDataByID(dl);
            IClient cl = listConnected.get(user2.getUsername());
            if (cl != null) {
                // client online
                users_groups = user2.GetRecentUserGroup(dl);
                obj = getRecentChats(user2, users_groups);
                //if other user on same chat get chat
                if (user_chatopen.get(user2.getId()) != null) {
                    int userchatwith = (int) user_chatopen.get(user2.getId())[0];
                    boolean user__or__group = (boolean) user_chatopen.get(user2.getId())[1];
                    if (userchatwith == user1.getId() && user__or__group == false) {
                        getChat(user2, from_id, user_group, cl);
                    }
                }
                cl.notifier(obj);
            }
        } else {
            // notifier clients in this group
            ArrayList<User> users = Group1.GetGroupMembers(dl, to_id);
            for (int i = 0; i < users.size(); i++) {
                user2 = users.get(i);
                user2.GetUserDataByID(dl);
                IClient cl = listConnected.get(user2.getUsername());
                if (cl != null) {
                    // client online
                    users_groups = user2.GetRecentUserGroup(dl);
                    obj = getRecentChats(user2, users_groups);
                    //if other user on same chat get chat
                    if (user_chatopen.get(user2.getId()) != null) {
                        int userchatwith = (int) user_chatopen.get(user2.getId())[0];
                        boolean user__or__group = (boolean) user_chatopen.get(user2.getId())[1];
                        if (userchatwith == to_id && user__or__group == true) {
                            getChat(user2, to_id, user_group, cl);
                        }
                    }
                    cl.notifier(obj);
                }
            }
        }
    }

    @Override
    public void SearchUserGroup(User user, String name, IClient client) throws RemoteException {
        DataLayer dl = new DataLayer();
        ArrayList<User> users_groups;
        if (name.equals("")) {
            users_groups = user.GetRecentUserGroup(dl);
        } else {
            users_groups = user.SearchUserGroup(dl, name);
        }
        Object[] obj = getRecentChats(user, users_groups);
        client.notifier(obj);
    }

    @Override
    public void addNewChat(int from_id, String username) throws RemoteException {
        DataLayer dl = new DataLayer();
        int id = User.CheckUser(dl, username);
        if (id != 0) {
            User u = new User(from_id);
            u.GetUserDataByID(dl);
            IClient client = listConnected.get(u.getUsername());
            send(u.getUsername() + " added you to his chat list", from_id, id, false);
            User u2 = new User(id);
            u2.GetUserDataByID(dl);
            client.setCurrentChatInfo(u2);
        }
    }

    @Override
    public void Broadcast(String msg, int from_id,ArrayList<User> to_ids) throws RemoteException {
        DataLayer dl = new DataLayer();
        User u=new User(from_id);
        u.GetUserDataByID(dl);
        IClient client = listConnected.get(u.getUsername());
        for (int i = 0; i < to_ids.size(); i++) {
            send(msg, from_id, to_ids.get(i).getId(), false);
            client.setCurrentChatInfo(to_ids.get(i));
        }
    }

    @Override
    public ArrayList<User> listConnected() throws RemoteException {
        // get online users and return a=users array
        DataLayer dl = new DataLayer();
        return User.GetOnlineUsers(dl);
    }
    
    @Override
    public ArrayList<User> getAllUsers() throws RemoteException{
        DataLayer dl=new DataLayer();
        return User.GetUsersData(dl);
    }

    @Override
    public void CreateGroup(String name, int admin_id, String imageurl) throws RemoteException {
        DataLayer dl = new DataLayer();
        Group1 g = new Group1(name, admin_id, imageurl);
        g.Create(dl);
        g.GetGroupDataByID(dl);
        Group1.add(dl, g.getId(),admin_id);
        User admin=new User(admin_id);
        admin.GetUserDataByID(dl);
        IClient client=listConnected.get(admin.getUsername());
        client.setCurrentChattedWith(g.getId(),true);
        send(admin.getUsername()+" created the group", admin.getId(), g.getId(), true);
        client.setCurrentGroupInfo(g);
        System.out.println("User: "+admin.getUsername()+" has succefully created group: "+g.getName());
    }
    
    @Override
    public void AddClient(User admin,int id_group, int id_user2) throws RemoteException { 
        DataLayer dl=new DataLayer();
        Group1.add(dl, id_group,id_user2);
        User u=new User(id_user2);
        u.GetUserDataByID(dl);
        send(admin.getUsername()+" added "+u.getUsername()+" to the group", admin.getId(), id_group, true);
    }

    @Override
    public void RemoveClient(User admin,int id_group, int id_user2) throws RemoteException {
        DataLayer dl=new DataLayer();
        Group1.remove(dl, id_group,id_user2);
        User u=new User(id_user2);
        u.GetUserDataByID(dl);
        send(admin.getUsername()+" removed "+u.getUsername()+" from the group", admin.getId(), id_group, true);
    }

    @Override
    public ArrayList<User> GetAllGroupUsers(int id_group) throws RemoteException {
        DataLayer dl=new DataLayer();
        return  Group1.GetGroupMembers(dl, id_group);
    }
    
    @Override
    public ArrayList<User> GetAllUsersChattedWith(int id) throws RemoteException {
        DataLayer dl=new DataLayer();
        return  User.GetUsersChattedWith(dl,id);
    }

    @Override
    public boolean CheckUsernameTaken(String username) throws RemoteException {
        DataLayer dl = new DataLayer();
        int count = User.CheckUsernameTaken(dl, username);
        return count > 0;
    }

    @Override
    public void signUp(User u) throws RemoteException {
        DataLayer dl = new DataLayer();
        u.Create(dl);
    }

    @Override
    public boolean signIn(User u) throws RemoteException {
        DataLayer dl = new DataLayer();
        boolean correct;
        correct = User.CheckUser(dl, u.getUsername(), u.getPassword());
        if (!correct){
           System.out.println("failed login attempt");
        }
        return correct;
    }

    public void setUserChatOpen(Integer i, Object[] obj) {
        user_chatopen.put(i, obj);
    }

    public static Object[] getRecentChats(User current, ArrayList<User> u) {
        Chat c;
        ArrayList<Chat> chat = new ArrayList<>();
        DataLayer dl = new DataLayer();
        for (int i = 0; i < u.size(); i++) {
            c = current.GetRecentChatMessage(dl, u.get(i).getOnline(), u.get(i).getId());
            chat.add(c);
            System.out.println(c);
        }
        return new Object[]{u, chat};
    }

}
