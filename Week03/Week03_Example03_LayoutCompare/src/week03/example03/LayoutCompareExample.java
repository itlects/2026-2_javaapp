package week03.example03;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LayoutCompareExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("예제3 - Layout 비교");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));

        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.add(new JButton("A"));
        flowPanel.add(new JButton("B"));
        flowPanel.add(new JButton("C"));

        JPanel gridPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        gridPanel.add(new JButton("1"));
        gridPanel.add(new JButton("2"));
        gridPanel.add(new JButton("3"));

        frame.add(flowPanel);
        frame.add(gridPanel);
        frame.setVisible(true);
    }
}
