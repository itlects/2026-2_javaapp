package week07.member.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import week07.member.dto.MemberDTO;
import week07.member.service.MemberService;

public class MemberManagerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final MemberService service =
            new MemberService();

    private final JTextField name =
            new JTextField(9);

    private final JTextField phone =
            new JTextField(10);

    private final JTextField email =
            new JTextField(14);

    private final JTextField grade =
            new JTextField(7);

    private final DefaultTableModel model =
            new DefaultTableModel(
                new String[] {
                    "ID",
                    "이름",
                    "전화",
                    "이메일",
                    "등급"
                },
                0
            );

    private final JTable table =
            new JTable(model);

    private int selectedId = -1;

    public MemberManagerFrame() {

        setTitle("Workbook01 회원관리");
        setSize(760, 440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel top = new JPanel();

        top.add(new JLabel("이름"));
        top.add(name);

        top.add(new JLabel("전화"));
        top.add(phone);

        top.add(new JLabel("이메일"));
        top.add(email);

        top.add(new JLabel("등급"));
        top.add(grade);

        JButton load =
                new JButton("조회");

        JButton add =
                new JButton("등록");

        JButton update =
                new JButton("수정");

        JButton del =
                new JButton("삭제");

        JButton clear =
                new JButton("초기화");

        JPanel bottom = new JPanel();

        for (JButton button :
                new JButton[] {
                    load,
                    add,
                    update,
                    del,
                    clear
                }) {

            bottom.add(button);
        }

        add(top, BorderLayout.NORTH);
        add(
            new JScrollPane(table),
            BorderLayout.CENTER
        );
        add(bottom, BorderLayout.SOUTH);

        // TODO 8:
        // 버튼 이벤트를 Service와 연결

        // TODO 9:
        // JTable 선택행 -> selectedId/입력창 반영

        // TODO 10:
        // 오류처리와 초기 목록 재조회
    }

    private MemberDTO read(int id) {

        return new MemberDTO(
            id,
            name.getText().trim(),
            phone.getText().trim(),
            email.getText().trim(),
            grade.getText().trim()
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
            () ->
                new MemberManagerFrame()
                    .setVisible(true)
        );
    }
}
