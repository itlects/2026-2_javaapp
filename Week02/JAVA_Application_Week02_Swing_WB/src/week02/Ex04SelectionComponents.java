package week02;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class Ex04SelectionComponents {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ex04 - 선택 컴포넌트");
            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JRadioButton maleButton = new JRadioButton("남");
            JRadioButton femaleButton = new JRadioButton("여");
            ButtonGroup genderGroup = new ButtonGroup();
            genderGroup.add(maleButton);
            genderGroup.add(femaleButton);
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleButton);
            genderPanel.add(femaleButton);
            panel.add(new JLabel("성별"));
            panel.add(genderPanel);
            panel.add(new JLabel("학과"));
            panel.add(new JComboBox<>(new String[] {"컴퓨터소프트웨어과", "AI융합과", "전자과"}));
            panel.add(new JLabel("수신 동의"));
            panel.add(new JCheckBox("이메일 안내를 받겠습니다."));
            panel.add(new JLabel("상태"));
            panel.add(new JLabel("원하는 값을 선택해 보세요."));
            frame.add(panel);
            frame.setSize(520, 280);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
