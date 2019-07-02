package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.mappers.MessageMapper;
import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.models.dto.MessageDTO;
import com.gabor.partypeps.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService extends AbstractService<Message, MessageDTO> {

    private static MessageMapper mapper = new MessageMapper();

    @Autowired
    public MessageRepository messageRepository;

    @Autowired
    public MessageMapper messageMapper;

    /**
     * Function to insert a newly created Message entity into the database
     * @param dto MessageDTO
     * @return long - the ID of the newly created entity
     */
    @Override
    public long insert(MessageDTO dto) {
        return 0;
    }

    /**
     * Function to update an existing Message into the database
     * @param dto MessageDTO
     * @return boolean - to show if the update has succeeded of not
     */
    @Override
    public boolean update(MessageDTO dto) {
        return false;
    }

    @Override
    public JpaRepository<Message, Long> getRepository() {
        return messageRepository;
    }

    @Override
    public AbstractMapper<Message, MessageDTO> getMapper() {
        return messageMapper;
    }
}
