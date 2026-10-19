package com.boulhosa.usuario.infrastructure.exptions;

public class ResourceNotFaundExeption extends RuntimeException {

    public ResourceNotFaundExeption( String messege) {
        super(messege);
    }

    public ResourceNotFaundExeption( String messege, Throwable cause) {
        super(messege, cause);
    }
}
