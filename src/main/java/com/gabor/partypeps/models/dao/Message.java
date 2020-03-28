package com.gabor.partypeps.models.dao;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MESSAGES")
public class Message extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "SOURCE_USER", referencedColumnName = "ID")
    private User sourceUser;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_USER", referencedColumnName = "ID")
    private User receiverUser;

    @ManyToOne
    @JoinTable(name = "GROUPS_MESSAGES",
            joinColumns = @JoinColumn(name = "MESSAGE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", table = "GROUPS"))
    private Group group;

    @Column(name = "TEXT")
    @NotNull
    private String messageText;

    @Column(name = "IS_READ")
    private Boolean isRead;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(User sourceUser) {
        this.sourceUser = sourceUser;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
