package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.mappers.NotificationAuthorizationMapper;
import com.gabor.partypeps.models.dao.NotificationAuthorization;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.NotificationAuthorizationDTO;
import com.gabor.partypeps.notifications.NotificationsHandler;
import com.gabor.partypeps.repositories.NotificationAuthorizationRepository;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationAuthorizationService extends AbstractService<NotificationAuthorization, NotificationAuthorizationDTO> {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public NotificationAuthorizationMapper notificationAuthorizationMapper;

    @Autowired
    public NotificationAuthorizationRepository notificationAuthorizationRepository;

    @Transactional
    public List<NotificationAuthorizationDTO> getNotificationAuthorizations(String myUsername) {
        User user = userRepository.findByUsername(myUsername);
        return notificationAuthorizationMapper.mapListOfDTO(user.getNotificationsList());
    }

    @Transactional
    public Long addNotificationAuthorization(String myUsername, NotificationAuthorizationDTO dto){
        User user  = userRepository.findByUsername(myUsername);
        NotificationAuthorization notify = notificationAuthorizationMapper.mapToDAO(dto);
        notify.setUser(user);
        notify = notificationAuthorizationRepository.saveAndFlush(notify);
        return notify.getId();
    }

    public Boolean notifyUser(String myUsername, String hisUsername){
        User hisUser = userRepository.findByUsername(hisUsername);
        NotificationAuthorization notificationAuthorization = (NotificationAuthorization) notificationAuthorizationRepository.getNotificationAuthorizationByUser(hisUser);
        /**
         * TODO
          */
        return true;
    }

    public Boolean notifyAdmin(){
        User user = userRepository.findByUsername("admin");
        if(! user.getNotificationsList().isEmpty()) {
            return NotificationsHandler.doNotificationsToUser(user.getNotificationsList().get(0));
        }
        return false;
    }

    @Override
    public long insert(NotificationAuthorizationDTO dto) {
        return 0;
    }

    @Override
    public boolean update(NotificationAuthorizationDTO dto) {
        return false;
    }

    @Override
    public JpaRepository<NotificationAuthorization, Long> getRepository() {
        return notificationAuthorizationRepository;
    }

    @Override
    public AbstractMapper<NotificationAuthorization, NotificationAuthorizationDTO> getMapper() {
        return notificationAuthorizationMapper;
    }

}
