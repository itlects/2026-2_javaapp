package week02;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ex06BorderLayout {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BorderLayout");
            frame.add(new JButton("NORTH"), BorderLayout.NORTH);
            frame.add(new JButton("SOUTH"), BorderLayout.SOUTH);
            frame.add(new JButton("WEST"), BorderLayout.WEST);
            frame.add(new JButton("EAST"), BorderLayout.EAST);
            frame.add(new JButton("CENTER"), BorderLayout.CENTER);
            frame.setSize(460, 260);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
