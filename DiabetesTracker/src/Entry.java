import java.time.Instant;

public class Entry {
	Instant time;
	int bloodSugar;
	double insulin;
	double carbs;
	
	Entry(int v, double i, double c){
		bloodSugar = v;
		insulin = i;
		carbs = c;
		time = Instant.now();
	}
	
	Entry(int v, double i, double c, Instant t){
		time = t;
		bloodSugar = v;
		insulin = i;
		carbs = c;
	}
}
