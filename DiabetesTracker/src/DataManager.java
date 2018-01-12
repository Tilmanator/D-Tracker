import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.Date;

public class DataManager {
	// Default units is mg/dL
	private ArrayList<Entry> values;

	// Constants
	private final int maxValues = 10*90;
	
	// Constructors
	DataManager(ArrayList<Entry> v){
		values = v;
	}
	
	DataManager(){
		values = new ArrayList<Entry>();
	}
	
	// Methods
	// Return true for success
	public boolean initialLoad(){
		try (Connection conn = DriverManager.getConnection(
		               "jdbc:mysql://localhost:"+DatabaseInfo.socket()+"/"+
		               DatabaseInfo.db()+"?useSSL=false", DatabaseInfo.user(), DatabaseInfo.pw());
		               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

		         Statement stmt = conn.createStatement();
		      ) {
		         
		         String query = "select * from shoes";
		 
		         ResultSet rset = stmt.executeQuery(query);

		         System.out.println("The records selected are:");
		         int rowCount = 0;
		         while(rset.next()) {   // Move the cursor to the next row, return false if no more row
		            String title = rset.getString("name");
		            String price = rset.getString("colour");
		            int    qty   = rset.getInt("id");
		            System.out.println(title + ", " + price + ", " + qty);
		            ++rowCount;
		         }
		         System.out.println("Total number of records = " + rowCount);
		 
		      } catch(SQLException ex) {
		         ex.printStackTrace();
		         return false;
		      }
		      
		return true;
	}
	
	public void addValue(int val){
		if(values.size() >= maxValues){
			values.remove(0);
		}
		values.add(new Entry(val, 0.0, 0.0));
	}
	
	public void addValue(int val, Instant when){
		// Date d = new Date(when.toEpochMilli());
		if(values.size() >= maxValues){
			values.remove(0);
		}
		values.add(new Entry(val, 0.0, 0.0, when));
	}
}