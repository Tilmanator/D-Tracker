import java.time.*;

public class BSMeasurement {
	Instant time;
	int value;
	
	BSMeasurement(int v){
		value = v;
		time = Instant.now();
	}
	
	BSMeasurement(int v, Instant t){
		time = t;
		value = v;
	}
}
