
public class Converter {
	public static double mgToMmol(int mgPerDL){
		return (double)mgPerDL/18;
	}
	
	public static int mmolToMg(double mMol){
		return (int)(mMol*18);
	}
	
	public static int hToMin(double hours){
		return (int)(hours*60);
	}
}
