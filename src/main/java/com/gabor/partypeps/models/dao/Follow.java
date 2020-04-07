package com.gabor.partypeps.models.dao;

import javax.persistence.*;

@Entity
@Table(name = "FOLLOWS")
public class Follow extends AbstractEntity{

    public Follow(){}

    public Follow(User follower, User followed, Boolean accepted){
        this.followed = followed;
        this.follower = follower;
        this.accepted = accepted;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long id;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "FOLLOWER_ID")
    private User follower;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "FOLLOWED_ID")
    private User followed;

    @Column(name = "ACCEPTED")
    private Boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
