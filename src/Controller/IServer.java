/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author Mohamad
 */
public interface IServer extends Remote {

    public void reconnect(User u, IClient client) throws RemoteException;

    public void disconnect(User u) throws RemoteException;

    public void send(String msg, int from_id, int to_id, boolean user_group) throws RemoteException ;
    
    public ArrayList<User> listConnected() throws RemoteException;
    
    public ArrayList<User> getAllUsers() throws RemoteException;

    public ArrayList<User> GetAllUsersChattedWith(int id) throws RemoteException;
    
    public void getChat(User u1, int u2, boolean user_group, IClient client) throws RemoteException;

    public Object getChatUser_Group(int user_group_id, boolean user_group) throws RemoteException;

    public boolean UserIsOnline(String username) throws RemoteException;

    public IClient GetClient(String username) throws RemoteException;
    
    public void setUserChatOpen (Integer i, Object[] obj) throws  RemoteException;

    public void SearchUserGroup(User user, String name, IClient client) throws RemoteException;
    
    public void addNewChat(int from_id,String username) throws RemoteException;

public void Broadcast(String msg, int from_id,ArrayList<User> to_ids) throws RemoteException;

    public void CreateGroup(String name, int admin_id, String imageurl) throws RemoteException;
    
    public void RemoveClient(User admin,int id_group, int id_user2) throws RemoteException;
    
    public void AddClient(User admin,int id_group, int id_user2) throws RemoteException;
    
    public ArrayList<User> GetAllGroupUsers(int id_group) throws RemoteException;

    public boolean CheckUsernameTaken(String username) throws RemoteException;

    public void signUp(User u) throws RemoteException;

    public boolean signIn(User u) throws RemoteException;

}
