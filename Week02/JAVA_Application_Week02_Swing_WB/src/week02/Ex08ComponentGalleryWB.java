package week02;

import java.awt.EventQueue;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Ex08ComponentGalleryWB {
    private JFrame frame;
    private JTextField nameField;
    private JPasswordField passwordField;
    private final ButtonGroup genderGroup = new ButtonGroup();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Ex08ComponentGalleryWB window = new Ex08ComponentGalleryWB();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Ex08ComponentGalleryWB() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Ex08 - WindowBuilder 컴포넌트");
        frame.setBounds(100, 100, 560, 430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(30, 30, 90, 25);
        frame.getContentPane().add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(130, 30, 180, 25);
        frame.getContentPane().add(nameField);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(30, 70, 90, 25);
        frame.getContentPane().add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(130, 70, 180, 25);
        frame.getContentPane().add(passwordField);

        JLabel genderLabel = new JLabel("성별");
        genderLabel.setBounds(30, 110, 90, 25);
        frame.getContentPane().add(genderLabel);
        JRadioButton maleButton = new JRadioButton("남");
        genderGroup.add(maleButton);
        maleButton.setBounds(130, 110, 55, 25);
        frame.getContentPane().add(maleButton);
        JRadioButton femaleButton = new JRadioButton("여");
        genderGroup.add(femaleButton);
        femaleButton.setBounds(190, 110, 55, 25);
        frame.getContentPane().add(femaleButton);

        JLabel deptLabel = new JLabel("학과");
        deptLabel.setBounds(30, 150, 90, 25);
        frame.getContentPane().add(deptLabel);
        JComboBox<String> deptCombo = new JComboBox<>(
            new String[] {"컴퓨터소프트웨어과", "AI융합과", "전자과"});
        deptCombo.setBounds(130, 150, 180, 25);
        frame.getContentPane().add(deptCombo);

        JCheckBox agreeCheck = new JCheckBox("개인정보 수집에 동의합니다.");
        agreeCheck.setBounds(130, 190, 250, 25);
        frame.getContentPane().add(agreeCheck);
        JLabel memoLabel = new JLabel("자기소개");
        memoLabel.setBounds(30, 230, 90, 25);
        frame.getContentPane().add(memoLabel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(130, 230, 360, 80);
        frame.getContentPane().add(scrollPane);
        scrollPane.setViewportView(new JTextArea());

        JButton saveButton = new JButton("저장");
        saveButton.setBounds(200, 335, 90, 30);
        frame.getContentPane().add(saveButton);
        JButton closeButton = new JButton("닫기");
        closeButton.setBounds(300, 335, 90, 30);
        frame.getContentPane().add(closeButton);
        closeButton.addActionListener(e -> frame.dispose());
    }
}
