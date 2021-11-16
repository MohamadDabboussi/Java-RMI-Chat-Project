/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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

/**
 *
 * @author Mohamad
 */
@Entity
@Table(name = "Chat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c")
    , @NamedQuery(name = "Chat.findById", query = "SELECT c FROM Chat c WHERE c.id = :id")
    , @NamedQuery(name = "Chat.findByFrom", query = "SELECT c FROM Chat c WHERE c.from = :from")
    , @NamedQuery(name = "Chat.findByTo", query = "SELECT c FROM Chat c WHERE c.to = :to")
    , @NamedQuery(name = "Chat.findByMessage", query = "SELECT c FROM Chat c WHERE c.message = :message")
    , @NamedQuery(name = "Chat.findByUserGroup", query = "SELECT c FROM Chat c WHERE c.userGroup = :userGroup")
    , @NamedQuery(name = "Chat.findByTime", query = "SELECT c FROM Chat c WHERE c.time = :time")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    private String username;
    @Column(name = "from_")
    private Integer from;
    @Column(name = "to_")
    private Integer to;
    @Column(name = "message")
    private String message;
    @Column(name = "user_group")
    private Boolean userGroup;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Chat() {
    }
    public Chat(Integer id) {
        this.id = id;
    }
    public Chat(Integer id, String username, String message, Date time) {
        this.id = id;
        this.username = username;
        this.message=message;
        this.time=time;
    }
    public Chat(int from_, int to_, String message, boolean  user_group) {
        this.from=from_;
        this.to=to_;
        this.message=message;
        this.userGroup=user_group;
    }
    public Chat(Integer id, int from_, int to_, String message, boolean  user_group, Date time) {
        this.id = id;
        this.from=from_;
        this.to=to_;
        this.message=message;
        this.userGroup=user_group;
        this.time=time;
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

    public Integer getFrom() {
        return from;
    }
    public void setFrom(Integer from) {
        this.from = from;
    }
    
    public Integer getTo() {
        return to;
    }
    public void setTo(Integer to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getUserGroup() {
        return userGroup;
    }
    public void setUserGroup(Boolean userGroup) {
        this.userGroup = userGroup;
    }

    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
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
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "[ "+username+" = " + message + " ]";
    }
    
    public static ArrayList<Chat> ResultSetToChats(ResultSet rs) throws SQLException{
        ArrayList<Chat> chats = new ArrayList<>();
        int id;
        String username;
        String message;
        Date time;
        while (rs.next()) {
            id = rs.getInt("id");
            username = rs.getString("username");
            message = rs.getString("message");
            time=rs.getTime("time");
            Chat chat = new Chat(id, username, message, time);
            chats.add(chat);
        }
    return chats;
    }  
    public static Chat ResultSetToChat(ResultSet rs) throws SQLException{
        int id;
        String username;
        String message;
        Date time;
        rs.next();
        id = rs.getInt("id");
        username = rs.getString("username");
        message = rs.getString("message");
        time=rs.getTime("time");
        Chat chat = new Chat(id, username, message, time);  
    return chat;
    }  
    
    public static void CreateMessage(DataLayer dl, Chat c){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement;
                if(c.userGroup==false){
                statement=conn.prepareCall("{call create_message_user(?,?,?)}");
                }
                else{
                statement=conn.prepareCall("{call create_message_group(?,?,?)}");
                }
                statement.setInt(1,c.from);
                statement.setInt(2,c.to);
                statement.setString(3, c.message);
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                c.setId(r.getInt(1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void CreateMessage(DataLayer dl){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement;
                if(this.userGroup==false){
                statement=conn.prepareCall("{call create_message_user(?,?,?)}");
                }
                else{
                statement=conn.prepareCall("{call create_message_group(?,?,?)}");
                }
                statement.setInt(1,this.from);
                statement.setInt(2,this.to);
                statement.setString(3, this.message);
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                this.setId(r.getInt(1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void DeleteMessage(DataLayer dl){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call delete_message(?)}");
                statement.setInt(1,this.id);
                statement.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static ArrayList<Chat> ResultSetToChats2(ResultSet rs) throws SQLException{
        ArrayList<Chat> chats = new ArrayList<>();
        int id;
        int from;
        int to;
        String message;
        boolean user_group;
        Date time;
        while (rs.next()) {
        id = rs.getInt("id");
        from = rs.getInt("from_");
        to = rs.getInt("to_");
        message = rs.getString("message");
        user_group = rs.getBoolean("user_group");
        time=rs.getTime("time");
        Chat chat = new Chat(id, from, to, message, user_group, time);
            chats.add(chat);
        }
    return chats;
    }  
    public static Chat ResultSetToChat2(ResultSet rs) throws SQLException{
        int id;
        int from;
        int to;
        String message;
        boolean user_group;
        Date time;
        rs.next();
        id = rs.getInt("id");
        from = rs.getInt("from_");
        to = rs.getInt("to_");
        message = rs.getString("message");
        user_group = rs.getBoolean("user_group");
        time=rs.getTime("time");
        Chat chat = new Chat(id, from, to, message, user_group, time);
    return chat;
    }
    
}
