package delete_from_rbgsittersite;

/**
 *
 * @author Justin_2
 */
public class RunMain {


    public static void main(String[] args) {
        Gui g = new Gui();
        GuiModel gm = new GuiModel(g);
        GuiController gc = new GuiController(g,gm);
        gm.assignGuiController(gc);
        g.activateButtonListeners(gc);
    }
    
}
