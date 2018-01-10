import java.util.ArrayList;
import java.time.*;
import java.util.Date;

public class DataManager {
	// Default units is mg/dL
	private ArrayList<BSMeasurement> values;

	// Constants
	private final int maxValues = 10*90;
	
	
	// Constructors
	DataManager(ArrayList<BSMeasurement> v){
		values = v;
	}
	
	DataManager(){
		values = new ArrayList<BSMeasurement>();
	}
	
	// Methods
	public void addValue(int val){
		if(values.size() >= maxValues){
			values.remove(0);
		}
		values.add(new BSMeasurement(val));
	}
	
	public void addValue(int val, Instant when){
		// Date d = new Date(when.toEpochMilli());
		if(values.size() >= maxValues){
			values.remove(0);
		}
		values.add(new BSMeasurement(val, when));
	}
}