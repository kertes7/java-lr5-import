package ua.duikt.importer.domain;

/**
 * Власний неперевірюваний виняток: об'єкт предметної області неможливо створити
 * з некоректними даними (порушення інваріантів GradeRecord).
 */
public class InvalidGradeException extends RuntimeException {

    public InvalidGradeException(String message) {
        super(message);
    }

    public InvalidGradeException(String message, Throwable cause) {
        super(message, cause);
    }
}
