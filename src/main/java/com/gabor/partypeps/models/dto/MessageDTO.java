package com.gabor.partypeps.models.dto;

import com.gabor.partypeps.models.dao.Message;

public class MessageDTO extends AbstractDTO {

    public String text;

    public String sourceUsername;

    public String receiverUsername;

    public String groupName;

    public boolean isRead;

    public MessageDTO() {}

    public MessageDTO(Message message){
        this.id = message.getId();
        this.text = message.getMessageText();
        this.sourceUsername = message.getSourceUser().getUsername();
        this.receiverUsername = message.getReceiverUser() != null ? message.getReceiverUser().getUsername() : null;
        this.groupName = message.getGroup() != null ? message.getGroup().name : null;
        this.isRead = message.getRead();
    }
}
