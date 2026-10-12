package week04;

import javax.swing.*;

public class GroupLayoutDemo extends JFrame {

    public GroupLayoutDemo() {
        setTitle("GroupLayout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel idLabel = new JLabel("아이디");
        JLabel pwLabel = new JLabel("비밀번호");
        JLabel nameLabel = new JLabel("이름");

        JTextField idField = new JTextField();
        JPasswordField pwField = new JPasswordField();
        JTextField nameField = new JTextField();

        JButton okButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(idLabel)
                        .addComponent(pwLabel)
                        .addComponent(nameLabel))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(idField)
                        .addComponent(pwField)
                        .addComponent(nameField)))
                .addGroup(GroupLayout.Alignment.TRAILING,
                    layout.createSequentialGroup()
                        .addComponent(okButton)
                        .addComponent(cancelButton))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabel)
                    .addComponent(idField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(pwLabel)
                    .addComponent(pwField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
        );

        add(panel);
        setSize(520, 260);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new GroupLayoutDemo().setVisible(true)
        );
    }
}
