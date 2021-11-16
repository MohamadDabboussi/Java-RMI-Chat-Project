/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Chat;
import model.Group1;
import model.User;

/**
 *
 * @author Mohamad
 */
public interface IClient extends Remote{
    public void notifier(Object[] obj) throws RemoteException;
    public void showChat(ArrayList<Chat> chats,String username) throws RemoteException;
    public void setLastSeen(String lastseen) throws RemoteException;
    public void setCurrentChatInfo(User user) throws RemoteException;
    public void setCurrentGroupInfo(Group1 group) throws RemoteException;
    public void setCurrentChattedWith(int id_group,boolean user_or_group) throws RemoteException;
}
