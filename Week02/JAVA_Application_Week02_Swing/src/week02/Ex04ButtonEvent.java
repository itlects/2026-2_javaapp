package week02;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Ex04ButtonEvent {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("버튼 이벤트");
            JLabel message = new JLabel("버튼을 누르세요.");
            JButton button = new JButton("확인");
            button.addActionListener(e -> message.setText("버튼을 클릭했습니다."));
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 25));
            frame.add(message);
            frame.add(button);
            frame.setSize(420, 160);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
