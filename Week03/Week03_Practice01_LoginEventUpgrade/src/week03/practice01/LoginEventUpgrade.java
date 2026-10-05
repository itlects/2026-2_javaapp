package week03.practice01;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

public class LoginEventUpgrade {
    private JFrame frame;
    private JTextField txtId;
    private JPasswordField txtPassword;
    private JLabel lblStatus;
    private JButton btnLogin;
    private int failCount = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LoginEventUpgrade().frame.setVisible(true));
    }

    public LoginEventUpgrade() {
        frame = new JFrame("실습1 - 2주차 로그인 화면 + 이벤트");
        frame.setBounds(100, 100, 470, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblId = new JLabel("아이디");
        lblId.setBounds(70, 55, 70, 30);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 55, 200, 30);
        frame.getContentPane().add(txtId);

        JLabel lblPw = new JLabel("비밀번호");
        lblPw.setBounds(70, 100, 70, 30);
        frame.getContentPane().add(lblPw);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 100, 200, 30);
        frame.getContentPane().add(txtPassword);

        btnLogin = new JButton("로그인");
        btnLogin.setBounds(105, 165, 105, 35);
        frame.getContentPane().add(btnLogin);

        JButton btnCancel = new JButton("초기화");
        btnCancel.setBounds(230, 165, 105, 35);
        frame.getContentPane().add(btnCancel);

        lblStatus = new JLabel("2주차 화면에 이벤트를 추가합니다.");
        lblStatus.setBounds(70, 225, 330, 30);
        frame.getContentPane().add(lblStatus);

        // 1) ActionEvent
        btnLogin.addActionListener(e -> login());

        // 2) Password Enter
        txtPassword.addActionListener(e -> login());

        // 3) FocusEvent
        txtId.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                txtId.setBackground(new Color(255, 255, 220));
                lblStatus.setText("ID 입력 중");
            }
            @Override public void focusLost(FocusEvent e) {
                txtId.setBackground(Color.WHITE);
            }
        });

        // 4) KeyEvent - ESC 초기화
        frame.getRootPane().registerKeyboardAction(
            e -> clearForm(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        btnCancel.addActionListener(e -> clearForm());
    }

    private void login() {
        String id = txtId.getText().trim();
        String pw = new String(txtPassword.getPassword());

        if (id.isEmpty() || pw.isEmpty()) {
            lblStatus.setText("아이디와 비밀번호를 입력하세요.");
            return;
        }

        if ("java".equals(id) && "1234".equals(pw)) {
            failCount = 0;
            lblStatus.setText("로그인 성공");
            JOptionPane.showMessageDialog(frame, "로그인 성공");
        } else {
            failCount++;
            lblStatus.setText("로그인 실패 " + failCount + "회");
            if (failCount >= 3) {
                btnLogin.setEnabled(false);
                lblStatus.setText("3회 실패: 로그인 버튼 비활성화");
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtPassword.setText("");
        txtId.requestFocus();
        lblStatus.setText("입력값 초기화");
    }
}