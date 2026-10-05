package week04;

import java.io.*;

public class Example03BufferedCopy {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/source.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("data/copy.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            System.out.println("파일 복사 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
