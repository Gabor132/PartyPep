package com.gabor.party.main.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabor.party.common.model.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GROUPS")
public class Group extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long id;

    @Column(name = "NAME")
    public String name;

    @OneToMany
    @JoinTable(name = "USERS_GROUPS",
            joinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    @JsonIgnore
    public List<User> groupUsers;

    @OneToMany
    @JoinTable(name = "GROUPS_MESSAGES",
            joinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID", referencedColumnName = "ID", table = "GROUPS"))
    @JsonIgnore
    public List<Message> messages;

    public Group(){
        this.groupUsers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<User> groupUsers) {
        this.groupUsers = groupUsers;
    }
}
