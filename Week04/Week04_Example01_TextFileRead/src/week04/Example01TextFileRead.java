package week04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Example01TextFileRead {
    public static void main(String[] args) {
        String fileName = "data/sample.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }
    }
}
