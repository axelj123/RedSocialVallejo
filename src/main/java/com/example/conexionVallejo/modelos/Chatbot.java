package com.example.conexionVallejo.modelos;

import com.example.conexionVallejo.servicios.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.Normalizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Chatbot {

    private Map<String, String> faq;
    @Autowired
    private PostService postService;

    public Chatbot() {
        faq = new HashMap<>();
        faq.put("Cuál es el objetivo de esta red social", "Enriquecer la experiencia académica, personal y profesional de los estudiantes mediante la promoción de una cultura de aprendizaje continuo y colaboración efectiva.");
        faq.put("Cual es el objetivo de talk?", "Enriquecer la experiencia académica, personal y profesional de los estudiantes mediante la promoción de una cultura de aprendizaje continuo y colaboración efectiva.");
        faq.put("¿Quien eres?", "Soy Talk, una red social para los estudiantes de la Universidad César Vallejo");
        faq.put("Quien eres", "Soy Talk, una red social para los estudiantes de la Universidad César Vallejo");
        faq.put("Como te llamas", "Soy Talk, una red social para los estudiantes de la Universidad César Vallejo");
        faq.put("Cual es tu nombre", "Soy Talk, una red social para los estudiantes de la Universidad César Vallejo");

        faq.put("¿Cuál es el objetivo de esta red social?", "Enriquecer la experiencia académica, personal y profesional de los estudiantes mediante la promoción de una cultura de aprendizaje continuo y colaboración efectiva.");
        faq.put("¿Cómo me registro?", "Puedes registrarte en https://tu-sitio.com/registro. Si necesitas ayuda, contáctanos.");
        faq.put("¿Dónde está la biblioteca?", "La biblioteca está ubicada en el edificio principal, primer piso. Consulta el mapa en https://tu-sitio.com/mapa.");
        faq.put("¿Quién eres?", "Soy tu asistente virtual :).");
        faq.put("quien eres", "Soy tu asistente virtual :).");

    }

    public String getResponse(String message) {
        System.out.println("Mensaje recibido en el modelo: " + message);  // Log para verificar el mensaje recibido

        if (message.equalsIgnoreCase("hola")) {
            return "¡Hola! ¿Cómo puedo ayudarte?";
        } else if (message.equalsIgnoreCase("adios")) {
            return "¡Hasta luego!";
        } else if (faq.containsKey(message)) {
            return faq.get(message);
        } else {
            // Busca en la base de datos
            List<Post> preguntas = postService.obtenerPreguntas();
            for (Post pregunta : preguntas) {
                if (message.toLowerCase().contains(pregunta.getPostTitle().toLowerCase())) {
                    String url = "http://127.0.0.1:8080/postopen/" + pregunta.getId();
                    return "Aquí tienes información relevante: " + url;
                }
            }
            return "Lo siento, no entendí eso. ¿Podrías repetirlo?";
        }
    }

}
