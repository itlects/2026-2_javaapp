package week02;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ex05FlowLayout {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ex05 - FlowLayout");
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
            frame.add(new JButton("신규"));
            frame.add(new JButton("저장"));
            frame.add(new JButton("삭제"));
            frame.add(new JButton("닫기"));
            frame.setSize(420, 180);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
