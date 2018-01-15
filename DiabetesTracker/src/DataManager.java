import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.Date;
import java.util.List;

public class DataManager {
	// Default units is mg/dL
	private List<Entry> values;

	// Constants
	private final int maxValues = 900; // 10*90
	private Connection conn;
	
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

		         int rowCount = 0;
		         values = new ArrayList<Entry>(maxValues);
	         
		         while(rset.next() && rowCount < maxValues) {   // Move the cursor to the next row, return false if no more row
		        	Timestamp t = (Timestamp) rset.getObject("date");
		        	
		        	// Sql Timestamp -> util..Date
		        	Date date = new Date(t.getTime());
		            int bs = rset.getInt("bs");
		            int carbs = rset.getInt("carbs");
		            double insulin = rset.getDouble("insulin");
		            String notes = rset.getString("notes");
		            //System.out.println(date + " " + bs +" "+ carbs +" "+ insulin+ " "+notes);
		            Entry e = new Entry(bs, insulin, carbs, date);
		            values.add(e);
		            ++rowCount;
		         }
		         conn.close(); // Close the connection
		      } catch(SQLException ex) {
		         ex.printStackTrace();
		         return false;
		      }
		      
		/*for(Entry e: values){
			System.out.println(e);
			System.out.println(sqlFormat(e.time));
		}*/
		writeToDB(values.get(0));
		return true;
	}
	
	public void addValue(int val){
		if(values.size() >= maxValues){
			values.remove(0);
		}
		values.add(new Entry(val, 0.0, 0.0));
	}
	
	public void addValue(int val, Date when){
		if(values.size() >= maxValues){
			values.remove(0);
		}
		values.add(new Entry(val, 0.0, 0.0, when));
	}
	
	public boolean writeToDB(Entry e){
		return writeToDB(valueStmt(e));
	}
	
	public boolean writeToDB(List<Entry> l){
		return writeToDB(valueStmt(l));
	}
	
	private boolean writeToDB(String s){
		try (Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost:"+DatabaseInfo.socket()+"/"+
	               DatabaseInfo.db()+"?useSSL=false", DatabaseInfo.user(), DatabaseInfo.pw());
	         Statement stmt = conn.createStatement();
	      ) {
			// Get most recent first
	         String write = "INSERT IGNORE INTO vals (date,bs,insulin,carbs) VALUES"+s+
	        		 // TODO: change this to essentially merge values if possible (causing canMerge redundancy)
	        		 " ON DUPLICATE KEY UPDATE bs=";
	 
	         stmt.executeUpdate(write);
	         conn.close();
	      } catch(SQLException ex) {
	         ex.printStackTrace();
	         return false;
	      }
		return true;
	}
	
	// Each entry is seperate (a,b,c),(d,e,f)
	private String valueStmt(Entry e){return "('"+sqlFormat(e.time) +"',"+e.bloodSugar+","+e.insulin+","+e.carbs+")";}
	
	private String valueStmt(List<Entry> l){
		String s = "";
		for(Entry e : l){
			s += valueStmt(e) +",";
		}
		return s.substring(0, s.length()-1);
	}
	
	private String sqlFormat(Date d){
		final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
		return sdf.format(d);
	}
}