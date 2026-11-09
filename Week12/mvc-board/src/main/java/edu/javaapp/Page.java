package edu.javaapp;

public final class Page {
    public static final int SIZE = 5;
    private final int totalPages;
    private final int number;

    public Page(String raw, int count) {
        totalPages = Math.max(1, (int) ((count + (long) SIZE - 1) / SIZE));
        int requested = 1;
        try {
            requested = Integer.parseInt(raw);
        } catch (NumberFormatException ignored) {
            // 누락 또는 숫자가 아닌 값은 1페이지로 처리한다.
        }
        number = Math.max(1, Math.min(requested, totalPages));
    }

    public int getNumber() { return number; }
    public int getTotalPages() { return totalPages; }
    public int getOffset() { return (number - 1) * SIZE; }
    public int getStart() { return ((number - 1) / 5) * 5 + 1; }
    public int getEnd() { return Math.min(getStart() + 4, totalPages); }
}
