package com.gabor.partypeps.models.dto;

import com.gabor.partypeps.models.dao.NotificationAuthorization;

public class NotificationAuthorizationDTO extends AbstractDTO {

    public String user;

    public String endpointUrl;

    public String p256dh;

    public String auth;

    public NotificationAuthorizationDTO(){}

    public NotificationAuthorizationDTO(NotificationAuthorization entity){
        this.id = entity.getId();
        this.endpointUrl = entity.getEndpointUrl();
        this.p256dh = entity.getAuth();
        this.auth = entity.getAuth();
        this.user = entity.getUser().getUsername();
    }

}
