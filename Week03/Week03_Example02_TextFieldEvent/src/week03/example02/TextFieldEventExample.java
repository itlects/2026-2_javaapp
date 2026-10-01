package week03.example02;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TextFieldEventExample {
    private JFrame frame;
    private JTextField txtName;
    private JLabel lblResult;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TextFieldEventExample().frame.setVisible(true));
    }

    public TextFieldEventExample() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("예제2 - 입력값 처리");
        frame.setBounds(100, 100, 450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("이름");
        lblName.setBounds(80, 50, 50, 30);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(135, 50, 160, 30);
        frame.getContentPane().add(txtName);

        JButton btnHello = new JButton("인사");
        btnHello.setBounds(155, 100, 100, 30);
        frame.getContentPane().add(btnHello);

        lblResult = new JLabel("");
        lblResult.setBounds(110, 150, 240, 30);
        frame.getContentPane().add(lblResult);

        btnHello.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                lblResult.setText("이름을 입력하세요.");
            } else {
                lblResult.setText(name + "님, 반갑습니다.");
            }
        });
    }
}
