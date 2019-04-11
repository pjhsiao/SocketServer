import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class ProcessBuilder {
	public static void main(String[] args) throws IOException, InterruptedException {
		try{
		    Runtime rt = Runtime.getRuntime();
		    Process proc = rt.exec("cmd /c netstat -ano|findstr 9909");

		    BufferedReader stdInput = new BufferedReader(
		                                     new InputStreamReader(proc.getInputStream()));
		    String s = null;

		    if ((s = stdInput.readLine()) != null) {
		        int index=s.lastIndexOf(" ");
		        String sc=s.substring(index, s.length());
		        rt.exec("Taskkill /PID" +sc+" /T /F");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
    } 
}
