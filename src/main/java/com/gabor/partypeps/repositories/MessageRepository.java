package com.gabor.partypeps.repositories;

import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.receiverUser=:user or m.sourceUser=:user")
    List<Message> getUserMessages(@Param("user") User user);

    @Modifying
    @Query("UPDATE Message m SET m.isRead=true WHERE m.id IN :listIds")
    int readMessage(@Param("listIds") List<Long> listIds);

}
