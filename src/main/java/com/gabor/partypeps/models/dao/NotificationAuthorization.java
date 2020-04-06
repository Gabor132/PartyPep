package com.gabor.partypeps.models.dao;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PUSH_NOTIFICATIONS_AUTHORIZATIONS")
public class NotificationAuthorization extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long id;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @NotEmpty
    @Column(name = "ENDPOINT_URL")
    private String endpointUrl;


    @NotNull
    @NotEmpty
    @Column(name = "P256DH")
    private String p256dh;


    @NotNull
    @NotEmpty
    @Column(name = "AUTH")
    private String auth;

    public NotificationAuthorization() {}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
