package com.example.conexionVallejo.servicios;

public class EmailRequest {
    private String email;

    // Constructor, getters y setters
    // Constructor vacío necesario para que Spring pueda deserializar la solicitud correctamente
    public EmailRequest() {
    }

    public EmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
