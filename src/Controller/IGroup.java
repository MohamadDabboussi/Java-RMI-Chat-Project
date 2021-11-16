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
import model.User;

/**
 *
 * @author Mohamad
 */
public interface IGroup extends Remote{
    public void AddClient(int id_group,int id_user) throws RemoteException;
    public void RemoveClient(int id_group,int id_user) throws RemoteException;
    public ArrayList<User> GetAllGroupUsers(int id_group) throws RemoteException;
}