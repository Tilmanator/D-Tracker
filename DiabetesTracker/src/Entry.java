import java.time.Instant;

public class Entry implements Comparable<Entry> {
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
	
	// Based on time
	@Override
	public int compareTo(Entry o) {
		return time.compareTo(o.time);
	}
	
	public static boolean canMerge(Entry e, Entry r){
		if(e.time.equals(r.time)){   // Make narrow interval?
			if((e.bloodSugar == 0 && r.bloodSugar > 0) ||
				(e.bloodSugar > 0 && r.bloodSugar == 0) ||  
				(e.insulin == 0 && r.insulin > 0) ||
				(e.insulin> 0 && r.insulin == 0) ||  
				(e.carbs == 0 && r.carbs > 0) ||
				(e.carbs > 0 && r.carbs == 0))
				return true;
		}
		return false;
	}
}
