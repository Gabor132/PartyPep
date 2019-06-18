package com.gabor.party.main.mappers;

import com.gabor.party.main.models.dao.Message;
import com.gabor.party.main.models.dto.MessageDTO;
import common.mapper.AbstractMapper;

public class MessageMapper extends AbstractMapper<Message, MessageDTO> {

    @Override
    public MessageDTO mapToDTO(Message entity) {
        MessageDTO dto = new MessageDTO();
        dto.text = entity.messageText;
        dto.groupId = entity.group.id;
        dto.sourceUserId = entity.sourceUser.id;
        return dto;
    }

    @Override
    public Message mapToDAO(MessageDTO dto) {
        Message message = new Message();
        message.messageText = dto.text;
        message.id = dto.id;
        /**
         * TODO: Implement the retrieval of connecting objects
         message.sourceUser =
         message.group =
         */
        return message;
    }

}
