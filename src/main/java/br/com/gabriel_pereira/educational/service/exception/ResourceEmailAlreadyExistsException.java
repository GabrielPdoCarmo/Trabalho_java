package br.com.gabriel_pereira-main.educational.service.exception;

public class ResourceEmailAlreadyExistsException extends RuntimeException{
    public ResourceEmailAlreadyExistsException(String msg) {
        super(msg);
    }
}