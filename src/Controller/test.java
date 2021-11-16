/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.objects.NativeArray;
import model.Chat;
import model.User;
import model.DataLayer;
import model.Group1;

/**
 *
 * @author Mohamad
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, RemoteException {
        // TODO code application logic here
        DataLayer dl=new DataLayer();
        ArrayList<User> users=User.GetUsersData(dl);
        User u=new User(20);
        System.out.println(users.contains(u));
//        ArrayList<User> u =User.GetOnlineUsers(dl);
//        System.out.println(u);
        
        
    }
    public static Object[] getRecentChats(User current,ArrayList<User> u){
        Chat c;
        ArrayList<Chat> chat=new  ArrayList<>();
        DataLayer dl=new DataLayer();
        for(int i=0;i<u.size();i++){
        c= current.GetRecentChatMessage(dl,u.get(i).getOnline(),u.get(i).getId());
        chat.add(c);
        }
        return new Object[]{u, chat};
    }
}
