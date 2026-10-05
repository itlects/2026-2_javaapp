package week03.practice03;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

public class EventMonitor {
    private JFrame frame;
    private JTextArea log;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new EventMonitor().frame.setVisible(true));
    }

    public EventMonitor() {
        frame = new JFrame("실습3 - Event Monitor");
        frame.setBounds(100, 100, 650, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnAction = new JButton("Action");
        btnAction.setBounds(40, 35, 110, 35);
        frame.getContentPane().add(btnAction);

        JCheckBox chkItem = new JCheckBox("Item");
        chkItem.setBounds(175, 35, 90, 35);
        frame.getContentPane().add(chkItem);

        JComboBox<String> combo = new JComboBox<>(new String[]{"Java", "Python", "Spring"});
        combo.setBounds(285, 35, 130, 35);
        frame.getContentPane().add(combo);

        JTextField txtKey = new JTextField();
        txtKey.setBounds(440, 35, 150, 35);
        frame.getContentPane().add(txtKey);

        JPanel mousePanel = new JPanel();
        mousePanel.setBorder(BorderFactory.createTitledBorder("Mouse Area"));
        mousePanel.setBounds(40, 90, 550, 80);
        frame.getContentPane().add(mousePanel);

        log = new JTextArea();
        log.setEditable(false);
        JScrollPane scroll = new JScrollPane(log);
        scroll.setBounds(40, 195, 550, 200);
        frame.getContentPane().add(scroll);

        btnAction.addActionListener(e -> append("ActionEvent", "Action 버튼 클릭"));

        chkItem.addItemListener(e ->
            append("ItemEvent", "체크박스 " + (chkItem.isSelected() ? "선택" : "해제")));

        combo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                append("ItemEvent", "콤보 선택 = " + combo.getSelectedItem());
        });

        txtKey.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                append("KeyEvent", "keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        txtKey.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                append("FocusEvent", "입력창 focusGained");
            }
            @Override public void focusLost(FocusEvent e) {
                append("FocusEvent", "입력창 focusLost");
            }
        });

        mousePanel.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                append("MouseEvent", "mouseEntered");
            }
            @Override public void mouseExited(MouseEvent e) {
                append("MouseEvent", "mouseExited");
            }
            @Override public void mouseClicked(MouseEvent e) {
                append("MouseEvent", "click count = " + e.getClickCount());
            }
        });
    }

    private void append(String type, String message) {
        log.append("[" + type + "] " + message + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }
}