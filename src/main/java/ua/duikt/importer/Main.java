package ua.duikt.importer;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import ua.duikt.importer.io.ReportWriter;
import ua.duikt.importer.service.ImportFailedException;
import ua.duikt.importer.service.ImportService;
import ua.duikt.importer.service.ImportSummary;

/**
 * Консольна утиліта: java -jar importer.jar <вхідний_файл> <вихідний_звіт>.
 * Винятки перехоплюються тут, на найвищому рівні: користувач бачить зрозуміле повідомлення
 * без стектрейсу, а причина лишається в ланцюжку cause.
 */
public final class Main {

    public static void main(String[] args) {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        if (args.length != 2) {
            out.println("Використання: java -jar importer.jar <вхідний_файл> <вихідний_звіт>");
            System.exit(2);
        }
        Path input = Path.of(args[0]);
        Path output = Path.of(args[1]);

        try {
            ImportSummary summary = new ImportService().importFrom(input, out::println);
            new ReportWriter().write(output, summary);
            out.println("Імпортовано: " + summary.imported() + ", відхилено: " + summary.rejected()
                    + ". Звіт записано у " + output);
        } catch (ImportFailedException e) {
            out.println("Помилка: " + e.getMessage());       // без стектрейсу
            System.exit(1);
        }
    }
}
