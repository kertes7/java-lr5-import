package ua.duikt.importer.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import ua.duikt.importer.service.ImportFailedException;
import ua.duikt.importer.service.ImportSummary;

/** Запис підсумкового звіту у файл (UTF-8, try-with-resources). */
public final class ReportWriter {

    public void write(Path output, ImportSummary s) throws ImportFailedException {
        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            writer.write("Звіт імпорту оцінок");
            writer.newLine();
            writer.write("Кількість записів: " + s.imported());
            writer.newLine();
            if (s.imported() > 0) {
                writer.write(String.format(Locale.US, "Середній бал: %.2f", s.average()));
                writer.newLine();
                writer.write("Мінімальний бал: " + s.min());
                writer.newLine();
                writer.write("Максимальний бал: " + s.max());
                writer.newLine();
            } else {
                writer.write("Коректних записів немає");
                writer.newLine();
            }
            writer.write("Відхилено рядків: " + s.rejected());
            writer.newLine();
        } catch (IOException e) {
            throw new ImportFailedException("не вдалося записати звіт у " + output
                    + " (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")", e);
        }
    }
}
