package week04;

import java.awt.*;
import javax.swing.*;

public class CardLayoutDemo extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    public CardLayoutDemo() {
        setTitle("CardLayout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComboBox<String> selector =
                new JComboBox<>(new String[]{"로그인 화면", "회원가입 화면", "회원목록 화면"});

        cardPanel.add(createCard("로그인 화면", "아이디/비밀번호 입력 화면"), "LOGIN");
        cardPanel.add(createCard("회원가입 화면", "회원정보 등록 화면"), "SIGNUP");
        cardPanel.add(createCard("회원목록 화면", "JTable 회원목록 화면"), "LIST");

        selector.addActionListener(e -> {
            switch (selector.getSelectedIndex()) {
                case 0 -> cardLayout.show(cardPanel, "LOGIN");
                case 1 -> cardLayout.show(cardPanel, "SIGNUP");
                case 2 -> cardLayout.show(cardPanel, "LIST");
            }
        });

        add(selector, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        setSize(560, 340);
        setLocationRelativeTo(null);
    }

    private JPanel createCard(String title, String description) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        JLabel desc = new JLabel(description, SwingConstants.CENTER);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(desc, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new CardLayoutDemo().setVisible(true)
        );
    }
}
