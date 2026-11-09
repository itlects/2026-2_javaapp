import edu.javaapp.Page;

public class PageTest {
    private static void check(String raw, int count, int number, int pages, int offset) {
        Page p = new Page(raw, count);
        if (p.getNumber() != number || p.getTotalPages() != pages || p.getOffset() != offset) {
            throw new AssertionError("page=" + raw + ", count=" + count);
        }
    }
    public static void main(String[] args) {
        check(null, 23, 1, 5, 0);
        check("2", 23, 2, 5, 5);
        check("999", 23, 5, 5, 20);
        check("-3", 23, 1, 5, 0);
        check("abc", 23, 1, 5, 0);
        check("999999999999999999", 23, 1, 5, 0);
        check("2", 0, 1, 1, 0);
        check("3", 10, 2, 2, 5);
        check("3", 12, 3, 3, 10);
        Page p = new Page("6", 31);
        if (p.getStart() != 6 || p.getEnd() != 7) throw new AssertionError("page block");
        System.out.println("PASS: page boundaries, offset and navigation block");
    }
}
