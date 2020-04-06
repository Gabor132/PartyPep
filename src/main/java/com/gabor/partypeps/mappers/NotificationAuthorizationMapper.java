package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.NotificationAuthorization;
import com.gabor.partypeps.models.dto.NotificationAuthorizationDTO;
import org.springframework.stereotype.Component;

@Component
public class NotificationAuthorizationMapper extends AbstractMapper<NotificationAuthorization, NotificationAuthorizationDTO> {

    @Override
    public NotificationAuthorizationDTO mapToDTO(NotificationAuthorization entity) {
        return new NotificationAuthorizationDTO(entity);
    }

    /**
     * For now we do not support simultanious changes to user, groups and invitations
     * Just the ID and name
     *
     * @param dto
     * @return User
     */
    @Override
    public NotificationAuthorization mapToDAO(NotificationAuthorizationDTO dto) {
        NotificationAuthorization notify = new NotificationAuthorization();
        if (dto.id != null) {
            notify.setId(dto.id);
        }
        notify.setEndpointUrl(dto.endpointUrl);
        notify.setP256dh(dto.p256dh);
        notify.setAuth(dto.auth);
        return notify;
    }

}
