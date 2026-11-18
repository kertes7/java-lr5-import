package ua.duikt.importer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.function.Consumer;

import ua.duikt.importer.domain.GradeRecord;
import ua.duikt.importer.parse.RecordParseException;
import ua.duikt.importer.parse.RecordParser;

/**
 * Потокове читання файлу: рядок за рядком, без завантаження всього файлу в пам'ять.
 * Кодування UTF-8 задано явно. Файл закривається try-with-resources за будь-якого результату.
 */
public final class ImportService {

    /**
     * @param rejectLog куди повідомляти про відхилені рядки (номер рядка й причина)
     */
    public ImportSummary importFrom(Path input, Consumer<String> rejectLog) throws ImportFailedException {
        ImportSummary summary = new ImportSummary();

        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            String line = reader.readLine();          // перший рядок — заголовок
            if (line == null) {
                return summary;                        // порожній файл — нічого імпортувати
            }
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    GradeRecord record = RecordParser.parse(line, lineNumber);
                    summary.accept(record.grade());
                } catch (RecordParseException e) {
                    summary.reject();                  // пошкоджений рядок не зупиняє імпорт
                    rejectLog.accept(describe(e));
                }
            }
        } catch (NoSuchFileException e) {
            throw new ImportFailedException("файл " + input + " не знайдено", e);
        } catch (IOException e) {
            throw new ImportFailedException("не вдалося прочитати файл " + input
                    + " (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")", e);
        }
        return summary;
    }

    /** Повідомлення про рядок + низькорівнева причина, якщо вона є (причина не губиться). */
    private static String describe(RecordParseException e) {
        Throwable cause = e.getCause();
        if (cause instanceof NumberFormatException) {
            return e.getMessage() + " [" + cause.getClass().getSimpleName() + ": " + cause.getMessage() + "]";
        }
        return e.getMessage();
    }
}
