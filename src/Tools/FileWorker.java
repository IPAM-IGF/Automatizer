package Tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWorker {

	public static void writeTo(File f, String content, boolean append){
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(f,append));
		    out.write(content);
		    out.close();
		} catch (IOException e) {
		}
	}

	public static String read(File file) {
		String content="";
        BufferedReader reader = null;
        String txt;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((txt = reader.readLine()) != null) { 
            	content+=txt+"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

		return content;
	}
}
