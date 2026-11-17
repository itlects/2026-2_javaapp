package week07.reservation.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import week07.reservation.dto.ReservationDTO;
import week07.reservation.service.ReservationService;

public class ReservationManagerFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final ReservationService service = new ReservationService();

    private JPanel contentPane;
    private JTextField tfId;
    private JTextField tfCustomer;
    private JTextField tfDate;
    private JTextField tfTime;
    private JComboBox<String> cbStatus;
    private JTable table;

    private final DefaultTableModel model = new DefaultTableModel(
            new String[] { "예약번호", "고객명", "예약일", "시간", "상태" }, 0) {
        private static final long serialVersionUID = 1L;
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ReservationManagerFrame frame = new ReservationManagerFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ReservationManagerFrame() {
        setTitle("예약관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 520);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout(8, 8));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JPanel panelTop = new JPanel(new BorderLayout(8, 8));
        contentPane.add(panelTop, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(3, 4, 8, 8));
        panelTop.add(panelForm, BorderLayout.CENTER);

        panelForm.add(new JLabel("예약번호"));
        tfId = new JTextField();
        panelForm.add(tfId);

        panelForm.add(new JLabel("고객명"));
        tfCustomer = new JTextField();
        panelForm.add(tfCustomer);

        panelForm.add(new JLabel("예약일"));
        tfDate = new JTextField();
        panelForm.add(tfDate);

        panelForm.add(new JLabel("시간"));
        tfTime = new JTextField();
        panelForm.add(tfTime);

        panelForm.add(new JLabel("상태"));
        cbStatus = new JComboBox<>(new String[] { "예약완료", "대기", "취소" });
        panelForm.add(cbStatus);
        panelForm.add(new JLabel(""));
        panelForm.add(new JLabel(""));

        JPanel panelButton = new JPanel(new GridLayout(1, 5, 20, 0));
        panelTop.add(panelButton, BorderLayout.SOUTH);

        JButton btnSearch = new JButton("조회");
        JButton btnInsert = new JButton("등록");
        JButton btnUpdate = new JButton("수정");
        JButton btnDelete = new JButton("삭제");
        JButton btnClear = new JButton("초기화");
        for (JButton b : new JButton[] { btnSearch, btnInsert, btnUpdate, btnDelete, btnClear }) {
            panelButton.add(b);
        }

        JPanel panelList = new JPanel(new BorderLayout(0, 5));
        contentPane.add(panelList, BorderLayout.CENTER);
        panelList.add(new JLabel("예약목록"), BorderLayout.NORTH);

        table = new JTable(model);
        panelList.add(new JScrollPane(table), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> loadReservations());
        btnInsert.addActionListener(e -> insertReservation());
        btnUpdate.addActionListener(e -> updateReservation());
        btnDelete.addActionListener(e -> deleteReservation());
        btnClear.addActionListener(e -> clearForm());

        connectTableSelection();
        loadReservations();
    }

    private void loadReservations() {
        try {
            model.setRowCount(0);
            for (ReservationDTO r : service.getReservations()) {
                model.addRow(new Object[] {
                        r.reservationId(), r.customerName(), r.reservationDate(), r.reservationTime(), r.status()
                });
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private ReservationDTO readForm() {
        return new ReservationDTO(
                tfId.getText().trim(),
                tfCustomer.getText().trim(),
                tfDate.getText().trim(),
                tfTime.getText().trim(),
                cbStatus.getSelectedItem().toString());
    }

    private void insertReservation() {
        try {
            service.addReservation(readForm());
            clearForm();
            loadReservations();
            JOptionPane.showMessageDialog(this, "예약이 등록되었습니다.");
        } catch (Exception e) {
            showError(e);
        }
    }

    private void updateReservation() {
        try {
            service.updateReservation(readForm());
            clearForm();
            loadReservations();
            JOptionPane.showMessageDialog(this, "예약이 수정되었습니다.");
        } catch (Exception e) {
            showError(e);
        }
    }

    private void deleteReservation() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 예약을 선택하세요.");
            return;
        }

        String id = model.getValueAt(row, 0).toString();
        int answer = JOptionPane.showConfirmDialog(
                this, id + " 예약을 삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;

        try {
            service.deleteReservation(id);
            clearForm();
            loadReservations();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void connectTableSelection() {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row < 0) return;

            tfId.setText(model.getValueAt(row, 0).toString());
            tfCustomer.setText(model.getValueAt(row, 1).toString());
            tfDate.setText(model.getValueAt(row, 2).toString());
            tfTime.setText(model.getValueAt(row, 3).toString());
            cbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
            tfId.setEditable(false);
        });
    }

    private void clearForm() {
        tfId.setText("");
        tfCustomer.setText("");
        tfDate.setText("");
        tfTime.setText("");
        cbStatus.setSelectedIndex(0);
        tfId.setEditable(true);
        table.clearSelection();
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
    }
}
