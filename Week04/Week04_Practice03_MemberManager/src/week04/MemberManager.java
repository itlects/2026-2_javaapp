package week04;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemberManager extends JFrame {
    private final JTextField id = new JTextField();
    private final JTextField name = new JTextField();
    private final JTextField phone = new JTextField();
    private final JComboBox<String> grade = new JComboBox<>(new String[]{"일반","VIP","VVIP"});
    private final DefaultTableModel model = new DefaultTableModel(
        new String[]{"회원번호","이름","등급","연락처"}, 0);
    private final JTable table = new JTable(model);

    public MemberManager() {
        setTitle("회원 데이터 관리");
        setSize(820, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel input = new JPanel(new GridLayout(2, 4, 6, 6));
        input.add(new JLabel("회원번호")); input.add(id);
        input.add(new JLabel("이름")); input.add(name);
        input.add(new JLabel("등급")); input.add(grade);
        input.add(new JLabel("연락처")); input.add(phone);

        JPanel buttons = new JPanel();
        JButton add = new JButton("등록"), update = new JButton("수정"),
                delete = new JButton("삭제"), clear = new JButton("초기화");
        buttons.add(add); buttons.add(update); buttons.add(delete); buttons.add(clear);

        JPanel north = new JPanel(new BorderLayout());
        north.add(input, BorderLayout.CENTER); north.add(buttons, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> {
            if (id.getText().isBlank() || name.getText().isBlank()) return;
            model.addRow(new Object[]{id.getText(), name.getText(), grade.getSelectedItem(), phone.getText()});
            clearForm();
        });
        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                id.setText(model.getValueAt(r,0).toString());
                name.setText(model.getValueAt(r,1).toString());
                grade.setSelectedItem(model.getValueAt(r,2));
                phone.setText(model.getValueAt(r,3).toString());
            }
        });
        update.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                model.setValueAt(id.getText(), r, 0);
                model.setValueAt(name.getText(), r, 1);
                model.setValueAt(grade.getSelectedItem(), r, 2);
                model.setValueAt(phone.getText(), r, 3);
            }
        });
        delete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) model.removeRow(r);
        });
        clear.addActionListener(e -> clearForm());
    }
    private void clearForm() {
        id.setText(""); name.setText(""); phone.setText(""); grade.setSelectedIndex(0);
        table.clearSelection();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemberManager().setVisible(true));
    }
}
