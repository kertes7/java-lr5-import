package ua.duikt.importer.domain;

/**
 * Запис про оцінку студента. Некоректний об'єкт створити неможливо:
 * правила перевіряються в конструкторі (захисне програмування на межі домену).
 */
public final class GradeRecord {

    public static final int MIN_GRADE = 0;
    public static final int MAX_GRADE = 100;

    private final String fullName;
    private final String subject;
    private final int grade;

    public GradeRecord(String fullName, String subject, int grade) {
        if (fullName == null || fullName.isBlank()) {
            throw new InvalidGradeException("ПІБ не може бути порожнім");
        }
        if (grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new InvalidGradeException(
                    "оцінка " + grade + " поза межами " + MIN_GRADE + ".." + MAX_GRADE);
        }
        this.fullName = fullName;
        this.subject = subject;
        this.grade = grade;
    }

    public String fullName() { return fullName; }
    public String subject()  { return subject; }
    public int grade()       { return grade; }
}
