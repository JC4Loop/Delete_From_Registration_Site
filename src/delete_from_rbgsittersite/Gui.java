package delete_from_rbgsittersite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;   
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Justin_2
 */
public class Gui extends JFrame {
   JButton btn;
   JList<String> list1;
   JPasswordField pwInput;
   JLabel dLbl;
   JPanel mainPanel;
   //DefaultListModel<String> lmUsers;
   JTable tbl;
   final static int WIDTH = 900;
   ArrayList<User> userList;
   
   Font mono20 = new Font(Font.MONOSPACED, Font.BOLD, 20);
   Font dialog20B = new Font(Font.DIALOG, Font.BOLD, 20);
   Font dialog18 = new Font(Font.DIALOG, Font.PLAIN, 18);
   
    public Gui(){
        this.setTitle("Delete From rbgSitter Data");
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0,0,WIDTH,100);
        topPanel.setBackground(Color.decode("#66ffff"));
        
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(dialog20B);
        pwInput = new JPasswordField(20);
       // lblPass.setBounds(20,40, 80,30);
        //pwInput.setBounds(100,40,100,30);
        
        btn = new JButton("Click Here");
        btn.setBounds(300,40,200,30);
        
        mainPanel = new JPanel();
        mainPanel.setBounds(0,101,WIDTH,300);
        mainPanel.setBackground(Color.white);
        dLbl = new JLabel("Input Password above");
        dLbl.setFont(dialog18);

       
        mainPanel.add(dLbl);
        this.add(mainPanel);
        topPanel.add(lblPass);
        topPanel.add(pwInput);
        topPanel.add(btn);
        this.add(topPanel);
      
       // this.add(list1);
        this.setBounds(300,100,WIDTH,400);
        //this.getContentPane().setBackground(Color.white);
        this.setLayout(null);
        this.setVisible(true);
        
    }
    
    public void activateButtonListeners(GuiController controller){
        btn.addActionListener(controller);
    }
    
    public void activeTableListener(GuiController controller){
        tbl.getSelectionModel().addListSelectionListener(controller);
    }
    
    public User getUserOfSelectedRow(){
        int sr = tbl.getSelectedRow();
        Object o =  tbl.getValueAt(sr, 0);
        User u = User.getUserByUn(userList, o.toString());
        return u;
    }
    
    public String getUNofSelectedRow(){
        int sr = tbl.getSelectedRow();
        Object o = tbl.getValueAt(sr, 0); // 0 column is username
        return o.toString();
    }
    
    
    public String getPasswordInput(){
        return pwInput.getText();
    }
    
    public void showJOptionPane(String str){
        System.out.println("in gui" + str);
        JOptionPane.showMessageDialog(null, str);
        dLbl.setText(str);
    }
    
    public void disablePwInputs(){
        pwInput.setText("");
        pwInput.setEnabled(false);
        btn.setText("Password Entered Correctly");
        btn.setEnabled(false);
    }
    
    public void setupUsrTable(ArrayList<User> uList){
        userList = uList;
        System.out.println("in displayInfo");
        mainPanel.remove(dLbl);
        mainPanel.setLayout(new BorderLayout());
     
        //User(int id, String un, String sn, String fn,char g,String pc1, String pc2,String pnum, int ars,int rtbd)
        String[] colNames = { "UserName", "LastName", "Firstname","Gender","PostCode" ,"PhoneNo","Active Rates","tbd"};
        DefaultTableModel tableModel = new DefaultTableModel(colNames,0);

        
        for (User u : userList){
            Object[] o = {u.getUsrName(), u.getSurName(), u.getFirstName()
                    ,u.getGender(),u.getPostCode()
                    ,u.getPhoneNum(),u.getActiveRates(),u.getRatesTbd()};
            tableModel.addRow(o);
        }
        
        tbl = new JTable(tableModel);
        tbl.setDefaultEditor(Object.class, null); // disable editing of cells

        tbl.setFont(mono20);
        JScrollPane sp = new JScrollPane(tbl);
 
        mainPanel.add(sp);
     
        mainPanel.revalidate();
        repaint();
        showUserDetails();
    }
    
    public void showUserDetails(){
        tbl.getColumnModel().getColumn(0).setMinWidth(110);
        tbl.getColumnModel().getColumn(1).setMinWidth(110);
        tbl.getColumnModel().getColumn(2).setMinWidth(110);
        tbl.getColumnModel().getColumn(3).setMinWidth(80);
        tbl.getColumnModel().getColumn(4).setPreferredWidth(100);
        tbl.getColumnModel().getColumn(5).setMinWidth(130);
        tbl.setRowHeight(30);
      //  tbl.getColumnModel().getColumn(6).setPreferredWidth(150);
       // tbl.getColumnModel().getColumn(7).setPreferredWidth(150);
     
    }
    
    /***
     * Update table to show decremented ToBeDeleted value
     */
    public void decTbdOnTable(int id){
        User selectedUsr = null;
        for (User u : userList){
            if (u.getID() == id) {
                selectedUsr = u;
            }       
        }
        selectedUsr.decrementRatesTBD();
        DefaultTableModel model = (DefaultTableModel)tbl.getModel();
        int sr = tbl.getSelectedRow();
        model.setValueAt(selectedUsr.getRatesTbd(), sr, 7);
       // Object o = tbl.getValueAt(sr, 0); // 0 column is username
    }
        
}
