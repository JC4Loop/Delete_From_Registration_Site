package delete_from_rbgsittersite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Justin_2
 */
public class RatesGui extends JFrame {
    JTable tbl;
    DefaultTableModel tableModel;
    JPanel panel;
    JButton btnDelete;
    JLabel lblUserN, lblNumRates,lblTbd;
    final int TPANELHEIGHT = 100;
    User selectedUser;
    ArrayList<Rate> ratesList;
    JScrollPane scrollPane;
    
    public RatesGui(){
        this.setTitle("Rates");
        
      
        JPanel topPanel = new JPanel(null);
        topPanel.setBackground(Color.white);
        topPanel.setBounds(0,0,900, TPANELHEIGHT);
        JLabel lblHeader = new JLabel("Select Rate For Deletion");
        lblHeader.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        lblHeader.setBounds(350, 3, 300, 30);
        topPanel.add(lblHeader);
        lblUserN = new JLabel();
        lblUserN.setBounds(5,5,200,20);
        topPanel.add(lblUserN);
        lblNumRates = new JLabel();
        lblNumRates.setBounds(5, 35, 200, 20);
        topPanel.add(lblNumRates);
        lblTbd = new JLabel();
        lblTbd.setBounds(5,65,200,20);
        topPanel.add(lblTbd);
        btnDelete = new JButton("Delete Rate");
        btnDelete.setBounds(400,TPANELHEIGHT - 25, 100,20);
        topPanel.add(btnDelete);
        panel = new JPanel(new BorderLayout());
        panel.setBounds(0,100,900,300);
        this.add(topPanel);
        
        initTable();
        this.add(panel);
        this.setLayout(null);
        this.setBounds(300,505,900,400);
        this.setVisible(true);
    }
    
    public void activateBtnActionListener(GuiController gc){
        btnDelete.addActionListener(gc);
    }
    
    private void initTable(){
        String[] colNames = { "RateID","Hours","Price","Deletion Request", "Date Of Request" };
        tableModel = new DefaultTableModel(colNames,0);
        
        tbl = new JTable(tableModel);
        tbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        tbl.setRowHeight(30);
        tbl.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(tbl);
        scrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 52, 20,Color.BLACK));
        panel.add(scrollPane);
        
    }
    
    public void displayRates(ArrayList<Rate> rList,User user){
        ratesList = rList;
        selectedUser = user;
        int rowLen = tableModel.getRowCount();
        for (int i = rowLen - 1; i >= 0; i--){
            tableModel.removeRow(i);
        }
        
        for (Rate r : ratesList){
            //System.out.println(r.getID() + " " + r.getHours() + " " + r.getDateRequest());
            Object[] o = {r.getID(), r.getHours(), r.getPrice()
                    ,r.getTbd(),r.getDateRequest()};
            tableModel.addRow(o);
        }
        setLblInfoUser("User : " + selectedUser.getUsrName());
        setLblNumRates("Number of Rates:  " + ratesList.size());
        setLblTbd("Rates to Be deleted : " + selectedUser.getRatesTbd());
    }
    
    public User getSelectedUser(){
        return selectedUser;
    }
    
    public void setLblInfoUser(String str){
        lblUserN.setText(str);
    }
    public void setLblNumRates(String str){
        lblNumRates.setText(str);
    }
    public void setLblTbd(String str){
        lblTbd.setText(str);
    }
    
    public Rate getRateOfSelected(){
        int sr = tbl.getSelectedRow();
        Object o = tbl.getValueAt(sr, 0);
        Rate r = Rate.getRateFromID(ratesList,Integer.parseInt(o.toString()));
        return r;
    }
    
    public void showMessageDialog(String str){
        JOptionPane.showMessageDialog(null, str);
    }
    
    public boolean showDeleteConfirm(User u, Rate r){
        String msg = "Delete rate of " + r.getHours() + " hours and "
                + r.getPrice() + " for user " + u.getUsrName()
                + "\nThis cannot be undone";
        String[] btnOptions = {"DELETE", "cancel"};
        int result = JOptionPane.showOptionDialog(this, msg, "Confirm Deletion",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,btnOptions,null);
        
        boolean returnValue = false;
        switch(result){
            case JOptionPane.YES_OPTION:
                returnValue = true;
                break;
            case JOptionPane.NO_OPTION:
            case JOptionPane.CANCEL_OPTION:
                returnValue = true;
        }
        return returnValue;
    }
    
    public void removeRateFromList(int rid){
        for (Rate r : ratesList){
            if (r.getID() == rid){
                int row = tbl.getSelectedRow();
                ((DefaultTableModel)tbl.getModel()).removeRow(row);
            }
        }
    }
}
