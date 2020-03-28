package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.mappers.MessageMapper;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.MessageDTO;
import com.gabor.partypeps.repositories.MessageRepository;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends AbstractService<Message, MessageDTO> {

    private static MessageMapper mapper = new MessageMapper();

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Transactional
    public List<MessageDTO> findAllMyMessages(String username){
        return findAllMyMessages(username, false);
    }

    @Transactional
    public List<MessageDTO> findAllMyMessages(String username, boolean withReadMessages){
        List<MessageDTO> messages = findMyPrivateMessages(username, withReadMessages);
        messages.addAll(findMyGroupMessages(username, withReadMessages));
        return messages;
    }

    @Transactional
    public List<MessageDTO> findMyPrivateMessages(String username){
        return this.findMyPrivateMessages(username, false);
    }

    @Transactional
    public List<MessageDTO> findMyPrivateMessages(String username, boolean withReadMessages){
        User user = userRepository.findByUsername(username);
        List<Message> myMessages = messageRepository.getUserMessages(user).stream().filter(m -> withReadMessages && !m.getRead()).collect(Collectors.toList());
        return messageMapper.mapListOfDTO(myMessages);
    }

    @Transactional
    public List<MessageDTO> findMyGroupMessages(String username){
        return findMyGroupMessages(username, false);
    }

    @Transactional
    public List<MessageDTO> findMyGroupMessages(String username, boolean withReadMessages){
        User user = userRepository.findByUsername(username);
        List<Group> groups = user.getGroups();
        List<Message> groupMessages = groups.stream().map(Group::getMessages).reduce(new LinkedList<>(), (messages, messages2) -> {
            messages.addAll(messages2);
            return messages;
        }).stream().filter(m -> withReadMessages && !m.getRead()).collect(Collectors.toList());
        return messageMapper.mapListOfDTO(groupMessages);
    }

    /**
     * Function to insert a newly created Message entity into the database
     *
     * @param dto MessageDTO
     * @return long - the ID of the newly created entity
     */
    @Override
    public long insert(MessageDTO dto) {
        return 0;
    }

    /**
     * Function to update an existing Message into the database
     *
     * @param dto MessageDTO
     * @return boolean - to show if the update has succeeded of not
     */
    @Override
    public boolean update(MessageDTO dto) {
        return false;
    }

    @Transactional
    public int readMessages(String username, List<Long> ids){
        User user = userRepository.findByUsername(username);
        List<Message> messages = messageRepository.findAllById(ids);
        messages = messages.stream().filter(m -> m.getReceiverUser().getUsername().equals(username) || user.getGroups().stream().map(Group::getName).collect(Collectors.toList()).contains(m.getGroup().getName())).collect(Collectors.toList());
        messages.forEach(m -> m.setRead(true));
        return messageRepository.readMessage(messages.stream().map(Message::getId).collect(Collectors.toList()));
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
