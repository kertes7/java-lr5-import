package ua.duikt.importer.service;

/** Підсумок імпорту. Накопичується «на льоту», самі записи в пам'яті не зберігаються. */
public final class ImportSummary {

    private int imported;
    private int rejected;
    private long sum;
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;

    void accept(int grade) {
        imported++;
        sum += grade;
        if (grade < min) min = grade;
        if (grade > max) max = grade;
    }

    void reject() { rejected++; }

    public int imported() { return imported; }
    public int rejected() { return rejected; }
    public double average() { return imported == 0 ? 0.0 : (double) sum / imported; }
    public int min() { return min; }
    public int max() { return max; }
}
