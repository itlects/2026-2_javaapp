package week04;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class MemberListManager extends JFrame {

    private final DefaultTableModel model = new DefaultTableModel(
            new String[] {"회원번호", "이름", "성별", "등급", "연락처", "가입일"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(model);
    private final TableRowSorter<DefaultTableModel> sorter =
            new TableRowSorter<>(model);

    // 검색 영역
    private final JTextField searchField = new JTextField(16);
    private final JComboBox<String> searchGradeBox =
            new JComboBox<>(new String[] {"전체", "일반", "VIP", "VVIP"});

    // 상세 입력 영역
    private final JTextField idField = new JTextField(12);
    private final JTextField nameField = new JTextField(12);

    private final JRadioButton maleRadio = new JRadioButton("남");
    private final JRadioButton femaleRadio = new JRadioButton("여");
    private final ButtonGroup genderGroup = new ButtonGroup();

    private final JComboBox<String> gradeBox =
            new JComboBox<>(new String[] {"일반", "VIP", "VVIP"});

    private final JTextField phoneField = new JTextField(14);
    private final JTextField joinDateField = new JTextField(10);

    private final JLabel countLabel = new JLabel();
    private final JLabel statusLabel = new JLabel("회원 정보를 관리하세요.");

    public MemberListManager() {
        setTitle("회원목록 관리 워크북");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBorder(BorderFactory.createEmptyBorder(14, 16, 12, 16));
        setContentPane(content);

        // -------------------------------------------------
        // 1. 제목 + 검색 영역
        // -------------------------------------------------
        JLabel titleLabel = new JLabel("회원 관리");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton searchButton = new JButton("검색");
        JButton showAllButton = new JButton("전체보기");

        searchPanel.add(new JLabel("검색"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("등급"));
        searchPanel.add(searchGradeBox);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        JPanel northPanel = new JPanel(new BorderLayout(0, 10));
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.CENTER);

        // -------------------------------------------------
        // 2. 회원 목록 JTable
        // -------------------------------------------------
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);

        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(55);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(130);
        table.getColumnModel().getColumn(5).setPreferredWidth(95);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(760, 230));

        // -------------------------------------------------
        // 3. 상세 입력 영역
        // -------------------------------------------------
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);

        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("회원 상세정보"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(detailPanel, gbc, 0, 0, "회원번호", idField);
        addField(detailPanel, gbc, 0, 2, "이름", nameField);

        addField(detailPanel, gbc, 1, 0, "성별", genderPanel);
        addField(detailPanel, gbc, 1, 2, "등급", gradeBox);

        addField(detailPanel, gbc, 2, 0, "연락처", phoneField);
        addField(detailPanel, gbc, 2, 2, "가입일", joinDateField);

        // -------------------------------------------------
        // 4. CRUD + 파일 버튼 영역
        // -------------------------------------------------
        JButton addButton = new JButton("등록");
        JButton updateButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        JButton clearButton = new JButton("초기화");
        JButton saveFileButton = new JButton("파일 저장");
        JButton loadFileButton = new JButton("파일 불러오기");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(14));
        buttonPanel.add(saveFileButton);
        buttonPanel.add(loadFileButton);

        JPanel detailAndButtons = new JPanel(new BorderLayout(0, 6));
        detailAndButtons.add(detailPanel, BorderLayout.CENTER);
        detailAndButtons.add(buttonPanel, BorderLayout.SOUTH);

        // 목록과 상세영역을 세로 분할
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                tableScroll,
                detailAndButtons);
        splitPane.setResizeWeight(0.52);
        splitPane.setBorder(null);

        // -------------------------------------------------
        // 5. 상태 영역
        // -------------------------------------------------
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(countLabel, BorderLayout.WEST);
        statusPanel.add(statusLabel, BorderLayout.EAST);

        content.add(northPanel, BorderLayout.NORTH);
        content.add(splitPane, BorderLayout.CENTER);
        content.add(statusPanel, BorderLayout.SOUTH);

        // -------------------------------------------------
        // 6. 샘플 데이터
        // -------------------------------------------------
        model.addRow(new Object[] {
                "M001", "홍길동", "남", "일반",
                "010-1111-1111", "2026-09-01"
        });
        model.addRow(new Object[] {
                "M002", "김영희", "여", "VIP",
                "010-2222-2222", "2026-09-03"
        });
        model.addRow(new Object[] {
                "M003", "이순신", "남", "VVIP",
                "010-3333-3333", "2026-09-05"
        });
        model.addRow(new Object[] {
                "M004", "박민수", "남", "일반",
                "010-4444-4444", "2026-09-07"
        });
        model.addRow(new Object[] {
                "M005", "최지우", "여", "VIP",
                "010-5555-5555", "2026-09-09"
        });

        updateCount();

        // -------------------------------------------------
        // 7. 이벤트 처리
        // -------------------------------------------------
        searchButton.addActionListener(e -> applyFilter());

        showAllButton.addActionListener(e -> {
            searchField.setText("");
            searchGradeBox.setSelectedIndex(0);
            sorter.setRowFilter(null);
            updateCount();
            statusLabel.setText("전체 회원을 표시합니다.");
        });

        searchField.addActionListener(e -> applyFilter());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRowToForm();
            }
        });

        addButton.addActionListener(e -> addMember());
        updateButton.addActionListener(e -> updateMember());
        deleteButton.addActionListener(e -> deleteMember());
        clearButton.addActionListener(e -> clearForm());

        saveFileButton.addActionListener(e -> saveMembersToFile());
        loadFileButton.addActionListener(e -> loadMembersFromFile());

        // -------------------------------------------------
        // 8. 프레임 크기
        // -------------------------------------------------
        setMinimumSize(new Dimension(760, 620));
        setSize(900, 680);
        splitPane.setDividerLocation(260);
        setLocationRelativeTo(null);
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int labelColumn,
            String labelText,
            Component component) {

        gbc.gridy = row;

        gbc.gridx = labelColumn;
        gbc.weightx = 0.0;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = labelColumn + 1;
        gbc.weightx = 1.0;
        panel.add(component, gbc);
    }

    private void applyFilter() {
        String keyword = searchField.getText().trim();
        String selectedGrade = (String) searchGradeBox.getSelectedItem();

        RowFilter<DefaultTableModel, Integer> nameFilter = null;
        RowFilter<DefaultTableModel, Integer> gradeFilter = null;

        if (!keyword.isEmpty()) {
            nameFilter = RowFilter.regexFilter(
                    "(?i)" + Pattern.quote(keyword), 1);
        }

        if (selectedGrade != null && !"전체".equals(selectedGrade)) {
            gradeFilter = RowFilter.regexFilter(
                    "^" + Pattern.quote(selectedGrade) + "$", 3);
        }

        if (nameFilter != null && gradeFilter != null) {
            sorter.setRowFilter(RowFilter.andFilter(
                    java.util.List.of(nameFilter, gradeFilter)));
        } else if (nameFilter != null) {
            sorter.setRowFilter(nameFilter);
        } else if (gradeFilter != null) {
            sorter.setRowFilter(gradeFilter);
        } else {
            sorter.setRowFilter(null);
        }

        updateCount();
        statusLabel.setText("검색/필터 결과를 표시합니다.");
    }

    private void loadSelectedRowToForm() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            return;
        }

        int row = table.convertRowIndexToModel(viewRow);

        idField.setText(valueAt(row, 0));
        nameField.setText(valueAt(row, 1));

        String gender = valueAt(row, 2);
        if ("남".equals(gender)) {
            maleRadio.setSelected(true);
        } else if ("여".equals(gender)) {
            femaleRadio.setSelected(true);
        } else {
            genderGroup.clearSelection();
        }

        gradeBox.setSelectedItem(valueAt(row, 3));
        phoneField.setText(valueAt(row, 4));
        joinDateField.setText(valueAt(row, 5));

        statusLabel.setText("선택 회원: " + valueAt(row, 0));
    }

    private String valueAt(int row, int column) {
        Object value = model.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    private String selectedGender() {
        if (maleRadio.isSelected()) {
            return "남";
        }
        if (femaleRadio.isSelected()) {
            return "여";
        }
        return "";
    }

    private boolean validateForm() {
        if (idField.getText().trim().isEmpty()) {
            showWarning("회원번호를 입력하세요.");
            idField.requestFocusInWindow();
            return false;
        }

        if (nameField.getText().trim().isEmpty()) {
            showWarning("이름을 입력하세요.");
            nameField.requestFocusInWindow();
            return false;
        }

        if (selectedGender().isEmpty()) {
            showWarning("성별을 선택하세요.");
            return false;
        }

        if (phoneField.getText().trim().isEmpty()) {
            showWarning("연락처를 입력하세요.");
            phoneField.requestFocusInWindow();
            return false;
        }

        if (joinDateField.getText().trim().isEmpty()) {
            showWarning("가입일을 입력하세요. 예: 2026-10-06");
            joinDateField.requestFocusInWindow();
            return false;
        }

        return true;
    }

    private void addMember() {
        if (!validateForm()) {
            return;
        }

        String memberId = idField.getText().trim();

        if (findModelRowById(memberId) >= 0) {
            showWarning("이미 존재하는 회원번호입니다: " + memberId);
            return;
        }

        model.addRow(new Object[] {
                memberId,
                nameField.getText().trim(),
                selectedGender(),
                gradeBox.getSelectedItem(),
                phoneField.getText().trim(),
                joinDateField.getText().trim()
        });

        clearForm();
        applyFilter();
        statusLabel.setText("회원 등록 완료: " + memberId);
    }

    private void updateMember() {
        int viewRow = table.getSelectedRow();

        if (viewRow < 0) {
            showWarning("수정할 회원을 목록에서 선택하세요.");
            return;
        }

        if (!validateForm()) {
            return;
        }

        int row = table.convertRowIndexToModel(viewRow);
        String originalId = valueAt(row, 0);
        String newId = idField.getText().trim();

        int duplicateRow = findModelRowById(newId);
        if (duplicateRow >= 0 && duplicateRow != row) {
            showWarning("이미 존재하는 회원번호입니다: " + newId);
            return;
        }

        model.setValueAt(newId, row, 0);
        model.setValueAt(nameField.getText().trim(), row, 1);
        model.setValueAt(selectedGender(), row, 2);
        model.setValueAt(gradeBox.getSelectedItem(), row, 3);
        model.setValueAt(phoneField.getText().trim(), row, 4);
        model.setValueAt(joinDateField.getText().trim(), row, 5);

        applyFilter();
        statusLabel.setText("회원 수정 완료: " + originalId);
    }

    private void deleteMember() {
        int viewRow = table.getSelectedRow();

        if (viewRow < 0) {
            showWarning("삭제할 회원을 목록에서 선택하세요.");
            return;
        }

        int row = table.convertRowIndexToModel(viewRow);
        String memberId = valueAt(row, 0);

        int answer = JOptionPane.showConfirmDialog(
                this,
                memberId + " 회원을 삭제할까요?",
                "삭제 확인",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        model.removeRow(row);
        clearForm();
        applyFilter();
        statusLabel.setText("회원 삭제 완료: " + memberId);
    }

    private int findModelRowById(String memberId) {
        for (int row = 0; row < model.getRowCount(); row++) {
            if (memberId.equalsIgnoreCase(valueAt(row, 0))) {
                return row;
            }
        }
        return -1;
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        genderGroup.clearSelection();
        gradeBox.setSelectedIndex(0);
        phoneField.setText("");
        joinDateField.setText("");

        table.clearSelection();
        idField.requestFocusInWindow();

        statusLabel.setText("입력 항목을 초기화했습니다.");
    }

    private void updateCount() {
        countLabel.setText(
                "총 회원 수: " + model.getRowCount()
                + "명  (현재 표시: " + table.getRowCount() + "명)");
    }

    private void saveMembersToFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("회원 목록 저장");
        chooser.setSelectedFile(new File("members.csv"));
        chooser.setFileFilter(
                new FileNameExtensionFilter("CSV 파일 (*.csv)", "csv"));

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();

        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getParentFile(), file.getName() + ".csv");
        }

        if (file.exists()) {
            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "파일이 이미 존재합니다. 덮어쓸까요?",
                    "덮어쓰기 확인",
                    JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try (BufferedWriter writer =
                Files.newBufferedWriter(
                        file.toPath(),
                        StandardCharsets.UTF_8)) {

            writer.write("회원번호,이름,성별,등급,연락처,가입일");
            writer.newLine();

            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    if (col > 0) {
                        writer.write(",");
                    }
                    writer.write(csvEscape(valueAt(row, col)));
                }
                writer.newLine();
            }

            statusLabel.setText("파일 저장 완료: " + file.getName());

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "파일 저장 중 오류가 발생했습니다.\n" + ex.getMessage(),
                    "저장 오류",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadMembersFromFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("회원 목록 불러오기");
        chooser.setFileFilter(
                new FileNameExtensionFilter("CSV 파일 (*.csv)", "csv"));

        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();

        try (BufferedReader reader =
                Files.newBufferedReader(
                        file.toPath(),
                        StandardCharsets.UTF_8)) {

            java.util.List<String[]> rows = new java.util.ArrayList<>();

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    if (line.startsWith("회원번호,")) {
                        continue;
                    }
                }

                if (line.isBlank()) {
                    continue;
                }

                java.util.List<String> values = parseCsvLine(line);

                if (values.size() != model.getColumnCount()) {
                    throw new IOException(
                            "열 개수가 올바르지 않은 행이 있습니다: " + line);
                }

                rows.add(values.toArray(new String[0]));
            }

            model.setRowCount(0);
            for (String[] row : rows) {
                model.addRow(row);
            }

            clearForm();
            searchField.setText("");
            searchGradeBox.setSelectedIndex(0);
            sorter.setRowFilter(null);
            updateCount();

            statusLabel.setText("파일 불러오기 완료: " + file.getName());

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "파일 불러오기 중 오류가 발생했습니다.\n" + ex.getMessage(),
                    "불러오기 오류",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String csvEscape(String value) {
        String escaped = value.replace(""", """");
        if (escaped.contains(",")
                || escaped.contains(""")
                || escaped.contains("\n")
                || escaped.contains("\r")) {
            return """ + escaped + """;
        }
        return escaped;
    }

    private java.util.List<String> parseCsvLine(String line) {
        java.util.List<String> values = new java.util.ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                if (quoted && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    quoted = !quoted;
                }
            } else if (ch == ',' && !quoted) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        values.add(current.toString());
        return values;
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "입력 확인",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new MemberListManager().setVisible(true));
    }
}
