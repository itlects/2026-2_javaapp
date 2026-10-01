package week03.practice03;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LayoutPractice {

    public static void main(String[] args) {
        JFrame frame = new JFrame("실습3 - Layout 비교");
        frame.setSize(720, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1, 5, 5));

        JPanel flow = new JPanel(new FlowLayout());
        flow.setBorder(BorderFactory.createTitledBorder("FlowLayout"));
        for (int i = 1; i <= 6; i++) {
            flow.add(new JButton("F" + i));
        }

        JPanel grid = new JPanel(new GridLayout(2, 3, 5, 5));
        grid.setBorder(BorderFactory.createTitledBorder("GridLayout"));
        for (int i = 1; i <= 6; i++) {
            grid.add(new JButton("G" + i));
        }

        JPanel border = new JPanel(new BorderLayout(5, 5));
        border.setBorder(BorderFactory.createTitledBorder("BorderLayout"));
        border.add(new JButton("NORTH"), BorderLayout.NORTH);
        border.add(new JButton("SOUTH"), BorderLayout.SOUTH);
        border.add(new JButton("WEST"), BorderLayout.WEST);
        border.add(new JButton("EAST"), BorderLayout.EAST);
        border.add(new JButton("CENTER"), BorderLayout.CENTER);

        frame.add(flow);
        frame.add(grid);
        frame.add(border);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
