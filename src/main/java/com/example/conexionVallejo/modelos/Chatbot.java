package com.example.conexionVallejo.modelos;

import org.springframework.stereotype.Component;

@Component
public class Chatbot {

    public String getResponse(String message) {
        // Implementa la lógica del chatbot aquí
        if (message.equalsIgnoreCase("hola")) {
            return "¡Hola! ¿Cómo puedo ayudarte?";
        } else if (message.equalsIgnoreCase("adios")) {
            return "¡Hasta luego!";
        } else {
            return "Lo siento, no entendí eso. ¿Podrías repetirlo?";
        }
    }
}
