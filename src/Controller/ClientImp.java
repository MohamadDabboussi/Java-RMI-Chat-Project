/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ClientGui;
import View.MyJPanel;
import View.MyJPanel2;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import model.Chat;
import model.DataLayer;
import model.Group1;
import model.User;

/**
 *
 * @author Mohamad
 */
public class ClientImp extends UnicastRemoteObject implements IClient{
    ClientGui clientgui;
    public ClientImp(ClientGui cGui) throws RemoteException
    {
        this.clientgui=cGui;
    }
    
    @Override
    public void notifier(Object[] obj) throws RemoteException {
        // update chats on sidebar
        ArrayList<User> u=(ArrayList<User>) obj[0];
        ArrayList<Chat> c=(ArrayList<Chat>) obj[1];
        updateChats(u,c);
        //if user in u == clientgui.current chayuser (getchat)
    }
    
    @Override
    public void showChat(ArrayList<Chat> chats,String username) throws RemoteException {
        int i=0;
        int size=chats.size();
        clientgui.jPanel9.removeAll();
        clientgui.jPanel9.revalidate();
        clientgui.jPanel9.repaint();
        java.awt.Color color;
        int pos;
       while(i<8 && i<size){
           if(username.equals(chats.get(i).getUsername())){
           color=new java.awt.Color(77,155,103);
           pos=10;
           }
           else{
           color= new java.awt.Color(0,51,51);
           pos=100;
           }
           MyJPanel2 p=new MyJPanel2(chats.get(i),color);
           clientgui.jPanel9.add(p);
           p.setBounds(pos, 50+((6-i)*100), 380, 90);
           i++;
       }
    }
    
    @Override
    public void setLastSeen(String lastseen) throws RemoteException{
    clientgui.Llastseen.setText(lastseen);
    }
    
    @Override
    public void setCurrentChattedWith(int id_group,boolean user_or_group) throws RemoteException{
        clientgui.currentchatuser=id_group;
        clientgui.current_user_or_group=user_or_group;
        clientgui.serverRef.setUserChatOpen(clientgui.user.getId(),new Object[]{clientgui.currentchatuser, clientgui.current_user_or_group} );

    }
    
    @Override
    public void setCurrentGroupInfo(Group1 group) throws RemoteException{
        clientgui.PUsersChat1.setVisible(true);
             clientgui.LInfo.setVisible(true);
             if( clientgui.user.getId() == group.getAdmin()){
                 clientgui.LRemove.setVisible(true);
                 clientgui.LAdd.setVisible(true);
             }
         clientgui.LCurrentChatUsername.setText((group.getName()));
         setLastSeen("");
         String img=group.getImage();
        String search = ".png";
        int index = img.lastIndexOf(search);
        img = img.substring(0, index);
        img=img+"-small.png";
        clientgui.LCurrentChatImageUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Profile/small/"+img)));
        
    }
    
    @Override
    public void setCurrentChatInfo(User user) throws RemoteException{
        clientgui.PUsersChat1.setVisible(true);
        clientgui.LCurrentChatUsername.setText((user.getUsername()));
         // user
          if(clientgui.serverRef.UserIsOnline(user.getUsername())==false){
         clientgui.Llastseen.setText(user.getLastseen().toString());
          }
          else{setLastSeen("online");}
        String img=user.getImage();
        String search = ".png";
        int index = img.lastIndexOf(search);
        img = img.substring(0, index);
        img=img+"-small.png";
        clientgui.LCurrentChatImageUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Profile/small/"+img)));

    }
    
    public void updateChats(ArrayList<User> u,ArrayList<Chat> c) throws RemoteException{
        int i=0;
        clientgui.jPanel1.removeAll();
        clientgui.jPanel1.revalidate();
        clientgui.jPanel1.repaint();
        Collections.reverse(u);
        Collections.reverse(c);
       while(i<8 && i<u.size()){
           MyJPanel p=new MyJPanel(u.get(i), c.get(i));
           p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                 // show chat
                 try{
                 clientgui.PUsersChat1.setVisible(true);
                 clientgui.LRemove.setVisible(false);
                 clientgui.LAdd.setVisible(false);
                 clientgui.LInfo.setVisible(false);
                 MyJPanel panel= (MyJPanel) e.getSource();
                 clientgui.currentchatuser=panel.user.getId();
                 clientgui.current_user_or_group=panel.user_or_group;
                 clientgui.serverRef.getChat(clientgui.user,clientgui.currentchatuser,clientgui.current_user_or_group, clientgui.client);
                 Object user_group=clientgui.serverRef.getChatUser_Group(clientgui.currentchatuser,clientgui.current_user_or_group);
                 clientgui.serverRef.setUserChatOpen(clientgui.user.getId(),new Object[]{clientgui.currentchatuser, clientgui.current_user_or_group} );
                 if(clientgui.current_user_or_group==false){
                     // if ini list connect show online
                   User user=  (User) user_group;
                  setCurrentChatInfo(user);
                 //group
                 }else{
                     DataLayer dl=new DataLayer();
                     Group1 group=(Group1) user_group;
                     group.GetGroupDataByID(dl);
                     setCurrentGroupInfo(group);
                 }
                 } catch (RemoteException ex) {
                 Logger.getLogger(ClientGui.class.getName()).log(Level.SEVERE, null, ex);
             }
            }
            @Override
            public void mouseEntered(MouseEvent e){
            super.mouseEntered(e);
            MyJPanel panel= (MyJPanel) e.getSource();
            try{
            panel.setBackground(new java.awt.Color(77,155,103));
            clientgui.jPanel1.revalidate();
            clientgui.jPanel1.repaint();
            }catch(Exception ex){}
            }
            @Override
            public void mouseExited(MouseEvent e){
            super.mouseExited(e);
            MyJPanel panel= (MyJPanel) e.getSource();
                   panel.setBackground(new java.awt.Color(0,51,51));
            clientgui.jPanel1.revalidate();
            clientgui.jPanel1.repaint();
            }
         });
           clientgui.jPanel1.add(p, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, i*100, 510, 100));
           clientgui.jPanel1.revalidate();
           clientgui.jPanel1.repaint();
           i++;
       }
    }
    
    //view chat
    
}
