package com.gabor.partypeps.repositories;

import com.gabor.partypeps.models.dao.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
