package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message creatMessage(Message message) throws InvalidMessageException{
        Boolean validAccount = accountRepository.existsById(message.getPostedBy());
        if(message.getMessageText().length() > 255 || message.getMessageText().length() == 0 || !validAccount) {
            throw new InvalidMessageException();
        }
        return messageRepository.save(message);
    }

    public Optional<Message> deleteMessage(Integer messageId){
        Optional<Message> messageOpt = messageRepository.findByMessageId(messageId);
        if(messageOpt.isPresent()) {
            messageRepository.delete(messageOpt.get());
        }
        
        return messageOpt;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    public void updateMessage(Integer message_id, String messageText) throws InvalidMessageException {
        Optional<Message> messageOpt = messageRepository.findByMessageId(message_id);
        if(messageOpt.isPresent() && messageText.length() < 255 && messageText.length() > 0){
            Message message = messageOpt.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            messageOpt = Optional.of(message);
        } else {
            throw new InvalidMessageException();
        }
    }

    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
