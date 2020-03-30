package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dto.MessageDTO;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper extends AbstractMapper<Message, MessageDTO> {

    @Override
    public MessageDTO mapToDTO(Message entity) {
        return new MessageDTO(entity);
    }

    @Override
    public Message mapToDAO(MessageDTO dto) {
        Message message = new Message();
        if (dto.id != null) {
            message.setId(dto.id);
        }
        message.setMessageText(dto.text);;
        message.setRead(dto.isRead);
        /**
         * TODO: Implement the retrieval of connecting objects
         message.sourceUser =
         message.group =
         */
        return message;
    }

}
