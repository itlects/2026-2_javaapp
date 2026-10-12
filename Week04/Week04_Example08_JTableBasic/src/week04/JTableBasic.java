package week04;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JTableBasic extends JFrame {
    private final DefaultTableModel model;
    public JTableBasic() {
        setTitle("JTable Basic");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] cols = {"회원번호","이름","성별","등급","연락처"};
        model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"M001","홍길동","남","일반","010-1111-1111"});
        model.addRow(new Object[]{"M002","김영희","여","VIP","010-2222-2222"});

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton add = new JButton("샘플 행 추가");
        add.addActionListener(e -> model.addRow(
            new Object[]{"M003","이순신","남","VVIP","010-3333-3333"}));
        add(add, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JTableBasic().setVisible(true));
    }
}
