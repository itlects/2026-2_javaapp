package week04;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class BorderLayoutDemo extends JFrame {

    public BorderLayoutDemo() {
        setTitle("BorderLayout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(8, 8));

        panel.add(new JButton("NORTH"), BorderLayout.NORTH);
        panel.add(new JButton("SOUTH"), BorderLayout.SOUTH);
        panel.add(new JButton("WEST"), BorderLayout.WEST);
        panel.add(new JButton("EAST"), BorderLayout.EAST);

        JLabel center = new JLabel("CENTER - Remaining space", SwingConstants.CENTER);
        center.setPreferredSize(new Dimension(260, 160));
        panel.add(center, BorderLayout.CENTER);

        add(panel);
        setSize(520, 320);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new BorderLayoutDemo().setVisible(true)
        );
    }
}
