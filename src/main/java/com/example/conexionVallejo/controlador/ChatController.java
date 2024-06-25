package com.example.conexionVallejo.controlador;

import com.example.conexionVallejo.modelos.Chatbot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {


    @Autowired
    private Chatbot chatbot;

    @PostMapping("/message")
    public String handleMessage(@RequestBody String message) {
        return chatbot.getResponse(message);
    }
}
