package week02;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ex08LoginForm {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("로그인");
            JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
            form.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));
            form.add(new JLabel("아이디"));
            form.add(new JTextField(15));
            form.add(new JLabel("비밀번호"));
            form.add(new JPasswordField(15));

            JPanel buttons = new JPanel();
            buttons.add(new JButton("로그인"));
            buttons.add(new JButton("취소"));
            frame.add(form, BorderLayout.CENTER);
            frame.add(buttons, BorderLayout.SOUTH);
            frame.setSize(420, 220);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
