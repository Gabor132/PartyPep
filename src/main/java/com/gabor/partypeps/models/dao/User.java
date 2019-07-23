package com.gabor.partypeps.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long id;

    @Column(name = "USERNAME")
    private String name;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_GROUPS", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID"))
    private List<Group> groups;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "INVITATIONS", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID"))
    @JsonIgnore
    private List<Event> invitations;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Group> getGroups() {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Event> getInvitations() {
        if (invitations == null) {
            invitations = new ArrayList<>();
        }
        return invitations;
    }

    public void setInvitations(List<Event> invitations) {
        this.invitations = invitations;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
