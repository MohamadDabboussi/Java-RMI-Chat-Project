/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import javafx.scene.layout.Border;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Chat;
import model.User;

/**
 *
 * @author Mohamad
 */
public class MyJPanel extends  JPanel{
    public User user;
    public boolean user_or_group;// false=>user   true=>group
    
    public MyJPanel(User u,Chat c){ //boolean if user or group
     this.setBackground(new java.awt.Color(0, 51, 51));
     this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
     this.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
       
     user=u;
     user_or_group=u.getOnline();
     
     JLabel LUsername = new javax.swing.JLabel();
     LUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
     LUsername.setForeground(new java.awt.Color(255, 255, 255));
     LUsername.setText(u.getUsername());
     this.add(LUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 14, 220, 24));
     
     JLabel LTimeUser = new javax.swing.JLabel();
     LTimeUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
     LTimeUser.setForeground(new java.awt.Color(255, 255, 255));
     //format time
     LTimeUser.setText(c.getTime().toString());
     this.add(LTimeUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 60, -1));
     
     JLabel LMessageUser = new javax.swing.JLabel();
     LMessageUser.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
     LMessageUser.setForeground(new java.awt.Color(255, 255, 255));
     LMessageUser.setText(c.getUsername()+": "+c.getMessage());
     this.add(LMessageUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 45, 381, 47));
     
     
     JLabel LImageUser = new javax.swing.JLabel();
     LImageUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
     String img=u.getImage();
     String search = ".png";
     int index = img.lastIndexOf(search);
     img = img.substring(0, index);
     img=img+"-small.png";
     LImageUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Profile/small/"+img))); // NOI18N
     LImageUser.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(20, 40, 40), 5, true));
     this.add(LImageUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 14, -1, -1));
     
    }
}
