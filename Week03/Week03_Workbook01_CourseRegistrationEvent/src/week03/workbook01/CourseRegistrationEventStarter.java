package week03.workbook01;

import java.awt.EventQueue;
import javax.swing.*;

public class CourseRegistrationEventStarter {
    private JFrame frame;
    private JTextField txtName;
    private JComboBox<String> cboCourse;
    private JCheckBox chkMorning;
    private JCheckBox chkAfternoon;
    private JRadioButton rdoOnline;
    private JRadioButton rdoOffline;
    private JLabel lblPreview;
    private JPanel mouseArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new CourseRegistrationEventStarter().frame.setVisible(true));
    }

    public CourseRegistrationEventStarter() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Workbook 1 - 수강신청 이벤트");
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("이름");
        lblName.setBounds(60, 45, 90, 30);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(165, 45, 260, 30);
        frame.getContentPane().add(txtName);

        JLabel lblCourse = new JLabel("과목");
        lblCourse.setBounds(60, 95, 90, 30);
        frame.getContentPane().add(lblCourse);

        cboCourse = new JComboBox<>(new String[]{
                "선택하세요", "Java 응용", "데이터분석", "AI 기초", "IoT 실습"
        });
        cboCourse.setBounds(165, 95, 260, 30);
        frame.getContentPane().add(cboCourse);

        JLabel lblTime = new JLabel("시간대");
        lblTime.setBounds(60, 145, 90, 30);
        frame.getContentPane().add(lblTime);

        chkMorning = new JCheckBox("오전");
        chkMorning.setBounds(165, 145, 80, 30);
        frame.getContentPane().add(chkMorning);

        chkAfternoon = new JCheckBox("오후");
        chkAfternoon.setBounds(255, 145, 80, 30);
        frame.getContentPane().add(chkAfternoon);

        JLabel lblType = new JLabel("수업 방식");
        lblType.setBounds(60, 195, 90, 30);
        frame.getContentPane().add(lblType);

        rdoOnline = new JRadioButton("온라인");
        rdoOnline.setBounds(165, 195, 90, 30);
        frame.getContentPane().add(rdoOnline);

        rdoOffline = new JRadioButton("대면");
        rdoOffline.setBounds(265, 195, 90, 30);
        frame.getContentPane().add(rdoOffline);

        ButtonGroup group = new ButtonGroup();
        group.add(rdoOnline);
        group.add(rdoOffline);

        lblPreview = new JLabel("이벤트를 연결하면 선택 내용이 표시됩니다.");
        lblPreview.setBounds(60, 255, 470, 30);
        frame.getContentPane().add(lblPreview);

        mouseArea = new JPanel();
        mouseArea.setBorder(BorderFactory.createTitledBorder("Mouse Event Area"));
        mouseArea.setBounds(60, 305, 365, 70);
        frame.getContentPane().add(mouseArea);

        JButton btnApply = new JButton("신청");
        btnApply.setBounds(155, 400, 100, 35);
        frame.getContentPane().add(btnApply);

        JButton btnReset = new JButton("초기화");
        btnReset.setBounds(275, 400, 100, 35);
        frame.getContentPane().add(btnReset);

        // TODO 1: btnApply에 ActionListener를 연결하세요.
        // TODO 2: btnReset에 ActionListener를 연결하세요.
        // TODO 3: cboCourse, chkMorning, chkAfternoon에 ItemListener를 연결하세요.
        // TODO 4: rdoOnline, rdoOffline에 ActionListener를 연결하세요.
        // TODO 5: txtName에 KeyListener와 FocusListener를 연결하세요.
        // TODO 6: mouseArea에 MouseListener 또는 MouseAdapter를 연결하세요.
        // TODO 7: updatePreview(), resetForm(), applyCourse() 메서드를 구현하세요.
    }
}
