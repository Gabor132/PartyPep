package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.database.DatabaseConfiguration;
import com.gabor.partypeps.configurations.database.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.database.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dto.MessageDTO;
import com.gabor.partypeps.services.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = {
        DatabaseConfiguration.class,
        EntityManagerFactoryConfiguration.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class,
        UrlTestConfiguration.class
})
@ActiveProfiles(value = "DEV")
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageServiceTest {

    @Autowired
    public MessageService messageService;

    /**
     * Test to check the retrieval of all messages (read or unread)
     */
    @Test
    public void findAllMyMessagesTest() {
        String myUsername = "admin";
        List<MessageDTO> unreadMessages = messageService.findAllMyMessages(myUsername, false);
        List<MessageDTO> allMessages = messageService.findAllMyMessages(myUsername, true);
        Assert.notEmpty(unreadMessages, "No unread messages have been found");
        Assert.isTrue(unreadMessages.size() == 2, "Did not find exact two unread message");
        Assert.notEmpty(allMessages, "No messages have been found");
        Assert.isTrue(allMessages.size() == 4, "Did not find exact four unread message");
    }

    /**
     * Test to check the retrieval of private messages (read or unread)
     */
    @Test
    public void findMyPrivateMessagesTest() {
        String myUsername = "admin";
        List<MessageDTO> unreadMessages = messageService.findMyPrivateMessages(myUsername, false);
        List<MessageDTO> allMessages = messageService.findMyPrivateMessages(myUsername, true);
        Assert.notEmpty(unreadMessages, "No unread messages have been found");
        Assert.isTrue(unreadMessages.size() == 1, "Did not find exact one unread message");
        Assert.notEmpty(allMessages, "No messages have been found");
        Assert.isTrue(allMessages.size() == 2, "Did not find exact two unread message");
    }

    /**
     * Test to check the retrieval of group messages (read or unread)
     */
    @Test
    public void findMyGroupMessagesTest() {
        String myUsername = "admin";
        List<MessageDTO> unreadMessages = messageService.findMyGroupMessages(myUsername, false);
        List<MessageDTO> allMessages = messageService.findMyGroupMessages(myUsername, true);
        Assert.notEmpty(unreadMessages, "No unread messages have been found");
        Assert.isTrue(unreadMessages.size() == 1, "Did not find exact one unread message");
        Assert.notEmpty(allMessages, "No messages have been found");
        Assert.isTrue(allMessages.size() == 2, "Did not find exact two unread message");
    }

    /**
     * Test to check the adding of new messages between users
     */
    @Test
    public void addMessageTest() {
        String myUsername = "Dani";
        String hisUsername = "John";
        String receivingGroup = "Gasca Mica";
        MessageDTO privateMessageDTO = new MessageDTO();
        privateMessageDTO.sourceUsername = myUsername;
        privateMessageDTO.receiverUsername = hisUsername;
        privateMessageDTO.text = "Unit test text";
        MessageDTO groupMessageDTO = new MessageDTO();
        groupMessageDTO.sourceUsername = myUsername;
        groupMessageDTO.groupName = receivingGroup;
        groupMessageDTO.text = "Unit test text";
        long id = messageService.addMessage(myUsername, privateMessageDTO);
        long id2 = messageService.addMessage(myUsername, groupMessageDTO);
        MessageDTO privateMessage = messageService.findById(id);
        MessageDTO groupMessage = messageService.findById(id2);
        Assert.isTrue(privateMessage.text.equals(privateMessageDTO.text), "Created message and stored in DB message are not equal");
        Assert.isTrue(privateMessage.receiverUsername.equals(privateMessageDTO.receiverUsername), "Created message and stored in DB message are not equal");
        Assert.isTrue(privateMessage.groupName == null, "Created private message has group name assigned");
        Assert.isTrue(privateMessage.sourceUsername.equals(privateMessageDTO.sourceUsername), "Created message and stored in DB message are not equal");
        Assert.isTrue(privateMessage.isRead == privateMessageDTO.isRead, "Created message and stored in DB message are not equal");
        Assert.isTrue(groupMessage.text.equals(groupMessageDTO.text), "Created message and stored in DB message are not equal");
        Assert.isTrue(groupMessage.groupName.equals(groupMessageDTO.groupName), "Created message and stored in DB message are not equal");
        Assert.isTrue(groupMessage.receiverUsername == null, "Created group message has receiver user assigned");
        Assert.isTrue(groupMessage.sourceUsername.equals(groupMessageDTO.sourceUsername), "Created message and stored in DB message are not equal");
        Assert.isTrue(groupMessage.isRead == groupMessageDTO.isRead, "Created message and stored in DB message are not equal");
    }

    /**
     * Test to check the adding of private messages between users
     */
    @Test
    public void addPrivateMessageTest() {
        String myUsername = "Dani";
        String hisUsername = "John";
        MessageDTO privateMessageDTO = new MessageDTO();
        privateMessageDTO.sourceUsername = myUsername;
        privateMessageDTO.receiverUsername = hisUsername;
        privateMessageDTO.text = "Unit test text";
        long id = messageService.addPrivateMessage(myUsername, privateMessageDTO);
        MessageDTO privateMessage = messageService.findById(id);
        Assert.isTrue(privateMessage.text.equals(privateMessageDTO.text), "Created message and stored in DB message are not equal");
        Assert.isTrue(privateMessage.receiverUsername.equals(privateMessageDTO.receiverUsername), "Created message and stored in DB message are not equal");
        Assert.isTrue(privateMessage.groupName == null, "Created private message has group name assigned");
        Assert.isTrue(privateMessage.sourceUsername.equals(privateMessageDTO.sourceUsername), "Created message and stored in DB message are not equal");
        Assert.isTrue(privateMessage.isRead == privateMessageDTO.isRead, "Created message and stored in DB message are not equal");
    }


    /**
     * Test to check the adding of group messages from a user to a group
     */
    @Test
    public void addGroupMessage() {
        String myUsername = "Dani";
        String receivingGroup = "Gasca Mica";
        MessageDTO groupMessageDTO = new MessageDTO();
        groupMessageDTO.sourceUsername = myUsername;
        groupMessageDTO.groupName = receivingGroup;
        groupMessageDTO.text = "Unit test text";
        long id2 = messageService.addGroupMessage(myUsername, groupMessageDTO);
        MessageDTO groupMessage = messageService.findById(id2);Assert.isTrue(groupMessage.text.equals(groupMessageDTO.text), "Created message and stored in DB message are not equal");
        Assert.isTrue(groupMessage.groupName.equals(groupMessageDTO.groupName), "Created message and stored in DB message are not equal");
        Assert.isTrue(groupMessage.receiverUsername == null, "Created group message has receiver user assigned");
        Assert.isTrue(groupMessage.sourceUsername.equals(groupMessageDTO.sourceUsername), "Created message and stored in DB message are not equal");
        Assert.isTrue(groupMessage.isRead == groupMessageDTO.isRead, "Created message and stored in DB message are not equal");
    }

    /**
     * Test to check that setting the messages to read by the receiving user works
     */
    @Test
    public void readMessagesTest() {
        String myUsername = "Dani";
        List<MessageDTO> messageDTOS = messageService.findAllMyMessages(myUsername, false);
        Assert.isTrue(messageDTOS.size() > 0, "No unread messages to test");
        int i = messageService.readMessages(myUsername, messageDTOS.stream().map(m -> m.id).collect(Collectors.toList()));
        Assert.isTrue(messageDTOS.size() == i, "Not all messages were set to read");
        messageDTOS = messageService.findAllMyMessages(myUsername, false);
        Assert.isTrue(messageDTOS.size() == 0, "There are still read messages");
    }

    /**
     * Test to check that setting the messages to read by a different user then the receiving one does not work (Private Messages
     */
    @Test
    public void readMessagesNegativeTest() {
        String myUsername = "admin";
        String targetUsername = "John";
        List<MessageDTO> messageDTOS = messageService.findMyPrivateMessages(targetUsername, false);
        int originalNumberOfUnreadMessages = messageDTOS.size();
        Assert.isTrue(messageDTOS.size() > 0, "No unread messages to test");
        int i = messageService.readMessages(myUsername, messageDTOS.stream().map(m -> m.id).collect(Collectors.toList()));
        Assert.isTrue(i == 0, "Some messages were set to read");
        messageDTOS = messageService.findMyPrivateMessages(targetUsername, false);
        Assert.isTrue(messageDTOS.size() == originalNumberOfUnreadMessages, "There are no read messages left");
    }



    @Test
    public void getRepositoryTest() {
        JpaRepository<Message, Long> repository = messageService.getRepository();
        Assert.notNull(repository, "The mapper returned by the service is null");
    }

    @Test
    public void getMapperTest() {
        AbstractMapper mapper = messageService.getMapper();
        Assert.notNull(mapper, "The mapper returned by the service is null");
    }
}