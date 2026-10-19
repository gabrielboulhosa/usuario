package com.boulhosa.usuario.infrastructure.exptions;

public class ConflictExeption extends RuntimeException{

    public ConflictExeption(String message){
        super(message);
    }

    public ConflictExeption(String message, Throwable cause){
        super("Conflito de dados", cause);
    }
}
