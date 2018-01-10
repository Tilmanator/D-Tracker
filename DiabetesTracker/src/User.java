
public class User {
	
	public final String name;
	// Variables - set by user
	private double InsulinRatio = 3*18; // mg/dL that one unit corrects for
	private double carbRatio = 10; // Grams of carbs per unit of insulin
	
	User(String name){
		this.name = name;
	}
}
