package week06.member;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * 6주차 회원관리 GUI — 조회(SELECT) / 등록(INSERT) / 수정(UPDATE) / 삭제(DELETE)
 *
 * 데이터 흐름: 입력 필드 → readForm()으로 검증 → Member 객체 → MemberDAO → DB → loadMembers()로 JTable 갱신
 */
public class MemberManagementFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    // ---------------- 입력 필드
    private final JTextField idField = new JTextField();
    private final JTextField userIdField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField departmentField = new JTextField();
    private final JComboBox<String> gradeCombo = new JComboBox<>(new String[] {"", "1", "2", "3", "4"});

    // ---------------- 목록 (비밀번호는 화면에 표시하지 않음)
    private final DefaultTableModel model = new DefaultTableModel(
            new String[] {"번호", "아이디", "이름", "이메일", "전화", "학과", "학년"}, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // 표에서 직접 수정하지 않고 입력 필드로만 수정
        }
    };
    private final JTable table = new JTable(model);

    private final MemberDAO dao = new MemberDAO();
    private List<Member> members = new ArrayList<>(); // JTable 각 행에 해당하는 회원 (행 번호 = 인덱스)

    public MemberManagementFrame() {
        setTitle("6주차 회원관리 (Swing + JDBC CRUD)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);

        add(createFormPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedMember();
            }
        });

        loadMembers();
        clearForm();
    }

    // =========================================================== 화면 구성

    private JPanel createFormPanel() {
        JPanel form = new JPanel(new GridLayout(4, 4, 8, 6));
        form.setBorder(BorderFactory.createTitledBorder("회원 정보 (* 필수)"));

        form.add(new JLabel("번호 *"));
        form.add(idField);
        form.add(new JLabel("아이디 *"));
        form.add(userIdField);
        form.add(new JLabel("비밀번호 *"));
        form.add(passwordField);
        form.add(new JLabel("이름 *"));
        form.add(nameField);
        form.add(new JLabel("이메일"));
        form.add(emailField);
        form.add(new JLabel("전화 (010-0000-0000)"));
        form.add(phoneField);
        form.add(new JLabel("학과"));
        form.add(departmentField);
        form.add(new JLabel("학년"));
        form.add(gradeCombo);
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
            loadMembers();
            clearForm();
        });
        insertButton.addActionListener(e -> insertMember());
        updateButton.addActionListener(e -> updateMember());
        deleteButton.addActionListener(e -> deleteMember());
        clearButton.addActionListener(e -> clearForm());

        panel.add(selectButton);
        panel.add(insertButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        return panel;
    }

    // =========================================================== CRUD 이벤트 처리

    /** [조회] DB에서 다시 읽어 JTable을 새로 채운다 */
    private void loadMembers() {
        try {
            members = dao.findAll();
            model.setRowCount(0); // 기존 행을 지우지 않으면 같은 회원이 중복 표시됨
            for (Member m : members) {
                model.addRow(new Object[] {
                    m.id(), m.userId(), m.name(),
                    nvl(m.email()), nvl(m.phone()), nvl(m.department()),
                    m.grade() == null ? "" : m.grade()
                });
            }
        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    /** [등록] */
    private void insertMember() {
        Member member = readForm();
        if (member == null) {
            return; // 입력 오류 → 이미 메시지를 보여 줌
        }
        try {
            int count = dao.insert(member);
            showInfo(count + "명의 회원이 등록되었습니다.");
            loadMembers();
            clearForm();
        } catch (SQLIntegrityConstraintViolationException ex) {
            showWarning("이미 사용 중인 번호 또는 아이디입니다.\n다른 값을 입력하세요.");
        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    /** [수정] */
    private void updateMember() {
        if (table.getSelectedRow() < 0) {
            showWarning("수정할 회원을 목록에서 먼저 선택하세요.");
            return;
        }
        Member member = readForm();
        if (member == null) {
            return;
        }
        try {
            int count = dao.update(member);
            if (count == 0) {
                showWarning("번호 " + member.id() + " 회원이 없습니다. [조회]로 목록을 새로 고치세요.");
            } else {
                showInfo("회원 정보가 수정되었습니다.");
            }
            loadMembers();
            clearForm();
        } catch (SQLIntegrityConstraintViolationException ex) {
            showWarning("이미 다른 회원이 사용 중인 아이디입니다.");
        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    /** [삭제] 확인 대화상자에서 [예]를 선택한 경우에만 삭제 */
    private void deleteMember() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showWarning("삭제할 회원을 목록에서 먼저 선택하세요.");
            return;
        }
        Member target = members.get(row);
        int answer = JOptionPane.showConfirmDialog(this,
                target.id() + "번 " + target.name() + "(" + target.userId() + ") 회원을 삭제할까요?",
                "삭제 확인", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            int count = dao.delete(target.id());
            showInfo(count + "명의 회원이 삭제되었습니다.");
            loadMembers();
            clearForm();
        } catch (SQLException ex) {
            showDbError(ex);
        }
    }

    // =========================================================== 입력 필드 처리

    /** JTable에서 선택한 회원을 입력 필드에 표시 */
    private void showSelectedMember() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= members.size()) {
            return;
        }
        Member m = members.get(row);
        idField.setText(String.valueOf(m.id()));
        idField.setEditable(false); // 번호(기본키)는 수정하지 않음
        userIdField.setText(m.userId());
        passwordField.setText(m.password());
        nameField.setText(m.name());
        emailField.setText(nvl(m.email()));   // null → "" (setText(null)도 되지만 의도를 분명히)
        phoneField.setText(nvl(m.phone()));
        departmentField.setText(nvl(m.department()));
        gradeCombo.setSelectedItem(m.grade() == null ? "" : String.valueOf(m.grade()));
    }

    /** 입력 필드를 비우고 다음 회원 번호를 미리 채운다 */
    private void clearForm() {
        table.clearSelection();
        idField.setEditable(true);
        userIdField.setText("");
        passwordField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        departmentField.setText("");
        gradeCombo.setSelectedIndex(0);
        try {
            idField.setText(String.valueOf(dao.nextId()));
        } catch (SQLException ex) {
            idField.setText("");
        }
        userIdField.requestFocusInWindow();
    }

    /**
     * 입력값을 검증하여 Member 객체를 만든다.
     * 오류가 있으면 메시지를 보여 주고 null을 돌려준다.
     * 선택 입력 항목(이메일, 전화, 학과, 학년)은 비어 있으면 null → DB에 NULL로 저장.
     */
    private Member readForm() {
        String idText = idField.getText().trim();
        String userId = userIdField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String name = nameField.getText().trim();
        String email = emptyToNull(emailField.getText());
        String phone = emptyToNull(phoneField.getText());
        String department = emptyToNull(departmentField.getText());
        String gradeText = (String) gradeCombo.getSelectedItem();

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            return invalid("번호는 숫자로 입력하세요.", idField);
        }
        if (id <= 0) {
            return invalid("번호는 1 이상이어야 합니다.", idField);
        }
        if (!userId.matches("[A-Za-z0-9_]{2,30}")) {
            return invalid("아이디는 영문/숫자/_ 2~30자로 입력하세요.", userIdField);
        }
        if (password.isEmpty() || password.length() > 100) {
            return invalid("비밀번호를 입력하세요. (100자 이하)", passwordField);
        }
        if (name.isEmpty() || name.length() > 30) {
            return invalid("이름을 입력하세요. (30자 이하)", nameField);
        }
        if (email != null && (email.length() > 100 || !email.matches("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+"))) {
            return invalid("이메일 형식이 올바르지 않습니다. 예) hong@example.com", emailField);
        }
        if (phone != null && !phone.matches("\\d{2,3}-\\d{3,4}-\\d{4}")) {
            return invalid("전화번호 형식이 올바르지 않습니다. 예) 010-1234-5678", phoneField);
        }
        if (department != null && department.length() > 50) {
            return invalid("학과는 50자 이하로 입력하세요.", departmentField);
        }
        Integer grade = (gradeText == null || gradeText.isEmpty()) ? null : Integer.valueOf(gradeText);

        return new Member(id, userId, password, name, email, phone, department, grade);
    }

    // =========================================================== 보조 메서드

    private Member invalid(String message, Component field) {
        showWarning(message);
        field.requestFocusInWindow();
        return null;
    }

    /** null → "" (화면 표시용) */
    private static String nvl(String value) {
        return value == null ? "" : value;
    }

    /** "" 또는 공백 → null (DB 저장용) */
    private static String emptyToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
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
        SwingUtilities.invokeLater(() -> new MemberManagementFrame().setVisible(true));
    }
}
