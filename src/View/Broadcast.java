/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.IClient;
import Controller.IServer;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author Mohamad
 */
public class Broadcast extends javax.swing.JFrame {

    /**
     * Creates new form Broadcast
     */
    IServer serverRef;
    ArrayList<User> users;
    int user_id;
    ArrayList<User> to_ids;

    public Broadcast() {
        initComponents();
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }

    public Broadcast(IServer server, int id_user, ArrayList<User> u) {
        initComponents();
        serverRef = server;
        user_id = id_user;
        users = u;
        to_ids=new ArrayList<>();
        DefaultComboBoxModel DM = new DefaultComboBoxModel(users.toArray());
        jComboBox1.setModel(DM);
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        BAdd = new javax.swing.JButton();
        TextMessage = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        LSend = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(20, 40, 40));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Gabriola", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Message :");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 230, 100, -1));

        jComboBox1.setFont(new java.awt.Font("Gabriola", 0, 30)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 290, 40));

        BAdd.setBackground(new java.awt.Color(161, 193, 193));
        BAdd.setFont(new java.awt.Font("Gabriola", 0, 24)); // NOI18N
        BAdd.setText("Add");
        BAdd.setBorder(null);
        BAdd.setBorderPainted(false);
        BAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BAddMouseClicked(evt);
            }
        });
        BAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAddActionPerformed(evt);
            }
        });
        jPanel1.add(BAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 100, 40));

        TextMessage.setBackground(new java.awt.Color(20, 40, 40));
        TextMessage.setFont(new java.awt.Font("Harlow Solid Italic", 0, 24)); // NOI18N
        TextMessage.setForeground(new java.awt.Color(255, 255, 255));
        TextMessage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TextMessage.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(161, 193, 193)));
        TextMessage.setOpaque(false);
        jPanel1.add(TextMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 360, 50));

        jLabel3.setFont(new java.awt.Font("Gabriola", 0, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Broadcast");
        jLabel3.setToolTipText("");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 160, -1));

        LSend.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Resources/icons8-email-send-96.png"))); // NOI18N
        LSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LSendMouseClicked(evt);
            }
        });
        jPanel1.add(LSend, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, -1, -1));

        jLabel4.setFont(new java.awt.Font("Gabriola", 0, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("name :");
        jLabel4.setToolTipText("");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 100, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BAddMouseClicked
        // TODO add your handling code here:
        User u = (User) jComboBox1.getSelectedItem();
            if (to_ids.contains(u) == false) {
                to_ids.add(u);
                jComboBox1.removeItemAt(jComboBox1.getSelectedIndex());
            }
    }//GEN-LAST:event_BAddMouseClicked

    private void BAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAddActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_BAddActionPerformed

    private void LSendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LSendMouseClicked
        // TODO add your handling code here:
        try {
            if (to_ids != null) {
                    serverRef.Broadcast(TextMessage.getText(), user_id,to_ids);
                TextMessage.setText("");
                to_ids = null;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(CreateGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_LSendMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Broadcast.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Broadcast.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Broadcast.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Broadcast.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Broadcast().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BAdd;
    public javax.swing.JLabel LSend;
    private javax.swing.JTextField TextMessage;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}