package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        if (registeredAccount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
        } else if (accountService.findAccountByUsername(account.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }       
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account logInAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
        if(logInAccount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(logInAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        if(messageService.creatNewMessage(message) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.creatNewMessage(message));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }      
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageById(message_id));
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessageFromUser(@PathVariable Integer account_id) {
        if(messageService.getAllMessagesFromUser(account_id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessagesFromUser(account_id));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessage() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    @PatchMapping("messages/{message_id}")   
    public ResponseEntity<String> updateMessage( @PathVariable Integer message_id,  @RequestBody Message updateMessage ){     
        Message updatedMessage = messageService.updateMessage(message_id, updateMessage);
       if(updatedMessage != null) {
            return ResponseEntity.status(HttpStatus.OK)
                        .body("1");
       } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

   @DeleteMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<String> deleteMessage(@PathVariable Integer message_id) {
        Message deletedMessage = messageService.deleteMessage(message_id);
        if (deletedMessage != null) {
            return ResponseEntity.status(HttpStatus.OK).body( "1");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }       
    }
}
