package week02;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ex07GridLayout {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("GridLayout");
            frame.setLayout(new GridLayout(2, 3, 8, 8));
            for (int i = 1; i <= 6; i++) frame.add(new JButton(String.valueOf(i)));
            frame.setSize(420, 220);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
