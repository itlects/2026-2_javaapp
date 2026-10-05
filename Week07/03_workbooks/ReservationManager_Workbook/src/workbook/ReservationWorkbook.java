package workbook;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*;
public class ReservationWorkbook extends JFrame{
 static final String URL="jdbc:mysql://localhost:3306/javaapp?serverTimezone=Asia/Seoul&characterEncoding=UTF-8", USER="root", PW="1234";
 DefaultTableModel m=new DefaultTableModel(new String[]{"번호","고객","날짜","항목","상태"},0); JTable t=new JTable(m);
 JTextField customer=new JTextField(8), date=new JTextField(8), item=new JTextField(10); JComboBox<String> status=new JComboBox<>(new String[]{"예약","완료","취소"});
 ReservationWorkbook(){setTitle("예약관리 워크북");setSize(650,400);setDefaultCloseOperation(EXIT_ON_CLOSE); JPanel p=new JPanel();p.add(customer);p.add(date);p.add(item);p.add(status);
  for(String s:new String[]{"조회","등록","상태수정","삭제"}){JButton b=new JButton(s);b.addActionListener(e->run(s));p.add(b);}add(p,BorderLayout.NORTH);add(new JScrollPane(t));load();}
 void run(String a){try(Connection c=DriverManager.getConnection(URL,USER,PW)){int r=t.getSelectedRow();PreparedStatement ps=null;
  if(a.equals("등록")){ps=c.prepareStatement("INSERT INTO reservations(customer_name,reservation_date,item_name,status) VALUES(?,?,?,?)");ps.setString(1,customer.getText());ps.setDate(2,Date.valueOf(date.getText()));ps.setString(3,item.getText());ps.setString(4,status.getSelectedItem().toString());}
  else if(a.equals("상태수정")&&r>=0){ps=c.prepareStatement("UPDATE reservations SET status=? WHERE reservation_id=?");ps.setString(1,status.getSelectedItem().toString());ps.setInt(2,(int)m.getValueAt(r,0));}
  else if(a.equals("삭제")&&r>=0){ps=c.prepareStatement("DELETE FROM reservations WHERE reservation_id=?");ps.setInt(1,(int)m.getValueAt(r,0));}
  if(ps!=null)ps.executeUpdate();load();}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}}
 void load(){m.setRowCount(0);try(Connection c=DriverManager.getConnection(URL,USER,PW);PreparedStatement ps=c.prepareStatement("SELECT * FROM reservations ORDER BY reservation_id");ResultSet rs=ps.executeQuery()){while(rs.next())m.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getDate(3),rs.getString(4),rs.getString(5)});}catch(Exception e){JOptionPane.showMessageDialog(this,e.getMessage());}}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new ReservationWorkbook().setVisible(true));}
}