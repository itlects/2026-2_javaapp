package week02;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ex10SignupForm extends JFrame {
    private final JTextField idField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JTextField nameField = new JTextField(15);
    private final JComboBox<String> departmentBox = new JComboBox<>(new String[] {"컴퓨터소프트웨어과", "전자공학과", "기타"});
    private final JCheckBox agreeBox = new JCheckBox("개인정보 수집에 동의합니다.");

    public Ex10SignupForm() {
        super("회원가입");
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        form.add(new JLabel("아이디 *")); form.add(idField);
        form.add(new JLabel("비밀번호 *")); form.add(passwordField);
        form.add(new JLabel("이름 *")); form.add(nameField);
        form.add(new JLabel("학과")); form.add(departmentBox);
        form.add(new JLabel("동의")); form.add(agreeBox);

        JButton submitButton = new JButton("가입");
        JButton resetButton = new JButton("초기화");
        submitButton.addActionListener(e -> submit());
        resetButton.addActionListener(e -> reset());
        JPanel buttons = new JPanel();
        buttons.add(submitButton);
        buttons.add(resetButton);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        setSize(520, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void submit() {
        if (idField.getText().trim().isBlank() || passwordField.getPassword().length == 0 || nameField.getText().trim().isBlank()) {
            JOptionPane.showMessageDialog(this, "필수 항목을 입력하세요.", "입력 확인", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!agreeBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "개인정보 수집 동의가 필요합니다.", "동의 확인", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, nameField.getText().trim() + "님의 회원가입이 완료되었습니다.");
    }

    private void reset() {
        idField.setText("");
        passwordField.setText("");
        nameField.setText("");
        departmentBox.setSelectedIndex(0);
        agreeBox.setSelected(false);
        idField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ex10SignupForm().setVisible(true));
    }
}
