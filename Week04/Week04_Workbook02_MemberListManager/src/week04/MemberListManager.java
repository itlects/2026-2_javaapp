package week04;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class MemberListManager extends JFrame {
    private final DefaultTableModel model = new DefaultTableModel(
        new String[]{"회원번호","이름","성별","등급","연락처","가입일"}, 0);
    private final JTable table = new JTable(model);
    private final JTextField search = new JTextField(15);
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

    public MemberListManager() {
        setTitle("회원목록 관리 워크북");
        setSize(900, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        table.setRowSorter(sorter);

        JPanel searchPanel = new JPanel();
        JButton searchBtn = new JButton("검색"), allBtn = new JButton("전체보기");
        searchPanel.add(new JLabel("이름 검색")); searchPanel.add(search);
        searchPanel.add(searchBtn); searchPanel.add(allBtn);

        model.addRow(new Object[]{"M001","홍길동","남","일반","010-1111-1111","2026-09-01"});
        model.addRow(new Object[]{"M002","김영희","여","VIP","010-2222-2222","2026-09-03"});

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JLabel count = new JLabel();
        updateCount(count);
        add(count, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> {
            String q = search.getText().trim();
            sorter.setRowFilter(q.isEmpty() ? null : RowFilter.regexFilter("(?i)" + q, 1));
        });
        allBtn.addActionListener(e -> {
            search.setText("");
            sorter.setRowFilter(null);
        });
    }
    private void updateCount(JLabel label) {
        label.setText("총 회원 수: " + model.getRowCount() + "명");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemberListManager().setVisible(true));
    }
}
