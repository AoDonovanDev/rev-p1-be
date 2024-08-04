package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message creatMessage(Message message){
        return messageRepository.save(message);
    }

    public Message deleteMessage(Integer messageId){
        Message message = messageRepository.findByMessageId(messageId);
        messageRepository.delete(message);
        return message;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    public Message updateMessage(Integer message_id, String messageText) {
        Message msg = messageRepository.findByMessageId(message_id);
        msg.setMessageText(messageText);
        return messageRepository.save(msg);
    }

    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
