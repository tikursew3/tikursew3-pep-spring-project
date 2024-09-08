package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountService accountService;

    @Autowired
    public MessageService (MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
        
    }
    public Message getMessageById(Integer message_id) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }    
    }    
    public Message creatNewMessage(Message message) {
        Integer postedBy = message.getPostedBy();
        if (accountService.getAccountById(postedBy) != null) {
            if(!message.getMessageText().isBlank()) {
                if(message.getMessageText().length() < 255) {
                    return messageRepository.save(message);
                }
            }
        }
        return null;
    }
    public List<Message> getAllMessages() {       
        return messageRepository.findAll();
    }
    public List<Message>  getAllMessagesFromUser(Integer account_id) {
        List<Message> messages = new ArrayList<>();
        Account account = accountService.getAccountById(account_id);
        for(Message message : getAllMessages()) {
            if(message.getPostedBy().equals(account.getAccountId())) {
                messages.add(message);
            }
        }
        return messages;
    }   
    public Message updateMessage(Integer message_id, Message message) {
        if(getMessageById(message_id) != null) {
            if(!message.getMessageText().isBlank()) {
                if(message.getMessageText().length() < 255) {
                   messageRepository.save(message);
                   Message updatedMessage = messageRepository.getById(message_id);
                    return updatedMessage;
                }
            }           
        }
        return null;
    }
    public Message deleteMessage(Integer message_id) {   
        if(getMessageById(message_id) != null) {
            Message deletedMessage = getMessageById(message_id); 
            messageRepository.deleteById(message_id);
            return deletedMessage;       
        }
        return null;
    }
}
