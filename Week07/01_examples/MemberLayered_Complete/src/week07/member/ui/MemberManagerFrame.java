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
        setTitle("회원관리 - 계층 분리"); setSize(760,440); setDefaultCloseOperation(EXIT_ON_CLOSE); setLocationRelativeTo(null);
        JPanel top=new JPanel(); top.add(new JLabel("이름"));top.add(name);top.add(new JLabel("전화"));top.add(phone);top.add(new JLabel("이메일"));top.add(email);top.add(new JLabel("등급"));top.add(grade);
        JButton load=new JButton("조회"),add=new JButton("등록"),update=new JButton("수정"),del=new JButton("삭제"),clear=new JButton("초기화");
        JPanel bottom=new JPanel(); for(JButton b:new JButton[]{load,add,update,del,clear}) bottom.add(b);
        add(top,BorderLayout.NORTH); add(new JScrollPane(table),BorderLayout.CENTER); add(bottom,BorderLayout.SOUTH);
        load.addActionListener(e->load());
        add.addActionListener(e->run(()->{service.addMember(read(0)); reset(); load();}));
        update.addActionListener(e->run(()->{service.updateMember(read(selectedId)); reset(); load();}));
        del.addActionListener(e->{if(selectedId>0&&JOptionPane.showConfirmDialog(this,"삭제할까요?","확인",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)run(()->{service.deleteMember(selectedId);reset();load();});});
        clear.addActionListener(e->reset());
        table.getSelectionModel().addListSelectionListener(e->{int r=table.getSelectedRow(); if(r>=0){selectedId=(int)model.getValueAt(r,0);name.setText(String.valueOf(model.getValueAt(r,1)));phone.setText(String.valueOf(model.getValueAt(r,2)));email.setText(String.valueOf(model.getValueAt(r,3)));grade.setText(String.valueOf(model.getValueAt(r,4)));}});
        load();
    }
    private MemberDTO read(int id){return new MemberDTO(id,name.getText().trim(),phone.getText().trim(),email.getText().trim(),grade.getText().trim());}
    private void load(){run(()->{model.setRowCount(0);for(MemberDTO m:service.getMembers())model.addRow(new Object[]{m.getMemberId(),m.getName(),m.getPhone(),m.getEmail(),m.getGrade()});});}
    private void reset(){selectedId=-1;name.setText("");phone.setText("");email.setText("");grade.setText("");table.clearSelection();}
    private void run(Task t){try{t.run();}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"오류",JOptionPane.ERROR_MESSAGE);}}
    interface Task{void run()throws Exception;}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new MemberManagerFrame().setVisible(true));}
}
