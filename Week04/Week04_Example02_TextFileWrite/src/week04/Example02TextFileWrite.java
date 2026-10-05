package week04;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Example02TextFileWrite {
    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
            bw.write("Java 응용 4주차");
            bw.newLine();
            bw.write("파일 저장이 완료되었습니다.");
            System.out.println("output.txt 저장 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
