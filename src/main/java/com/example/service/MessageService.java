package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;


/**
 * 
 * This is a message service class
 * 
 */

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountService accountService;

    @Autowired
    public MessageService (MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
        
    }

     /**
     * This Method is getMessageById.
     * @param message_id the id of a message
     * @return an object of Message  if exist null otherwise.
     */
    public Message getMessageById(Integer message_id) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }    
    } 
    
     /**
     * This Method is creatNewMessage which creates new message if it comes from the existing user,
     * have message and not too long.
     * @param message  an object of message 
     * @return an object of Message  if it is created  null otherwise.
     */
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

     /**
     * This Method is getAllMessages to retrive all messages.
     * 
     * @return a list of messages.
     */
    public List<Message> getAllMessages() {       
        return messageRepository.findAll();
    }

     /**
     * This Method is getAllMessagesFromUser to retrive the exisiting messages from a particular user.
     * @param account_id the id of an account which is to be checked
     * @return list of messages  from a specified user 
     * 
     */
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
    
     /**
     * This Method is updateMessage to update the exisiting messages.
     * @param message_id the id of a message
     * @param message an object of message
     * @return an object of updated Message if updated null otherwise.
     */
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

     /**
     * This Method is deleteMessage.
     * @param message_id the id of a message which needs to be deleted
     * @return an object of Message  if deleted null otherwise.
     */
    public Message deleteMessage(Integer message_id) {   
        if(getMessageById(message_id) != null) {
            Message deletedMessage = getMessageById(message_id); 
            messageRepository.deleteById(message_id);
            return deletedMessage;       
        }
        return null;
    }
}
