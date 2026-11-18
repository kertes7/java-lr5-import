package ua.duikt.importer.parse;

/**
 * Перевірюваний виняток розбору рядка. Зберігає номер рядка та причину (cause) —
 * низькорівнева помилка не губиться (NumberFormatException, InvalidGradeException тощо).
 */
public class RecordParseException extends Exception {

    private final int lineNumber;
    private final String reason;

    public RecordParseException(int lineNumber, String reason, Throwable cause) {
        super("Рядок " + lineNumber + ": " + reason, cause);
        this.lineNumber = lineNumber;
        this.reason = reason;
    }

    public int lineNumber() { return lineNumber; }
    public String reason()  { return reason; }
}
