import java.sql.*;

public class Test {

	public static void main(String[] args) {
		DataManager d = new DataManager();
		if(d.initialLoad()){
			// launch
		}
		else{
			System.exit(1);
		}
	}
}
