package week02;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ex05FlowLayout {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FlowLayout");
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));
            for (int i = 1; i <= 5; i++) frame.add(new JButton("버튼 " + i));
            frame.setSize(460, 180);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
