package com.example.conexionVallejo.modelos;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Chatbot {

    private Map<String, String> faq;

    public Chatbot() {
        faq = new HashMap<>();
        // Aquí se inicializan algunas preguntas frecuentes y sus respuestas
        faq.put("¿Qué cursos ofrecen?", "Ofrecemos una variedad de cursos en las áreas de ciencias, humanidades, y tecnología. Visita https://tu-sitio.com/cursos para más detalles.");
        faq.put("¿Cómo me registro?", "Puedes registrarte en https://tu-sitio.com/registro. Si necesitas ayuda, contáctanos.");
        faq.put("¿Dónde está la biblioteca?", "La biblioteca está ubicada en el edificio principal, primer piso. Consulta el mapa en https://tu-sitio.com/mapa.");
    }

    public String getResponse(String message) {
        System.out.println("Mensaje recibido en el modelo: " + message);  // Log para verificar el mensaje recibido

        // Convertir a minúsculas y eliminar espacios adicionales
        if (message.equals("hola")) {
            return "¡Hola! ¿Cómo puedo ayudarte?";
        } else if (message.equals("adios")) {
            return "¡Hasta luego!";
        } else if (faq.containsKey(message)) {
            return faq.get(message);
        } else {
            return "Lo siento, no entendí eso. ¿Podrías repetirlo?";
        }
    }

}
