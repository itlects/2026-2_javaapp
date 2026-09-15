package week02;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ex03LabelTextButton {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ex03 - 기본 입력 컴포넌트");
            frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
            JLabel nameLabel = new JLabel("이름");
            JTextField nameField = new JTextField(12);
            JButton checkButton = new JButton("입력 확인");
            checkButton.addActionListener(e ->
                JOptionPane.showMessageDialog(frame,
                    "입력한 이름: " + nameField.getText().trim()));
            frame.add(nameLabel);
            frame.add(nameField);
            frame.add(checkButton);
            frame.setSize(450, 160);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
