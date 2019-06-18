package com.gabor.party.services;

import com.gabor.party.main.mappers.MessageMapper;
import com.gabor.party.main.models.dao.Message;
import com.gabor.party.main.models.dto.MessageDTO;
import com.gabor.party.repositories.MessageRepository;
import common.dto.AbstractDTO;
import common.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MessageService extends AbstractService<MessageDTO> {

    @Autowired
    public MessageRepository messageRepository;

    public static MessageMapper mapper = new MessageMapper();

    @Override
    public List<MessageDTO> findAll() {
        List<Message> messages = messageRepository.findAll();
        return this.mapper.mapListOfDTO(messages);
    }

    @Override
    public MessageDTO findById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return this.mapper.mapToDTO(message.get());
        }
        return null;
    }

    @Override
    public long insert(MessageDTO dto) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return MessageService.genericDelete(id, messageRepository);
    }

    @Override
    public boolean update(MessageDTO dto) {
        return false;
    }
}
