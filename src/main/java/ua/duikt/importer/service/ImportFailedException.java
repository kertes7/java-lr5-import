package ua.duikt.importer.service;

/**
 * Фатальна помилка імпорту (файл не знайдено, не вдалося прочитати/записати).
 * Повідомлення зрозуміле користувачеві, а низькорівнева причина лишається в cause.
 */
public class ImportFailedException extends Exception {

    public ImportFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
