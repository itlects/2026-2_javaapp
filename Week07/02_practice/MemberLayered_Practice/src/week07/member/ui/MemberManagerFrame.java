package week07.member.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import week07.member.dto.MemberDTO;
import week07.member.service.MemberService;

public class MemberManagerFrame extends JFrame {
    private final MemberService service=new MemberService();
    private final JTextField name=new JTextField(9),phone=new JTextField(10),email=new JTextField(14),grade=new JTextField(7);
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"ID","이름","전화","이메일","등급"},0);
    private final JTable table=new JTable(model);
    private int selectedId=-1;
    public MemberManagerFrame(){
        setTitle("회원관리 실습"); setSize(760,440); setDefaultCloseOperation(EXIT_ON_CLOSE); setLocationRelativeTo(null);
        JPanel top=new JPanel(); top.add(new JLabel("이름"));top.add(name);top.add(new JLabel("전화"));top.add(phone);top.add(new JLabel("이메일"));top.add(email);top.add(new JLabel("등급"));top.add(grade);
        JButton load=new JButton("조회"),add=new JButton("등록"),update=new JButton("수정"),del=new JButton("삭제"),clear=new JButton("초기화");
        JPanel bottom=new JPanel(); for(JButton b:new JButton[]{load,add,update,del,clear}) bottom.add(b);
        add(top,BorderLayout.NORTH); add(new JScrollPane(table),BorderLayout.CENTER); add(bottom,BorderLayout.SOUTH);
        // TODO 8: 조회/등록/수정/삭제/초기화 이벤트 연결
        // TODO 9: JTable 행 선택 시 selectedId 및 입력창 동기화
        // TODO 10: 오류 메시지를 JOptionPane으로 표시
    }
    private MemberDTO read(int id){return new MemberDTO(id,name.getText().trim(),phone.getText().trim(),email.getText().trim(),grade.getText().trim());}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new MemberManagerFrame().setVisible(true));}
}
