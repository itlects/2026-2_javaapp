package week06.product;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * [워크북 2] 상품관리 GUI
 * 화면 구성과 입력 검증(readForm)은 완성되어 있다.
 * 회원관리 MemberManagementFrame의 흐름을 참고하여 TODO A~E를 완성한다.
 */
public class ProductManagementFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField stockField = new JTextField();
    private final JTextField makerField = new JTextField();

    private final DefaultTableModel model = new DefaultTableModel(
            new String[] {"상품ID", "상품명", "분류", "가격", "재고", "제조사"}, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);

    private final ProductDAO dao = new ProductDAO();
    private List<Product> products = new ArrayList<>();

    public ProductManagementFrame() {
        setTitle("6주차 워크북 2 — 상품관리");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(860, 500);
        setLocationRelativeTo(null);

        add(createFormPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedProduct();
            }
        });

        loadProducts();
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel(new GridLayout(3, 4, 8, 6));
        form.setBorder(BorderFactory.createTitledBorder("상품 정보 (* 필수)"));
        form.add(new JLabel("상품ID * (예: P011)"));
        form.add(idField);
        form.add(new JLabel("상품명 *"));
        form.add(nameField);
        form.add(new JLabel("분류 *"));
        form.add(categoryField);
        form.add(new JLabel("가격 *"));
        form.add(priceField);
        form.add(new JLabel("재고 *"));
        form.add(stockField);
        form.add(new JLabel("제조사"));
        form.add(makerField);
        return form;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        JButton selectButton = new JButton("조회");
        JButton insertButton = new JButton("등록");
        JButton updateButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        JButton clearButton = new JButton("새로 입력");

        selectButton.addActionListener(e -> {
            loadProducts();
            clearForm();
        });
        insertButton.addActionListener(e -> insertProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        clearButton.addActionListener(e -> clearForm());

        panel.add(selectButton);
        panel.add(insertButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        return panel;
    }

    private void loadProducts() {
        // TODO A : dao.findAll() → model.setRowCount(0) → model.addRow(...)  (가격은 String.format("%,d", ...))
    }

    private void insertProduct() {
        // TODO B : readForm() → dao.insert() → 안내 → loadProducts() → clearForm()  (상품ID 중복 처리 포함)
        showWarning("TODO B: insertProduct()를 완성하세요.");
    }

    private void updateProduct() {
        // TODO C : 선택 확인 → readForm() → dao.update() → 결과(0/1) 안내 → loadProducts() → clearForm()
        showWarning("TODO C: updateProduct()를 완성하세요.");
    }

    private void deleteProduct() {
        // TODO D : 선택 확인 → showConfirmDialog → [예]일 때만 dao.delete() → loadProducts() → clearForm()
        showWarning("TODO D: deleteProduct()를 완성하세요.");
    }

    private void showSelectedProduct() {
        // TODO E : 선택한 행의 Product를 입력 필드에 표시 (상품ID 필드는 편집 불가, maker가 null이면 "")
    }

    private void clearForm() {
        table.clearSelection();
        idField.setEditable(true);
        idField.setText("");
        nameField.setText("");
        categoryField.setText("");
        priceField.setText("");
        stockField.setText("");
        makerField.setText("");
        idField.requestFocusInWindow();
    }

    private Product readForm() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String maker = makerField.getText().trim();

        if (!id.matches("[A-Za-z0-9]{1,10}")) {
            return invalid("상품ID는 영문/숫자 1~10자로 입력하세요. 예) P011", idField);
        }
        if (name.isEmpty() || name.length() > 100) {
            return invalid("상품명을 입력하세요. (100자 이하)", nameField);
        }
        if (category.isEmpty() || category.length() > 50) {
            return invalid("분류를 입력하세요. (50자 이하)", categoryField);
        }
        Integer price = parseNonNegative(priceField.getText());
        if (price == null) {
            return invalid("가격은 0 이상의 정수로 입력하세요.", priceField);
        }
        Integer stock = parseNonNegative(stockField.getText());
        if (stock == null) {
            return invalid("재고는 0 이상의 정수로 입력하세요.", stockField);
        }
        if (maker.length() > 50) {
            return invalid("제조사는 50자 이하로 입력하세요.", makerField);
        }
        return new Product(id, name, category, price, stock, maker.isEmpty() ? null : maker);
    }

    /** 0 이상의 정수면 그 값, 아니면 null (쉼표 허용: 35,000) */
    private static Integer parseNonNegative(String text) {
        try {
            int value = Integer.parseInt(text.trim().replace(",", ""));
            return value >= 0 ? value : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Product invalid(String message, Component field) {
        showWarning(message);
        field.requestFocusInWindow();
        return null;
    }

    private static String nvl(String value) {
        return value == null ? "" : value;
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "알림", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "입력 확인", JOptionPane.WARNING_MESSAGE);
    }

    private void showDbError(SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "DB 처리 중 오류가 발생했습니다.\n" + ex.getMessage(), "DB 오류", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductManagementFrame().setVisible(true));
    }
}
