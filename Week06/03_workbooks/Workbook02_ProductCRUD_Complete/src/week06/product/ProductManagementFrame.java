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
        setTitle("6주차 워크북 2 — 상품관리 CRUD 완성본");
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
        try {
            products = dao.findAll();

            model.setRowCount(0);

            for (Product p : products) {
                model.addRow(new Object[] {
                        p.productId(),
                        p.productName(),
                        p.category(),
                        String.format("%,d", p.price()),
                        p.stock(),
                        nvl(p.maker())
                });
            }

        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    private void insertProduct() {
        Product p = readForm();

        if (p == null) {
            return;
        }

        try {
            int count = dao.insert(p);

            if (count == 1) {
                showInfo("상품이 등록되었습니다.");
                loadProducts();
                clearForm();
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            showWarning("이미 존재하는 상품ID입니다.");

        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    private void updateProduct() {
        int row = table.getSelectedRow();

        if (row < 0) {
            showWarning("수정할 상품을 선택하세요.");
            return;
        }

        Product p = readForm();

        if (p == null) {
            return;
        }

        try {
            int count = dao.update(p);

            if (count == 1) {
                showInfo("상품 정보가 수정되었습니다.");
                loadProducts();
                clearForm();
            } else {
                showWarning("수정할 상품을 찾지 못했습니다.");
            }

        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();

        if (row < 0) {
            showWarning("삭제할 상품을 선택하세요.");
            return;
        }

        String productId = products.get(row).productId();
        String productName = products.get(row).productName();

        int answer = JOptionPane.showConfirmDialog(
                this,
                productName + " 상품을 삭제할까요?",
                "삭제 확인",
                JOptionPane.YES_NO_OPTION
        );

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int count = dao.delete(productId);

            if (count == 1) {
                showInfo("상품이 삭제되었습니다.");
                loadProducts();
                clearForm();
            } else {
                showWarning("삭제할 상품을 찾지 못했습니다.");
            }

        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    private void showSelectedProduct() {
        int row = table.getSelectedRow();

        if (row < 0 || row >= products.size()) {
            return;
        }

        Product p = products.get(row);

        idField.setText(p.productId());
        idField.setEditable(false);

        nameField.setText(p.productName());
        categoryField.setText(p.category());
        priceField.setText(String.valueOf(p.price()));
        stockField.setText(String.valueOf(p.stock()));
        makerField.setText(nvl(p.maker()));
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
            return invalid(
                    "상품ID는 영문/숫자 1~10자로 입력하세요. 예) P011",
                    idField
            );
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

        return new Product(
                id,
                name,
                category,
                price,
                stock,
                maker.isEmpty() ? null : maker
        );
    }

    private static Integer parseNonNegative(String text) {
        try {
            int value = Integer.parseInt(
                    text.trim().replace(",", "")
            );

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
        JOptionPane.showMessageDialog(
                this,
                message,
                "알림",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "입력 확인",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showDbError(SQLException ex) {
        JOptionPane.showMessageDialog(
                this,
                "DB 처리 중 오류가 발생했습니다.\n" + ex.getMessage(),
                "DB 오류",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new ProductManagementFrame().setVisible(true)
        );
    }
}
