package com.gabor.party.main.models.dto;

import com.gabor.party.common.dto.AbstractDTO;

public class MessageDTO extends AbstractDTO {

    public String text;

    public long sourceUserId;

    public long groupId;
}
