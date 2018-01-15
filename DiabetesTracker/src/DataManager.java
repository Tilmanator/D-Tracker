import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
//import java.sql.Date;
import java.time.*;
import java.util.Date;
import java.util.List;

public class DataManager {
	// Default units is mg/dL
	private List<Entry> values;

	// Constants
	private final int maxValues = 10*90;
	
	// Constructors
	DataManager(ArrayList<Entry> v){
		values = v;
	}
	
	DataManager(){
		initialLoad();
	}
	
	// Methods
	// Return true for success when all recent values 
	public boolean initialLoad(){
		try (Connection conn = DriverManager.getConnection(
		               "jdbc:mysql://localhost:"+DatabaseInfo.socket()+"/"+
		               DatabaseInfo.db()+"?useSSL=false", DatabaseInfo.user(), DatabaseInfo.pw());
		               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

		         Statement stmt = conn.createStatement();
		      ) {
		         
				// Get most recent first
		         String query = "SELECT * FROM vals ORDER BY date DESC LIMIT " + Integer.toString(maxValues) ;
		 
		         ResultSet rset = stmt.executeQuery(query);

		         System.out.println("The records selected are:");
		         int rowCount = 0;
		         values = new ArrayList<Entry>(maxValues);
	         
		         while(rset.next()) {   // Move the cursor to the next row, return false if no more row
		        	//Instant date =  Instant.parse(rset.getString("date"));
		        	String date = rset.getString("date");
		        	//Instant q = Instant.parse(date.substring(0, date.length()-2));
		        	Date d = rset.getDate("date");
		        	System.out.println(d.getTime());
		        	Object o = rset.getObject("date");
		        	Timestamp t = (Timestamp)o;
		        
		        	Date b = new Date(t.getTime());
		        	System.out.println(t.getTime());
		        	Instant i = Instant.ofEpochMilli(t.getTime());
		        	
		        	System.out.println(i);
		            int bs = rset.getInt("bs");
		            int carbs = rset.getInt("carbs");
		            double insulin = rset.getDouble("insulin");
		            String notes = rset.getString("notes");
		            System.out.println(date + " " + bs +" "+ carbs +" "+ insulin+ " "+notes);
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
	
	public void addValue(int val, Date when){
		// Date d = new Date(when.toEpochMilli());
		if(values.size() >= maxValues){
			values.remove(0);
		}
		values.add(new Entry(val, 0.0, 0.0, when));
	}
}