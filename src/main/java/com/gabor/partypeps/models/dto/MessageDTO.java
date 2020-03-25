package com.gabor.partypeps.models.dto;

import com.gabor.partypeps.models.dao.Message;

public class MessageDTO extends AbstractDTO {

    public String text;

    public String sourceUsername;

    public String groupName;

    public MessageDTO() {}

    public MessageDTO(Message message){
        this.id = message.getId();
        this.text = message.getMessageText();
        this.sourceUsername = message.getSourceUser().getUsername();
        this.groupName = message.getGroup().name;
    }
}
