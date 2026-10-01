package week03.practice02;

import java.awt.EventQueue;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SignUpPractice {
    private JFrame frame;
    private JTextField txtId;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;
    private JTextField txtName;
    private JRadioButton rdoMale;
    private JRadioButton rdoFemale;
    private JComboBox<String> cboMajor;
    private JCheckBox chkReading;
    private JCheckBox chkGame;
    private JCheckBox chkExercise;
    private final ButtonGroup genderGroup = new ButtonGroup();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new SignUpPractice().frame.setVisible(true));
    }

    public SignUpPractice() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("실습2 - 회원가입");
        frame.setBounds(100, 100, 520, 510);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblId = new JLabel("아이디");
        lblId.setBounds(70, 45, 90, 30);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(175, 45, 210, 30);
        frame.getContentPane().add(txtId);

        JLabel lblPw = new JLabel("비밀번호");
        lblPw.setBounds(70, 90, 90, 30);
        frame.getContentPane().add(lblPw);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(175, 90, 210, 30);
        frame.getContentPane().add(txtPassword);

        JLabel lblPw2 = new JLabel("비밀번호 확인");
        lblPw2.setBounds(70, 135, 100, 30);
        frame.getContentPane().add(lblPw2);

        txtPasswordConfirm = new JPasswordField();
        txtPasswordConfirm.setBounds(175, 135, 210, 30);
        frame.getContentPane().add(txtPasswordConfirm);

        JLabel lblName = new JLabel("이름");
        lblName.setBounds(70, 180, 90, 30);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(175, 180, 210, 30);
        frame.getContentPane().add(txtName);

        JLabel lblGender = new JLabel("성별");
        lblGender.setBounds(70, 225, 90, 30);
        frame.getContentPane().add(lblGender);

        rdoMale = new JRadioButton("남");
        rdoMale.setBounds(175, 225, 60, 30);
        frame.getContentPane().add(rdoMale);

        rdoFemale = new JRadioButton("여");
        rdoFemale.setBounds(245, 225, 60, 30);
        frame.getContentPane().add(rdoFemale);

        genderGroup.add(rdoMale);
        genderGroup.add(rdoFemale);

        JLabel lblMajor = new JLabel("학과");
        lblMajor.setBounds(70, 270, 90, 30);
        frame.getContentPane().add(lblMajor);

        cboMajor = new JComboBox<>(new String[] {
                "선택하세요", "컴퓨터소프트웨어과", "AI융합과", "정보통신과"
        });
        cboMajor.setBounds(175, 270, 210, 30);
        frame.getContentPane().add(cboMajor);

        JLabel lblHobby = new JLabel("취미");
        lblHobby.setBounds(70, 315, 90, 30);
        frame.getContentPane().add(lblHobby);

        chkReading = new JCheckBox("독서");
        chkReading.setBounds(175, 315, 70, 30);
        frame.getContentPane().add(chkReading);

        chkGame = new JCheckBox("게임");
        chkGame.setBounds(245, 315, 70, 30);
        frame.getContentPane().add(chkGame);

        chkExercise = new JCheckBox("운동");
        chkExercise.setBounds(315, 315, 70, 30);
        frame.getContentPane().add(chkExercise);

        JButton btnJoin = new JButton("가입");
        btnJoin.setBounds(135, 385, 100, 35);
        frame.getContentPane().add(btnJoin);

        JButton btnCancel = new JButton("취소");
        btnCancel.setBounds(265, 385, 100, 35);
        frame.getContentPane().add(btnCancel);

        btnJoin.addActionListener(e -> validateAndJoin());
        btnCancel.addActionListener(e -> clearForm());
    }

    private void validateAndJoin() {
        String id = txtId.getText().trim();
        String pw = new String(txtPassword.getPassword());
        String pw2 = new String(txtPasswordConfirm.getPassword());
        String name = txtName.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "아이디를 입력하세요.");
            txtId.requestFocus();
            return;
        }
        if (pw.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "비밀번호를 입력하세요.");
            txtPassword.requestFocus();
            return;
        }
        if (!pw.equals(pw2)) {
            JOptionPane.showMessageDialog(frame, "비밀번호가 일치하지 않습니다.");
            txtPasswordConfirm.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "이름을 입력하세요.");
            txtName.requestFocus();
            return;
        }
        if (!rdoMale.isSelected() && !rdoFemale.isSelected()) {
            JOptionPane.showMessageDialog(frame, "성별을 선택하세요.");
            return;
        }
        if (cboMajor.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(frame, "학과를 선택하세요.");
            return;
        }

        JOptionPane.showMessageDialog(frame, "회원가입 입력 검사가 완료되었습니다.");
    }

    private void clearForm() {
        txtId.setText("");
        txtPassword.setText("");
        txtPasswordConfirm.setText("");
        txtName.setText("");
        genderGroup.clearSelection();
        cboMajor.setSelectedIndex(0);
        chkReading.setSelected(false);
        chkGame.setSelected(false);
        chkExercise.setSelected(false);
        txtId.requestFocus();
    }
}
