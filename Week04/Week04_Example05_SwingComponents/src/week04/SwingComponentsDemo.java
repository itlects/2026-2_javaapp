package week04;

import java.awt.*;
import javax.swing.*;

public class SwingComponentsDemo extends JFrame {
    public SwingComponentsDemo() {
        setTitle("Swing Components Demo");
        setSize(620, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField name = new JTextField();
        JPasswordField pw = new JPasswordField();
        JRadioButton male = new JRadioButton("남");
        JRadioButton female = new JRadioButton("여");
        ButtonGroup gender = new ButtonGroup(); gender.add(male); gender.add(female);
        JPanel genderPanel = new JPanel(); genderPanel.add(male); genderPanel.add(female);
        JComboBox<String> grade = new JComboBox<>(new String[]{"일반","VIP","VVIP"});
        JCheckBox agree = new JCheckBox("이메일 수신 동의");
        JSpinner age = new JSpinner(new SpinnerNumberModel(20, 10, 100, 1));
        JList<String> interests = new JList<>(new String[]{"Java","DB","Web","AI"});

        form.add(new JLabel("이름")); form.add(name);
        form.add(new JLabel("비밀번호")); form.add(pw);
        form.add(new JLabel("성별")); form.add(genderPanel);
        form.add(new JLabel("등급")); form.add(grade);
        form.add(new JLabel("나이")); form.add(age);
        form.add(new JLabel("수신")); form.add(agree);
        form.add(new JLabel("관심분야")); form.add(new JScrollPane(interests));

        JButton show = new JButton("입력값 확인");
        show.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "이름: " + name.getText() + "\n등급: " + grade.getSelectedItem() +
            "\n나이: " + age.getValue()));

        add(form, BorderLayout.CENTER);
        add(show, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingComponentsDemo().setVisible(true));
    }
}
