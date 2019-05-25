package delete_from_rbgsittersite;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author Justin_2
 */
public class DBConnection {
    private static Connection conn;
    private static String password; // password is orangetree
    private static GuiModel gModel;
    private DBConnection(){
        boolean b = true;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //connection with required timestamp change + db user & password
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/rbgsite"
                            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
                            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"
                    ,"siud",password);
        } catch (Exception ex){
            //ex.printStackTrace();
            b = false;
            String msg = ex.getMessage().substring(0,22);
            System.out.println(msg);
            if (msg.startsWith("Access denied for user")){
                gModel.dbConnectError("Invalid Password");
            }
            if (msg.startsWith("Communications link fa")){
                gModel.dbConnectError("Cannot connect to Database");
            }
        } finally {
            if (b == true){
                System.out.println("b == true");
                gModel.setCanConnectTrue();
            }
        }
    }
    
    public static Connection getDbConnection(){
        if (conn == null){
            new DBConnection();
        }
        return conn;
    }
    
    public static void assignGuiModel(GuiModel gm){
        gModel = gm;
    }
    public static void assignPassword(String pw){
        password = pw;
    }
    
    public static boolean deleteRate(int rid){
        boolean bool = true;
        try {
            Connection conn = getDbConnection();
            Statement stmt = conn.createStatement();
            int deleted = stmt.executeUpdate("DELETE FROM rates WHERE rateID = " + rid);
            if (deleted == 1){
                System.out.println("In DBConnection.deleteRate() - delete = 1");
            } else {
                bool = false;
            }
        } catch (Exception ex){
            bool = false;
            ex.printStackTrace();
        }
        return bool;            
    }
    
    public static ArrayList<Rate> getRates(int uid){
        ArrayList<Rate> rList = new ArrayList<Rate>();
        try {
            Connection conn = getDbConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Rates WHERE uid = " + uid);
            while (rs.next()){
                int rateID = rs.getInt("rateID");
                int hours = rs.getInt("hours");
                int price = rs.getInt("price");
                int nTbd = rs.getInt("toBeDeleted");
                Date d = rs.getDate("tbdDateReq");
                Boolean bTbd = false;
                if (nTbd == 1){
                    bTbd = true;
                }
                rList.add(new Rate(rateID,uid,hours,price,bTbd,d));
            }
        } catch (Exception e){
            System.out.println("error in getRates");
            System.out.println(e);
        }
        
        return rList;
    }
    
    public static ArrayList<User> getUserInfo(){
        ArrayList<User> uList = new ArrayList<User>();
        int i = 0;
        try{
            Connection conn = getDbConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT users.uid,username, surname,firstname, gender,"
                            + "PostCode1, Postcode2, PhoneNum,"
                            + " COUNT(IF(toBeDeleted = 0,1,NULL))\"ActiveRates\","
                            + " COUNT(IF(toBeDeleted = 1,1,NULL))\"Rates_tBD\""
                            + " FROM userdetails"
                            + " LEFT JOIN users"
                            + " ON userdetails.uid = users.uID"
                            + " LEFT JOIN rates ON rates.uid = users.uid"
                            + " GROUP BY users.uid"
                            + " ORDER BY userdetails.surname");
         
            while (rs.next()){
                i++;
                String gen = rs.getString("gender");
                if (gen == null){
                    gen = "u";
                }
                User u = new User(rs.getInt("users.uid"),rs.getString("username"),rs.getString("surname")
                        ,rs.getString("firstname"),gen.charAt(0),rs.getString("PostCode1")
                        ,rs.getString("PostCode2"),rs.getString("PhoneNum"),rs.getInt("ActiveRates"),rs.getInt("Rates_tBD"));
                uList.add(u);
            }
            //User(int id, String un, String sn, String fn,char g,String pc1, String pc2,String pnum, int ars,int rtbd
        } catch (Exception ex){
            System.out.println(ex);
            return null;
        }
        System.out.println("Length of uList is " + uList.size() + " i = " + i);
        return uList;
    }

    
    
    /*
    "SELECT users.uid,username, surname,firstname, gender,
				PostCode1, Postcode2, PhoneNum,
                                COUNT(IF(toBeDeleted = 0,1,NULL))"ActiveRates",
                                COUNT(IF(toBeDeleted = 1,1,NULL))"Rates_to_Delete"
				FROM userdetails 
				LEFT JOIN users
                                ON userdetails.uid = users.uID
                                LEFT JOIN rates ON rates.uid = users.uid
                                GROUP BY users.uid
				ORDER BY userdetails.surname"
    */
}
