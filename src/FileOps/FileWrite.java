package FileOps;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileWrite {
	
	private PrintWriter file=null;

	//The Class is used for writing a file
	
	//write string
	public void write(String str) {
		file.print(str);
	}
	//write number
	public void write(int number) {
		file.print(number);
	}
	//write string
	public void writeln(String str) {
		file.println(str);
	}
	public void writeln(float flo) {
		file.println(flo);
	}
	//write number
	public void writeln(int number) {
		file.println(number);	
	}
	//open output file
	public void openWFile(String fileName){
			try{
				file = new PrintWriter(new FileOutputStream(fileName), true);
		    }catch ( FileNotFoundException fileNotFoundException ){
		           System.err.println( "Error opening file." );
		           System.exit( 1 );
		    } 
	}
		
	//close output file
	public void closeWFile(){
		if ( file != null ){
				 file.close();
		}
	}
	
}
