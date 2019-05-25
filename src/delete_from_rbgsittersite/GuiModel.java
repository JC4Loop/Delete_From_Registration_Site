package delete_from_rbgsittersite;
import java.util.ArrayList;
/**
 *
 * @author Justin_2
 */
public class GuiModel {
    Gui gui;
    RatesGui rGui;
    boolean canConnect;
    GuiController gCntrl;
    //private String password;
    public GuiModel(Gui g){
        gui = g;
        canConnect = false;
    }
    
    public void assignGuiController(GuiController gc){
        gCntrl = gc;
    }
    
    public Boolean checkPassword(String pw){
        if (pw.isEmpty()){
            dbConnectError("You have not entered password");
            return false;
        }
        //some simple code prevention of sqlInjection
        if (pw.contains(" ") || pw.contains("\"")){
            dbConnectError("There are invalid chars in your input\nPassword will not contain this");
            return false;
        }

        
        DBConnection.assignGuiModel(this);
        DBConnection.assignPassword(pw);
        DBConnection.getDbConnection();
        if (canConnect){
            System.out.println("connected success");
            //gui.displayList();
            gui.disablePwInputs();
            System.out.println("b4 getUserInfo()");
            ArrayList<User> uList = DBConnection.getUserInfo();
            uList = User.sortByTbd(uList);
            gui.setupUsrTable(uList);
            return true;
        } else {
            return false;
        } 
    }
    public void setCanConnectTrue(){
        canConnect = true;
    }

    public void manageRates(){
        User user = gui.getUserOfSelectedRow();
        
        if (rGui == null){
            rGui = new RatesGui();
            gCntrl.assignRatesGui(rGui);
            rGui.activateBtnActionListener(gCntrl);
        }
        if (!rGui.isVisible()){
            rGui.setVisible(true);
        }
        ArrayList<Rate> userRates = DBConnection.getRates(user.getID());
        
        rGui.displayRates(Rate.sortByDate(userRates),user);
    }
    
    public void deleteBtnPressed(){
       Rate rate = rGui.getRateOfSelected();
       User user = rGui.getSelectedUser();
        if (rate.getTbd() == false){
            rGui.showMessageDialog("Selected Rate has no deletion request\n"
                    + "Only deletion request of True can be deleted");
            
        } else if (rate.getTbd() == true){
            System.out.println("" + rate.getID());
            if(rGui.showDeleteConfirm(user, rate)){
                boolean b = DBConnection.deleteRate(rate.getID());
                if (b) {
                    System.out.println("Delete rate successfully");
                    removeRateFromInfo(user,rate);
                }
            }
        }
    }
    
    private void removeRateFromInfo(User u, Rate r){
        rGui.removeRateFromList(r.getID());
        gui.decTbdOnTable(u.getID());
    }
    
    public void dbConnectError(String err){
        gui.showJOptionPane(err);
    }
}
