package week04;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GridLayoutDemo extends JFrame {

    public GridLayoutDemo() {
        setTitle("GridLayout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(
            new GridLayout(3, 3, 8, 8)
        );

        for (int i = 1; i <= 9; i++) {
            panel.add(new JButton(String.valueOf(i)));
        }

        add(panel);

        setSize(360, 360);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new GridLayoutDemo().setVisible(true)
        );
    }
}
