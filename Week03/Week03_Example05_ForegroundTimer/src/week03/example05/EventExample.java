package week03.example05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventExample extends JFrame {
    private JLabel labelForeground;
    private JLabel labelTimer;
    private JButton button;

    public EventExample() {
        setTitle("Event Model Example - Foreground & Timer");
        setSize(430, 210);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        labelForeground = new JLabel(
                "Click the button to handle a foreground event.",
                SwingConstants.CENTER);
        labelTimer = new JLabel(
                "Waiting for the Swing Timer...",
                SwingConstants.CENTER);
        button = new JButton("Click Me (Foreground Source)");

        add(labelForeground);
        add(labelTimer);
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelForeground.setText(
                        "Foreground event: button clicked!");
            }
        });

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String time = LocalTime.now().format(
                        DateTimeFormatter.ofPattern("HH:mm:ss"));
                labelTimer.setText("[Timer] Current time: " + time);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
            new EventExample().setVisible(true)
        );
    }
}
