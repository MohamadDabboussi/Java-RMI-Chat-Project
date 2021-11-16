/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Chat;

/**
 *
 * @author Mohamad
 */
public class MyJPanel2 extends RoundedPanel{
    public MyJPanel2(Chat c, java.awt.Color color) {
    super(20);
    this.setBackground(color);
    this.setForeground(new java.awt.Color(255, 255, 255));
    this.setOpaque(false);
    this.setLayout(null);
    
    JLabel LUsername = new javax.swing.JLabel();
    LUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    LUsername.setForeground(new java.awt.Color(255, 255, 255));
    LUsername.setText(c.getUsername());
    this.add(LUsername);
    LUsername.setBounds(20, 10, 200, 20);

    
    JLabel jLabelMessage = new javax.swing.JLabel();
    jLabelMessage.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
    jLabelMessage.setForeground(new java.awt.Color(255, 255, 255));
    jLabelMessage.setText(c.getMessage());
    this.add(jLabelMessage);
    jLabelMessage.setBounds(20, 30, 320, 40);
    
    JLabel LTimeUser = new javax.swing.JLabel();
    LTimeUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    LTimeUser.setForeground(new java.awt.Color(255, 255, 255));
    LTimeUser.setText(c.getTime().toString());
    this.add(LTimeUser);
    LTimeUser.setBounds(320, 70, 60, 20);
    
    }
}
