package com.gabor.party.main.models.dao;

import common.model.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MESSAGES")
public class Message extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long id;

    @ManyToOne
    @JoinColumn(name = "SOURCE_USER", referencedColumnName = "ID")
    public User sourceUser;

    @ManyToOne
    @JoinTable(name = "GROUPS_MESSAGES",
            joinColumns = @JoinColumn(name = "MESSAGE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", table = "GROUPS"))
    public Group group;

    @Column(name = "TEXT")
    @NotNull
    public String messageText;
}
