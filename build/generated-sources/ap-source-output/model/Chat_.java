package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-26T10:27:41")
@StaticMetamodel(Chat.class)
public class Chat_ { 

    public static volatile SingularAttribute<Chat, Integer> from;
    public static volatile SingularAttribute<Chat, Integer> id;
    public static volatile SingularAttribute<Chat, Integer> to;
    public static volatile SingularAttribute<Chat, Date> time;
    public static volatile SingularAttribute<Chat, String> message;
    public static volatile SingularAttribute<Chat, Boolean> userGroup;
    public static volatile SingularAttribute<Chat, String> username;

}