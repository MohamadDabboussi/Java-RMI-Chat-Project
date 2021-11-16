package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-26T10:27:41")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Date> lastseen;
    public static volatile SingularAttribute<User, String> image;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> about;
    public static volatile SingularAttribute<User, Boolean> online;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> username;

}