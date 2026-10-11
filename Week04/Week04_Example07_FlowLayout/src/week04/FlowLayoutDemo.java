package week04;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FlowLayoutDemo extends JFrame {

    public FlowLayoutDemo() {
        setTitle("FlowLayout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 10)
        );

        for (int i = 1; i <= 8; i++) {
            panel.add(new JButton("Button " + i));
        }

        add(panel);

        setSize(520, 220);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new FlowLayoutDemo().setVisible(true)
        );
    }
}
