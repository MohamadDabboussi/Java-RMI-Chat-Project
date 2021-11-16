/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Controller.ClientImp;
import Controller.IClient;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.internal.jpa.parsing.TemporalLiteralNode;

/**
 *
 * @author Mohamad
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByLastseen", query = "SELECT u FROM User u WHERE u.lastseen = :lastseen")
    , @NamedQuery(name = "User.findByAbout", query = "SELECT u FROM User u WHERE u.about = :about")
    , @NamedQuery(name = "User.findByImage", query = "SELECT u FROM User u WHERE u.image = :image")
    , @NamedQuery(name = "User.findByOnline", query = "SELECT u FROM User u WHERE u.online = :online")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "lastseen")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastseen;
    @Column(name = "about")
    private String about;
    @Column(name = "image")
    private String image;
    @Column(name = "online")
    private Boolean online;
    
    
    public User() { 
    }
    public User(Integer id) {
        this.id = id;
    }
    public User(String username, String password) {
        this.username= username;
        this.password = password;
    }
    public User(Integer id, String username, String password) {
        this.id = id;
        this.username= username;
        this.password = password;
    }
    public User(Integer id, String username, String image, boolean online) {
        this.id = id;
        this.username= username;
        this.image=image;
        this.online=online;
    }
    public User(String username,String password, String about, String image) {
        this.username = username;
        this.password=password;
        this.image = image;
        this.about = about;
    }
    public User(Integer id, String username, Date lastseen, String about, String image, boolean  online) {
        this.id = id;
        this.username = username;
        this.lastseen = lastseen;
        this.image = image;
        this.about = about;
        this.online = online;
    }
    public User(User u) {
        this.id = u.id;
        this.username = u.username;
        this.password = u.password;
        this.lastseen = u.lastseen;
        this.image = u.image;
        this.about = u.about;
        this.online = u.online;
    }
    
    ///////////////////////////////////////// Getter and Setter ////////////////////////////////////////////////////////
    
    public void setUserData(DataLayer dl){
        User u=User.GetUserDataByID(dl,this.id);
        this.id = u.id;
        this.username = u.username;
        this.lastseen = u.lastseen;
        this.image = u.image;
        this.about = u.about;
        this.online = u.online;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastseen() {
        return lastseen;
    }
    public void setLastseen(Date lastseen) {
        this.lastseen = lastseen;
    }

    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getOnline() {
        return online;
    }
    public void setOnline(Boolean online) {
        this.online = online;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return username;
    }
    
    
    /////////////////////////////////////    Functions    /////////////////////////////////////////////////////
    
    public static ArrayList<User> ResultSetToUsers(ResultSet rs) throws SQLException{
        ArrayList<User> users = new ArrayList<>();
        int id;
        String username;
        String imageurl;
        String about;
        boolean online;
        Date last_seen;
        while (rs.next()) {
            id = rs.getInt("id");
            username = rs.getString("username");
            try {
                imageurl = rs.getString("image");
            } catch (Exception e) {
                imageurl = "none.png";
            }
            try {
                about = rs.getString("about");
            } catch (Exception e) {
                about = "available";
            }
            last_seen = rs.getTime("lastseen");
            online=rs.getBoolean("online");
            User user = new User(id, username, last_seen, about, imageurl, online);
            users.add(user);
        }
    return users;
    }  
    public static User ResultSetToUser(ResultSet rs) throws SQLException{
        int id;
        String username;
        String imageurl;
        String about;
        boolean online;
        Date last_seen;
        rs.next();
        id = rs.getInt("id");
        username = rs.getString("username");
        try {
            imageurl = rs.getString("image");
        } catch (Exception e) {
            imageurl = "none.png";
        }
        try {
            about = rs.getString("about");
        } catch (Exception e) {
            about = "available";
        }
        last_seen = rs.getTime("lastseen");
        online=rs.getBoolean("online");
        User user = new User(id, username, last_seen, about, imageurl, online);
    return user;
    }
    
    public static ArrayList<User> ResultSetToUsersGroups(ResultSet rs) throws SQLException{
        ArrayList<User> users = new ArrayList<>();
        int id;
        String username;
        String imageurl;
        boolean user_group;
        while (rs.next()) {
            id = rs.getInt("id");
            username = rs.getString("username");
            try {
                imageurl = rs.getString("image");
            } catch (Exception e) {
                imageurl = "none.png";
            }
            user_group=rs.getBoolean("user_group");
            User user = new User(id, username,imageurl,user_group);
            users.add(user);
        }
    return users;
    }  
    
    
    public static void Create(DataLayer dl, User u){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call create_user(?,?,?,?)}");
                statement.setString(1,u.username);
                statement.setString(2,u.password);
                if(u.about==null){statement.setString(3,"available");}
                else{statement.setString(3,u.about);}
                if(u.image==null){statement.setString(4,"none.png");}
                else{statement.setString(4,u.image);}
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                u.setId(r.getInt(1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void Create(DataLayer dl){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call create_user(?,?,?,?)}");
                statement.setString(1,this.username);
                statement.setString(2,this.password);
                if(this.about==null){statement.setString(3,"available");}
                else{statement.setString(3,this.about);}
                if(this.image==null){statement.setString(4,"none.png");}
                else{statement.setString(4,this.image);}
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                this.setId(r.getInt(1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static ArrayList<User> GetUsersData(DataLayer dl) {
        ArrayList<User> users = new ArrayList<>();
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_all_users}");
                statement.execute();
                users=ResultSetToUsers(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    } 
    
    public ArrayList<User> GetUsersChattedWith(DataLayer dl) {
        ArrayList<User> users = new ArrayList<>();
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_recent_user(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                users=ResultSetToUsers(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    } 
     
    public static ArrayList<User> GetUsersChattedWith(DataLayer dl,int id) {
        ArrayList<User> users = new ArrayList<>();
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_recent_user(?)}");
                statement.setInt(1,id);
                statement.execute();
                users=ResultSetToUsers(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    } 
        
    public static User GetUserDataByID(DataLayer dl,int id){
        User user=new User();
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_user(?)}");
                statement.setInt(1,id);
                statement.execute();
                user=ResultSetToUser(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return user;
    }
    public void GetUserDataByID(DataLayer dl){
     User user=new User();
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_user(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                user=ResultSetToUser(statement.getResultSet());
                this.username=user.username;
                this.lastseen=user.lastseen;
                this.image=user.image;
                this.about=user.about;
                this.online=user.online;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static User GetUserByLogin(DataLayer dl, String username, String password){
        User user=new User();
           if ((dl.IsValid)) {
               try (Connection conn = DriverManager.getConnection(dl.url)) {
                   CallableStatement statement=conn.prepareCall("{call get_user_login(?,?)}");
                   statement.setString(1,username);
                   statement.setString(2,password);
                   statement.execute();
                   user=ResultSetToUser(statement.getResultSet());
               } catch (Exception e) {
                   System.out.println(e.getMessage());
               }
           }
        return user;
    }
    public ArrayList<Group1> GetUserGroups(DataLayer dl) {
        ArrayList<Group1> groups = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_user_groups(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                groups=Group1.ResultSetToGroups(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return groups;
    }
    public ArrayList<User> GetRecentUserGroup(DataLayer dl) {
        ArrayList<User> users = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_recent(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                users=ResultSetToUsersGroups(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    }
    public ArrayList<User> SearchUserGroup(DataLayer dl,String name) {
        ArrayList<User> users = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call search_user_group(?,?)}");
                statement.setInt(1,this.id);
                statement.setString(2,name);
                statement.execute();
                users=ResultSetToUsersGroups(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    }
    public static ArrayList<User> GetOnlineUsers(DataLayer dl) {
        ArrayList<User> users = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_online_users}");
                statement.execute();
                users=ResultSetToUsers(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    }
    public ArrayList<Group1> GetRecentGroupsChattedOn(DataLayer dl) {
        ArrayList<Group1> groups = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_recent_group(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                groups=Group1.ResultSetToGroups(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return groups;
    }
    /*
        user_group variable indicates if the chat is between 2 users or between
        multiple users in a group.
        user_group = false => chat between 2 users
        user_group = true => group chat
    */
    public ArrayList<Chat> GetRecentChatMessages(DataLayer dl,boolean  user_group,int user_group_id) {  
        ArrayList<Chat> chats = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement;
                if (user_group == false){
                // Get chat between 2 users
                statement=conn.prepareCall("{call get_20_chat_messages(?,?)}");
                statement.setInt(1,this.id);
                statement.setInt(2,user_group_id);
                }
                else{
                //Get group chat
                statement=conn.prepareCall("{call get_20_group_chat_messages(?)}");
                statement.setInt(1,user_group_id);
                }
                statement.execute();
                chats=Chat.ResultSetToChats(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return chats;
    }   
    public Chat GetRecentChatMessage(DataLayer dl,boolean  user_group,int user_group_id) {  
        Chat chat = new Chat();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement;
                if (user_group == false){
                // Get chat between 2 users
                statement=conn.prepareCall("{call get_last_chat_message(?,?)}");
                statement.setInt(1,this.id);
                statement.setInt(2,user_group_id);
                }
                else{
                //Get group chat
                statement=conn.prepareCall("{call get_last_group_chat_message(?)}");
                statement.setInt(1,user_group_id);
                }
                statement.execute();
                chat=Chat.ResultSetToChat(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return chat;
    }
    
    public ArrayList<Chat> GetAllChatMessages(DataLayer dl,boolean  user_group,int user_group_id) {  
        ArrayList<Chat> chats = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement;
                if (user_group == false){
                // Get chat between 2 users
                statement=conn.prepareCall("{call get_all_chat_messages(?,?)}");
                statement.setInt(1,this.id);
                statement.setInt(2,user_group_id);
                }
                else{
                //Get group chat
                statement=conn.prepareCall("{call get_all_group_chat_messages(?)}");
                statement.setInt(1,user_group_id);
                }
                statement.execute();
                chats=Chat.ResultSetToChats(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return chats;
    }
    
    
    public static Boolean CheckUser(DataLayer dl, String username, String password){
    boolean present=false;
    if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call check_user(?,?)}");
                statement.setString(1,username);
                statement.setString(2,password);
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                int i=r.getInt(1);
                if (i>0) {present =true;}
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    return  present;
    }
    public static int CheckUser(DataLayer dl, String username){
    int id=0;
    if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_user_id(?)}");
                statement.setString(1,username);
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                id=r.getInt("id");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    return  id;
    }
    
    public static int CheckUsernameTaken(DataLayer dl, String username){
    int count =0;
    if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call check_user_by_name(?)}");
                statement.setString(1,username);
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                count=r.getInt("nb");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    return  count;
    }
    
    public static boolean CheckUserOnline(DataLayer dl, int user_id){
    boolean present=false;
    if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call check_online(?)}");
                statement.setInt(1,user_id);
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                int i=r.getInt(1);
                if (i>0) {present =true;}
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    return present;
    }
    
    public void Update_online(DataLayer dl){
    if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call update_login(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                this.setOnline(true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void Update_lastseen(DataLayer dl){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call update_logout(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                this.setOnline(false);
                Date date = new Date();
                this.setLastseen(date);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void Update_image(DataLayer dl, String imageurl){
    if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call update_user_image(?,?)}");
                statement.setInt(1,this.id);
                statement.setString(2,imageurl);
                statement.execute();
                this.setImage(imageurl);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void Update_about(DataLayer dl, String about){
    if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call update_user_about(?,?)}");
                statement.setInt(1,this.id);
                statement.setString(2,about);
                statement.execute();
                this.setAbout(about);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
