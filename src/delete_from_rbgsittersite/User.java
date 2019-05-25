package delete_from_rbgsittersite;

import java.util.ArrayList;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Justin_2
 */
public class User {
    
    private final int id;
    private final String userName;
    private final String surname;
    private final String firstname;
    private final char gender;
    private final String postcode1;
    private final String postcode2;
    private final String phoneNum;
    private final int activeRates;
    private int ratesToBeDeleted;
    
    public int getID(){
        return this.id;
    }
    public String getUsrName(){
        return this.userName;
    }
    public String getSurName(){
        if (this.surname == null){
            return "na";
        } else {
            return this.surname;
        }
    }
    public String getFirstName(){
        if (this.firstname == null){
            return "na";
        } else {
            return this.firstname;
        }
    }
    public String getGender(){
        if (this.gender == 'u'){
            return "na";
        } else {
            return "" + this.gender;
        }
    }

    public String getPostCode(){
        if (this.postcode1 == null || this.postcode2 == null || this.postcode1 == "" || this.postcode2 == ""){
            return "na";
        } else {
            return this.postcode1 + " " + this.postcode2;
        }
    }
    public String getPhoneNum(){
        if (this.phoneNum == null){
            return "na";
        } else {
            return this.phoneNum;
        }
    }
    public int getActiveRates(){
        return this.activeRates;
    }
    public int getRatesTbd(){
        return this.ratesToBeDeleted;
    }
    public void decrementRatesTBD(){
        this.ratesToBeDeleted--;
    }
    
    
    public User(int id, String un, String sn, String fn,char g,String pc1, String pc2,String pnum, int ars,int rtbd){
        this.id = id;
        this.userName = un;
        this.surname = sn;
        this.firstname = fn;
        this.gender = g;
        this.postcode1 = pc1;
        this.postcode2 = pc2;
        this.phoneNum = pnum;
        this.activeRates = ars;
        this.ratesToBeDeleted = rtbd;
    }
    
    public static ArrayList<User> sortByTbd(ArrayList<User> users){
        Comparator<User> com = comparing((u) -> u.getRatesTbd());
        List<User> sortedTbd = (List<User>) users.stream().sorted(com.reversed()).collect(Collectors.toList());
        return (ArrayList<User>) sortedTbd;
    }
    
    //UserName is unique to User, as is id
    public static User getUserByUn(ArrayList<User> users,String uName){
        User user = null;
        for (User u : users){
            if (u.userName.equals(uName)){
                user = u;
            }
        }
        return user;
    }
    
    
}
