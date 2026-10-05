package week03.example02;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import javax.swing.*;

public class ItemEventExample {
    private JFrame frame;
    private JLabel lblResult;
    private JCheckBox chkJava;
    private JCheckBox chkPython;
    private JComboBox<String> cboMajor;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ItemEventExample().frame.setVisible(true));
    }

    public ItemEventExample() {
        frame = new JFrame("예제2 - ItemEvent");
        frame.setBounds(100, 100, 520, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        cboMajor = new JComboBox<>(new String[]{"학과 선택", "컴퓨터소프트웨어과", "AI융합과", "정보통신과"});
        cboMajor.setBounds(60, 45, 220, 30);
        frame.getContentPane().add(cboMajor);

        chkJava = new JCheckBox("Java");
        chkJava.setBounds(60, 95, 80, 30);
        frame.getContentPane().add(chkJava);

        chkPython = new JCheckBox("Python");
        chkPython.setBounds(150, 95, 90, 30);
        frame.getContentPane().add(chkPython);

        lblResult = new JLabel("선택 결과가 여기에 표시됩니다.");
        lblResult.setBounds(60, 155, 390, 30);
        frame.getContentPane().add(lblResult);

        cboMajor.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) updateResult();
        });
        chkJava.addItemListener(e -> updateResult());
        chkPython.addItemListener(e -> updateResult());
    }

    private void updateResult() {
        String skills = "";
        if (chkJava.isSelected()) skills += "Java ";
        if (chkPython.isSelected()) skills += "Python ";
        lblResult.setText("학과: " + cboMajor.getSelectedItem() + " / 관심: " + skills);
    }
}