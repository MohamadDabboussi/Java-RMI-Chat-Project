/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ClientGui;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import model.DataLayer;
import model.Group1;
import model.User;

/**
 *
 * @author Mohamad
 */

public class GroupImp extends UnicastRemoteObject implements IGroup{
    
    ClientGui clientGui;
    
    public GroupImp() throws RemoteException {
   
    }
    public GroupImp(ClientGui cl) throws RemoteException {
     clientGui=cl;
    }

    @Override
    public void AddClient(int id_group, int id_user) throws RemoteException {
        clientGui.serverRef.AddClient(clientGui.user,id_group, id_user);
    }

    @Override
    public void RemoveClient(int id_group, int id_user) throws RemoteException {
        clientGui.serverRef.RemoveClient(clientGui.user,id_group, id_user);
    }

    @Override
    public ArrayList<User> GetAllGroupUsers(int id_group) throws RemoteException {
       return clientGui.serverRef.GetAllGroupUsers(id_group);
    }
    
}
