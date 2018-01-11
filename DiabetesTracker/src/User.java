
public class User {
	
	public final String name;
	// Variables - set by user

	
	private double insulinRatio = 3*18; // mg/dL that one unit corrects for
	private double carbRatio = 10; // Grams of carbs per unit of insulin
	
	User(String name){
		this.name = name;
	}
	
	public void setIRatio(double r){
		insulinRatio = r;
	}
	
	public void setCRatio(double r){
		carbRatio = r;
	}
	
	public double getInsulinRatio(){
		return insulinRatio;
	}
	
	public double getCarbRatio(){
		return carbRatio;
	}
}
