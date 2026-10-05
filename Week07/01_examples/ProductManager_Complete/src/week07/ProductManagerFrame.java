package week07;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*;
public class ProductManagerFrame extends JFrame {
 private final ProductDAO dao=new ProductDAO(); private final DefaultTableModel model=new DefaultTableModel(new String[]{"ID","상품명","분류","가격","재고"},0);
 private final JTable table=new JTable(model); private final JTextField name=new JTextField(10), category=new JTextField(8), price=new JTextField(6), stock=new JTextField(6);
 public ProductManagerFrame(){super("7주차 상품관리 미니프로젝트"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(720,420);
  JPanel input=new JPanel(); input.add(new JLabel("상품명"));input.add(name);input.add(new JLabel("분류"));input.add(category);input.add(new JLabel("가격"));input.add(price);input.add(new JLabel("재고"));input.add(stock);
  JPanel buttons=new JPanel(); for(String s:new String[]{"조회","등록","수정","삭제"}){JButton b=new JButton(s);b.addActionListener(e->action(s));buttons.add(b);}
  add(input,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);add(buttons,BorderLayout.SOUTH);
  table.getSelectionModel().addListSelectionListener(e->{int r=table.getSelectedRow();if(r>=0){name.setText(model.getValueAt(r,1).toString());category.setText(model.getValueAt(r,2).toString());price.setText(model.getValueAt(r,3).toString());stock.setText(model.getValueAt(r,4).toString());}}); load();
 }
 private int num(JTextField f){return Integer.parseInt(f.getText().trim());}
 private void action(String a){try{int r=table.getSelectedRow(); if(a.equals("등록"))dao.insert(name.getText(),category.getText(),num(price),num(stock)); else if(a.equals("수정")&&r>=0)dao.update((int)model.getValueAt(r,0),name.getText(),category.getText(),num(price),num(stock)); else if(a.equals("삭제")&&r>=0)dao.delete((int)model.getValueAt(r,0)); load();}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}}
 private void load(){try{model.setRowCount(0);for(Product p:dao.findAll())model.addRow(new Object[]{p.id(),p.name(),p.category(),p.price(),p.stock()});}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new ProductManagerFrame().setVisible(true));}
}