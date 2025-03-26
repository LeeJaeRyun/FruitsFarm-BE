package jnu_ddobuk.fruitsfarm_BE.domain.member.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}