package ua.duikt.importer.parse;

import ua.duikt.importer.domain.GradeRecord;
import ua.duikt.importer.domain.InvalidGradeException;

/** Розбір одного рядка формату «ПІБ;предмет;оцінка». Перевірка даних — саме тут, на межі системи. */
public final class RecordParser {

    private static final int FIELDS = 3;

    private RecordParser() { }

    public static GradeRecord parse(String line, int lineNumber) throws RecordParseException {
        if (line == null || line.isBlank()) {
            throw new RecordParseException(lineNumber, "порожній рядок", null);
        }

        String[] parts = line.split(";", -1);          // -1: зберігаємо порожні поля
        if (parts.length != FIELDS) {
            throw new RecordParseException(lineNumber,
                    "очікувалось " + FIELDS + " поля, отримано " + parts.length, null);
        }

        String rawGrade = parts[2].trim();
        int grade;
        try {
            grade = Integer.parseInt(rawGrade);
        } catch (NumberFormatException e) {
            // низькорівнева причина зберігається як cause
            throw new RecordParseException(lineNumber, "\"" + rawGrade + "\" не є цілим числом", e);
        }

        try {
            return new GradeRecord(parts[0].trim(), parts[1].trim(), grade);   // домен перевіряє правила
        } catch (InvalidGradeException e) {
            throw new RecordParseException(lineNumber, e.getMessage(), e);
        }
    }
}
