package week06.member;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.SQLException;
public class MemberManagementFrame extends JFrame {
  private final JTextField id=new JTextField(10), name=new JTextField(10), phone=new JTextField(12), email=new JTextField(18);
  private final DefaultTableModel model=new DefaultTableModel(new String[]{"ID","이름","전화","이메일"},0);
  private final JTable table=new JTable(model); private final MemberDAO dao=new MemberDAO();
  public MemberManagementFrame(){
    setTitle("6주차 회원관리 CRUD"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(760,420); setLocationRelativeTo(null);
    JPanel form=new JPanel(); form.add(new JLabel("ID"));form.add(id);form.add(new JLabel("이름"));form.add(name);form.add(new JLabel("전화"));form.add(phone);form.add(new JLabel("이메일"));form.add(email);
    JPanel buttons=new JPanel(); String[] labels={"조회","등록","수정","삭제","초기화"};
    for(String s:labels){JButton b=new JButton(s);buttons.add(b); if(s.equals("조회"))b.addActionListener(e->load()); if(s.equals("등록"))b.addActionListener(e->insert()); if(s.equals("수정"))b.addActionListener(e->update()); if(s.equals("삭제"))b.addActionListener(e->delete()); if(s.equals("초기화"))b.addActionListener(e->clear());}
    table.getSelectionModel().addListSelectionListener(e->{int r=table.getSelectedRow(); if(r>=0){id.setText(model.getValueAt(r,0).toString());name.setText(model.getValueAt(r,1).toString());phone.setText(model.getValueAt(r,2).toString());email.setText(model.getValueAt(r,3).toString());id.setEditable(false);}});
    add(form,BorderLayout.NORTH); add(new JScrollPane(table),BorderLayout.CENTER); add(buttons,BorderLayout.SOUTH); load();
  }
  private Member current(){return new Member(id.getText().trim(),name.getText().trim(),phone.getText().trim(),email.getText().trim());}
  private void load(){try{model.setRowCount(0);for(Member m:dao.findAll())model.addRow(new Object[]{m.getMemberId(),m.getName(),m.getPhone(),m.getEmail()});}catch(SQLException ex){error(ex);}}
  private void insert(){try{dao.insert(current());load();clear();}catch(SQLException ex){error(ex);}}
  private void update(){try{dao.update(current());load();clear();}catch(SQLException ex){error(ex);}}
  private void delete(){String mid=id.getText().trim(); if(mid.isEmpty())return; if(JOptionPane.showConfirmDialog(this,mid+" 회원을 삭제할까요?","삭제 확인",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)try{dao.delete(mid);load();clear();}catch(SQLException ex){error(ex);}}
  private void clear(){id.setEditable(true);id.setText("");name.setText("");phone.setText("");email.setText("");table.clearSelection();}
  private void error(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"DB 오류",JOptionPane.ERROR_MESSAGE);}
  public static void main(String[] args){SwingUtilities.invokeLater(()->new MemberManagementFrame().setVisible(true));}
}