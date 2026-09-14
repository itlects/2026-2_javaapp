package week02;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ex03LabelTextField {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("입력 컴포넌트");
            frame.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 20));
            frame.add(new JLabel("이름"));
            frame.add(new JTextField(15));
            frame.setSize(420, 160);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
