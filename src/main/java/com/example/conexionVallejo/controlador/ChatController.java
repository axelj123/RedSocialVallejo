package com.example.conexionVallejo.controlador;

import com.example.conexionVallejo.modelos.Chatbot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private Chatbot chatbot;

    @PostMapping("/message")
    public ResponseEntity<String> handleMessage(@RequestBody String message) {
        System.out.println("Mensaje recibido en el controlador: " + message);  // Log para verificar el mensaje recibido
        String response = chatbot.getResponse(message);
        System.out.println("Respuesta del chatbot: " + response);  // Log para verificar la respuesta del chatbot
        return ResponseEntity.ok(response);
    }
}
