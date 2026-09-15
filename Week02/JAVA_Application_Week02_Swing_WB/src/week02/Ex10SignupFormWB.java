package week02;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Ex10SignupFormWB {
    private JFrame frame;
    private JTextField idField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> deptCombo;
    private JCheckBox agreeCheck;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Ex10SignupFormWB window = new Ex10SignupFormWB();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Ex10SignupFormWB() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("회원가입 - WindowBuilder");
        frame.setBounds(100, 100, 560, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        addLabel("아이디", 35);
        idField = addTextField(35);
        addLabel("비밀번호", 80);
        passwordField = new JPasswordField();
        passwordField.setBounds(180, 80, 310, 28);
        frame.getContentPane().add(passwordField);
        addLabel("비밀번호 확인", 125);
        passwordConfirmField = new JPasswordField();
        passwordConfirmField.setBounds(180, 125, 310, 28);
        frame.getContentPane().add(passwordConfirmField);
        addLabel("이름", 170);
        nameField = addTextField(170);
        addLabel("학과", 215);
        deptCombo = new JComboBox<>(
            new String[] {"컴퓨터소프트웨어과", "AI융합과", "전자과"});
        deptCombo.setBounds(180, 215, 310, 28);
        frame.getContentPane().add(deptCombo);
        addLabel("이메일", 260);
        emailField = addTextField(260);
        agreeCheck = new JCheckBox("개인정보 수집 및 이용에 동의합니다.");
        agreeCheck.setBounds(180, 305, 310, 28);
        frame.getContentPane().add(agreeCheck);
        JButton signupButton = new JButton("가입");
        signupButton.setBounds(180, 350, 110, 32);
        frame.getContentPane().add(signupButton);
        JButton resetButton = new JButton("초기화");
        resetButton.setBounds(305, 350, 110, 32);
        frame.getContentPane().add(resetButton);
        signupButton.addActionListener(e -> validateInput());
        resetButton.addActionListener(e -> resetInput());
    }

    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(45, y, 120, 28);
        frame.getContentPane().add(label);
    }

    private JTextField addTextField(int y) {
        JTextField field = new JTextField();
        field.setBounds(180, y, 310, 28);
        frame.getContentPane().add(field);
        return field;
    }

    private void validateInput() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword());
        String passwordConfirm = new String(passwordConfirmField.getPassword());
        String name = nameField.getText().trim();
        if (id.isEmpty() || password.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "아이디, 비밀번호, 이름은 필수입니다.");
        } else if (!password.equals(passwordConfirm)) {
            JOptionPane.showMessageDialog(frame, "비밀번호가 서로 다릅니다.");
        } else if (!agreeCheck.isSelected()) {
            JOptionPane.showMessageDialog(frame, "개인정보 수집에 동의해 주세요.");
        } else {
            JOptionPane.showMessageDialog(frame, name + "님, 입력이 완료되었습니다.");
        }
    }

    private void resetInput() {
        idField.setText("");
        passwordField.setText("");
        passwordConfirmField.setText("");
        nameField.setText("");
        emailField.setText("");
        deptCombo.setSelectedIndex(0);
        agreeCheck.setSelected(false);
        idField.requestFocus();
    }
}
