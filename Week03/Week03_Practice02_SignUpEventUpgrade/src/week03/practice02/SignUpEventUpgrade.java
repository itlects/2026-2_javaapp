package week03.practice02;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import javax.swing.*;

public class SignUpEventUpgrade {
    private JFrame frame;
    private JTextField txtId;
    private JComboBox<String> cboMajor;
    private JCheckBox chkReading;
    private JCheckBox chkGame;
    private JCheckBox chkExercise;
    private JRadioButton rdoMale;
    private JRadioButton rdoFemale;
    private JLabel lblPreview;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new SignUpEventUpgrade().frame.setVisible(true));
    }

    public SignUpEventUpgrade() {
        frame = new JFrame("실습2 - 2주차 회원가입 화면 + 이벤트");
        frame.setBounds(100, 100, 560, 430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblId = new JLabel("아이디");
        lblId.setBounds(60, 45, 90, 30);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(165, 45, 230, 30);
        frame.getContentPane().add(txtId);

        JLabel lblGender = new JLabel("성별");
        lblGender.setBounds(60, 95, 90, 30);
        frame.getContentPane().add(lblGender);

        rdoMale = new JRadioButton("남");
        rdoFemale = new JRadioButton("여");
        rdoMale.setBounds(165, 95, 60, 30);
        rdoFemale.setBounds(235, 95, 60, 30);
        frame.getContentPane().add(rdoMale);
        frame.getContentPane().add(rdoFemale);

        ButtonGroup group = new ButtonGroup();
        group.add(rdoMale);
        group.add(rdoFemale);

        JLabel lblMajor = new JLabel("학과");
        lblMajor.setBounds(60, 145, 90, 30);
        frame.getContentPane().add(lblMajor);

        cboMajor = new JComboBox<>(new String[]{"선택", "컴퓨터소프트웨어과", "AI융합과", "정보통신과"});
        cboMajor.setBounds(165, 145, 230, 30);
        frame.getContentPane().add(cboMajor);

        JLabel lblHobby = new JLabel("취미");
        lblHobby.setBounds(60, 195, 90, 30);
        frame.getContentPane().add(lblHobby);

        chkReading = new JCheckBox("독서");
        chkGame = new JCheckBox("게임");
        chkExercise = new JCheckBox("운동");
        chkReading.setBounds(165, 195, 70, 30);
        chkGame.setBounds(235, 195, 70, 30);
        chkExercise.setBounds(305, 195, 70, 30);
        frame.getContentPane().add(chkReading);
        frame.getContentPane().add(chkGame);
        frame.getContentPane().add(chkExercise);

        lblPreview = new JLabel("선택 결과가 실시간으로 표시됩니다.");
        lblPreview.setBounds(60, 260, 430, 30);
        frame.getContentPane().add(lblPreview);

        JButton btnJoin = new JButton("가입");
        btnJoin.setBounds(165, 320, 100, 35);
        frame.getContentPane().add(btnJoin);

        cboMajor.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) updatePreview();
        });
        chkReading.addItemListener(e -> updatePreview());
        chkGame.addItemListener(e -> updatePreview());
        chkExercise.addItemListener(e -> updatePreview());
        rdoMale.addActionListener(e -> updatePreview());
        rdoFemale.addActionListener(e -> updatePreview());
        txtId.addActionListener(e -> updatePreview());

        btnJoin.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "아이디를 입력하세요.");
                txtId.requestFocus();
                return;
            }
            JOptionPane.showMessageDialog(frame, "현재 선택 정보를 확인했습니다.");
        });
    }

    private void updatePreview() {
        String gender = rdoMale.isSelected() ? "남" : rdoFemale.isSelected() ? "여" : "미선택";
        String hobby = "";
        if (chkReading.isSelected()) hobby += "독서 ";
        if (chkGame.isSelected()) hobby += "게임 ";
        if (chkExercise.isSelected()) hobby += "운동 ";
        lblPreview.setText("성별: " + gender + " / 학과: " + cboMajor.getSelectedItem() + " / 취미: " + hobby);
    }
}