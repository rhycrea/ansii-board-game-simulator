public class Main {
	
	public static void main(String[] args) {

		AllInOne data = new AllInOne();

		Drawing.Process((Reading.Process(data, args)), args);
		
	}
	
}
