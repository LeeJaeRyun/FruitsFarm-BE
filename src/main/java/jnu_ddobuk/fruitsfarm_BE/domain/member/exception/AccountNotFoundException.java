package jnu_ddobuk.fruitsfarm_BE.domain.member.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
