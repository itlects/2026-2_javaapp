package week03.workbook01;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

public class CourseRegistrationEventSolution {
    private JFrame frame;
    private JTextField txtName;
    private JComboBox<String> cboCourse;
    private JCheckBox chkMorning;
    private JCheckBox chkAfternoon;
    private JRadioButton rdoOnline;
    private JRadioButton rdoOffline;
    private JLabel lblPreview;
    private JPanel mouseArea;
    private final ButtonGroup group = new ButtonGroup();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new CourseRegistrationEventSolution().frame.setVisible(true));
    }

    public CourseRegistrationEventSolution() {
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

        group.add(rdoOnline);
        group.add(rdoOffline);

        lblPreview = new JLabel("선택 내용을 확인하세요.");
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

        btnApply.addActionListener(e -> applyCourse());
        btnReset.addActionListener(e -> resetForm());

        cboCourse.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updatePreview();
            }
        });
        chkMorning.addItemListener(e -> updatePreview());
        chkAfternoon.addItemListener(e -> updatePreview());
        rdoOnline.addActionListener(e -> updatePreview());
        rdoOffline.addActionListener(e -> updatePreview());

        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updatePreview();
            }
        });

        txtName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtName.setBackground(new Color(255, 255, 220));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtName.setBackground(Color.WHITE);
                if (txtName.getText().trim().isEmpty()) {
                    lblPreview.setText("이름을 입력하세요.");
                }
            }
        });

        mouseArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseArea.setBackground(new Color(220, 245, 255));
                lblPreview.setText("MouseEvent: mouseEntered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseArea.setBackground(null);
                updatePreview();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                lblPreview.setText("MouseEvent: click count = " + e.getClickCount());
            }
        });
    }

    private void updatePreview() {
        String name = txtName.getText().trim();
        String time = "";
        if (chkMorning.isSelected()) time += "오전 ";
        if (chkAfternoon.isSelected()) time += "오후 ";

        String type = rdoOnline.isSelected() ? "온라인"
                : rdoOffline.isSelected() ? "대면" : "미선택";

        lblPreview.setText(
                "이름: " + (name.isEmpty() ? "(미입력)" : name)
                + " / 과목: " + cboCourse.getSelectedItem()
                + " / 시간: " + (time.isBlank() ? "미선택" : time)
                + " / 방식: " + type
        );
    }

    private void applyCourse() {
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "이름을 입력하세요.");
            txtName.requestFocus();
            return;
        }

        if (cboCourse.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(frame, "과목을 선택하세요.");
            cboCourse.requestFocus();
            return;
        }

        if (!chkMorning.isSelected() && !chkAfternoon.isSelected()) {
            JOptionPane.showMessageDialog(frame, "시간대를 하나 이상 선택하세요.");
            return;
        }

        if (!rdoOnline.isSelected() && !rdoOffline.isSelected()) {
            JOptionPane.showMessageDialog(frame, "수업 방식을 선택하세요.");
            return;
        }

        JOptionPane.showMessageDialog(frame, "수강신청이 완료되었습니다.\n" + lblPreview.getText());
    }

    private void resetForm() {
        txtName.setText("");
        cboCourse.setSelectedIndex(0);
        chkMorning.setSelected(false);
        chkAfternoon.setSelected(false);
        group.clearSelection();
        mouseArea.setBackground(null);
        lblPreview.setText("입력값을 초기화했습니다.");
        txtName.requestFocus();
    }
}
