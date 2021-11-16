package View;
        
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Chat;
import model.DataLayer;
import model.User;

public class testgui {

    private static final int GAP = 5;

    private void displayGUI() {
        JFrame frame = new JFrame("Swing Worker Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DataLayer dl=new DataLayer();
        User u =new User(1);
        u.GetUserDataByID(dl);
        ArrayList<Chat> c=u.GetRecentChatMessages(dl, false, 4);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(0, 1, GAP, GAP));
        contentPane.setBorder(new EmptyBorder(GAP, GAP, GAP, GAP));
        System.out.println(c);
        contentPane.add(new MyJPanel2(c.get(0),new java.awt.Color(0,51,51)));
        contentPane.add(new MyJPanel2(c.get(1),new java.awt.Color(77,155,103)));
        contentPane.add(new MyJPanel2(c.get(2),new java.awt.Color(0,51,51)));

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new testgui().displayGUI();
            }
        };
        EventQueue.invokeLater(runnable);
    }
}