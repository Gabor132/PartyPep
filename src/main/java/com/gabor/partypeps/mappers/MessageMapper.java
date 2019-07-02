package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dto.MessageDTO;
import org.springframework.stereotype.Component;

@Component
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
