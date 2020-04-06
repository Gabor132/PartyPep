package com.gabor.partypeps.repositories;

import com.gabor.partypeps.models.dao.NotificationAuthorization;
import com.gabor.partypeps.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for the NotificationAuthorization data model
 *
 * @author dragos.gabor
 */

@Repository
public interface NotificationAuthorizationRepository extends JpaRepository<NotificationAuthorization, Long> {

    @Query("SELECT n FROM NotificationAuthorization n WHERE n.user=:user")
    List<NotificationAuthorization> getNotificationAuthorizationByUser(@Param("user") User user);

}
