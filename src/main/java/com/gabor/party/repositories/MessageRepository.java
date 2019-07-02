package com.gabor.party.repositories;

import com.gabor.party.main.models.dao.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
