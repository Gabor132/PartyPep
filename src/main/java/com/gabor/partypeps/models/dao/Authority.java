package com.gabor.partypeps.models.dao;

import com.gabor.partypeps.enums.AuthorityEnum;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITIES")
public class Authority extends AbstractEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_AUTHORITIES",
            joinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    private User user;

    @Enumerated(EnumType.STRING)
    private AuthorityEnum authority;

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public void setAuthority(AuthorityEnum authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authority(){}

    public Authority(AuthorityEnum role){
        this.authority = role;
    }
}
