package delete_from_rbgsittersite;

import java.sql.Date;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Justin_2
 */
public class Rate {
    private final int id;
    private final int uid;
    private final int hours;
    private final int price;
    private final boolean toBeDeleted;
    private final Date dateRequest;
    
    public int getID(){
        return this.id;
    }
    public int getUid(){
        return this.uid;
    }
    public int getHours(){
        return this.hours;
    }
    public int getPrice(){
        return this.price;
    }
    public boolean getTbd(){
        return this.toBeDeleted;
    }
    public Date getDateRequest(){
        return this.dateRequest;
    }
    
    public Rate (int id, int uid, int h, int p, boolean tbd, Date date){
        this.id = id;
        this.uid = uid;
        this.hours = h;
        this.price = p;
        this.toBeDeleted = tbd;
        this.dateRequest = date;
    }
    
    public static Rate getRateFromID(ArrayList<Rate> rates,int id){
        Rate rate = null;
        for (Rate r : rates){
            if (r.id == id){
                rate = r;
            }
        }
        return rate;
    }
    
    public static ArrayList<Rate> sortByDate(ArrayList<Rate> rates){
        Predicate<Rate> pTbd = (r) -> r.toBeDeleted == true;
        Predicate<Rate> pActive = (r) -> r.toBeDeleted == false;
        
        // Seperates tbd false from true
        ArrayList<Rate> tbdList = (ArrayList<Rate>) rates.stream().filter(pTbd).collect(Collectors.toList());
        ArrayList<Rate> activeList = (ArrayList<Rate>) rates.stream().filter(pActive).collect(Collectors.toList());
        
        /* Following two lines sort tbd in ascending date, but not needed as default produces same result
        Comparator<Rate> com = comparing((s) -> s.getDateRequest());
        tbdList = (ArrayList<Rate>) tbdList.stream().sorted(com).collect(Collectors.toList());
        */
        ArrayList<Rate> returnList = new ArrayList<Rate>(tbdList);
        returnList.addAll(activeList);
      
        return returnList;
    }
    
}


