package org.example.appschoolevent.exceptions;

public class OperacionNoPermitida extends RuntimeException {
    public OperacionNoPermitida(String mensaje) {
        super(mensaje);
    }
}
