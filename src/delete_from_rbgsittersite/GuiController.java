/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete_from_rbgsittersite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Justin_2
 */
public class GuiController implements ActionListener, ListSelectionListener {
    Gui gui;
    GuiModel gModel;
    RatesGui rGui;
    
    public GuiController(Gui g, GuiModel gm){
        gui = g;
        gModel = gm;
    }
    
    
    public void assignRatesGui(RatesGui rg){
        rGui = rg;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == gui.btn) {
            if(gModel.checkPassword(gui.getPasswordInput())){
                gui.activeTableListener(this);
            }
        }
        if (rGui != null){ // prevent null pointer exception before rGui is created
            if (ae.getSource() == rGui.btnDelete){
                System.out.println("button Delete pressed");
                gModel.deleteBtnPressed();
            }
        }  
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {//Prevent double events
            gModel.manageRates();
         }
    }
}
