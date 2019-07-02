package com.gabor.partypeps.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EVENTS")
public class Event extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EVENT_START")
    private Date startOfEvent;

    @Column(name = "LOCATION")
    private String location;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "INVITATIONS", joinColumns = @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    @JsonIgnore
    private List<User> invitees = null;

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

    public Date getStartOfEvent() {
        return startOfEvent;
    }

    public void setStartOfEvent(Date startOfEvent) {
        this.startOfEvent = startOfEvent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<User> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<User> invitees) {
        this.invitees = invitees;
    }

}
