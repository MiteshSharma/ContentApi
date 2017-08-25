package model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * Created by mitesh on 18/11/16.
 */
@Embedded
public class TeamMember {
    @Property("userId")
    private String userId;
    @Property("name")
    private String name;
    @Property("role")
    private ERole role = ERole.MEMBER;
    @Property("joinedDate")
    private Date joinedDate;

    public TeamMember() {}

    public TeamMember(User user, ERole role, Date joinedDate) {
        this.userId = user.getId().toString();
        this.name = user.getName();
        this.role = role;
        this.joinedDate = joinedDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public ERole getRole() {
        return role;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }
}
