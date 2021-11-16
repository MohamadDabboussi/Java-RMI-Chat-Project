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
import javax.xml.bind.annotation.XmlRootElement;
import static model.User.ResultSetToUser;
import static model.User.ResultSetToUsers;

/**
 *
 * @author Mohamad
 */
@Entity
@Table(name = "Group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Group1.findAll", query = "SELECT g FROM Group1 g")
    , @NamedQuery(name = "Group1.findById", query = "SELECT g FROM Group1 g WHERE g.id = :id")
    , @NamedQuery(name = "Group1.findByName", query = "SELECT g FROM Group1 g WHERE g.name = :name")
    , @NamedQuery(name = "Group1.findByAdmin", query = "SELECT g FROM Group1 g WHERE g.admin = :admin")
    , @NamedQuery(name = "Group1.findByImage", query = "SELECT g FROM Group1 g WHERE g.image = :image")})
public class Group1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "admin")
    private int admin;
    @Column(name = "image")
    private String image;

    public Group1() {
    }
    public Group1(Integer id) {
        this.id = id;
    }
    public Group1(Integer id, String name, int admin) {
        this.id = id;
        this.name = name;
        this.admin = admin;
    }
    public Group1(String name, int admin, String imageurl) {
        this.name = name;
        this.admin = admin;
        this.image = imageurl;
    }
    public Group1(Integer id, String name, int admin, String imageurl) {
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.image = imageurl;
    }
    public Group1(Group1 group) {
        this.id = group.id;
        this.name = group.name;
        this.admin = group.admin;
        this.image = group.image;
    }
    
    public void setGroupData(DataLayer dl){
        Group1 group=Group1.GetGroupDataByID(dl,this.id);
        this.id = group.id;
        this.name = group.name;
        this.admin = group.admin;
        this.image = group.image;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAdmin() {
        return admin;
    }
    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
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
        if (!(object instanceof Group1)) {
            return false;
        }
        Group1 other = (Group1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "[ name=" + name + " ]";
    }
    
    /////////////////////////////////////////    Functions      ////////////////////////////////////////////////////
    public static ArrayList<Group1> ResultSetToGroups(ResultSet rs) throws SQLException{
        ArrayList<Group1> groups = new ArrayList<>();
        int id;
        String name;
        int admin;
        String imageurl;
        while (rs.next()) {
            id = rs.getInt("id");
            name = rs.getString("name");
            admin = rs.getInt("admin");
            try {
                imageurl = rs.getString("image");
            } catch (Exception e) {
                imageurl = "none.png";
            }
            Group1 group = new Group1(id, name, admin, imageurl);
            groups.add(group);
        }
    return groups;
    }  
    public static Group1 ResultSetToGroup(ResultSet rs) throws SQLException{
        int id;
        String name;
        int admin;
        String imageurl;
        rs.next();
        id = rs.getInt("id");
        name = rs.getString("name");
        admin = rs.getInt("admin");
        try {
            imageurl = rs.getString("image");
        } catch (Exception e) {
            imageurl = "none.png";
        }
        Group1 group = new Group1(id, name, admin, imageurl);
    return group;
    }
    
    public static void Create(DataLayer dl, Group1 group){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call create_group(?,?,?)}");
                statement.setString(1,group.name);
                statement.setInt(2,group.admin);
                if(group.image==null){statement.setString(3,"none.png");}
                else{statement.setString(3,group.image);}
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                group.setId(r.getInt(1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void Create(DataLayer dl){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call create_group(?,?,?)}");
                statement.setString(1,this.name);
                statement.setInt(2,this.admin);
                if(this.image==null){statement.setString(3,"none.png");}
                else{statement.setString(3,this.image);}
                statement.execute();
                ResultSet r=statement.getResultSet();
                r.next();
                this.setId(r.getInt(1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void add(DataLayer dl,int id_group,int id_user){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call join_group(?,?)}");
                statement.setInt(1,id_group);
                statement.setInt(2,id_user);
                statement.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void remove(DataLayer dl,int id_group,int id_user){
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call delete_from_group(?,?)}");
                statement.setInt(1,id_group);
                statement.setInt(2,id_user);
                statement.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Group1 GetGroupDataByID(DataLayer dl, int id){
     Group1 group=new Group1();
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_group(?)}");
                statement.setInt(1,id);
                statement.execute();
                group=ResultSetToGroup(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return  group;
    }
    public void GetGroupDataByID(DataLayer dl){
     Group1 group=new Group1();
        if ((dl.IsValid)) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_group(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                group=ResultSetToGroup(statement.getResultSet());
                this.id = group.id;
                this.name = group.name;
                this.admin = group.admin;
                this.image = group.image;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public User GetGroupAdmin(DataLayer dl){
        User user = new User();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_group_admin(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                user=User.ResultSetToUser(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return user;
    
    }
    public ArrayList<User> GetGroupMembers(DataLayer dl) {
    ArrayList<User> users = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_group_members(?)}");
                statement.setInt(1,this.id);
                statement.execute();
                users=ResultSetToUsers(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    return users;
    }
    public static ArrayList<User> GetGroupMembers(DataLayer dl,int id_group) {
    ArrayList<User> users = new ArrayList<>();
        if (dl.IsValid) {
            try (Connection conn = DriverManager.getConnection(dl.url)) {
                CallableStatement statement=conn.prepareCall("{call get_group_members(?)}");
                statement.setInt(1,id_group);
                statement.execute();
                users=ResultSetToUsers(statement.getResultSet());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    return users;
    }
}
