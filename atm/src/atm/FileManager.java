package atm;

import java.util.*;
import java.io.*;

public class FileManager {

	private String fileName;
	private File file;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	private String data;
	
	public FileManager(String fileName, String data) {
		this.fileName = fileName;
		this.data = data;
		this.file = new File(fileName);
	}
	
	public FileManager(String fileName) {
		this.fileName = fileName;
		this.file = new File(fileName);
	}
	
	public void save() {
		try {
			this.fileWriter = new FileWriter(this.file);
			this.fileWriter.write(this.data);
			
			this.fileWriter.flush();
			this.fileWriter.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String load() {
		
		if(this.file.exists()) {
			try {
				this.fileReader = new FileReader(this.file);
				this.bufferedReader = new BufferedReader(this.fileReader);
				
				String data = this.bufferedReader.readLine();
				
				return data;
						
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return null;
	} 
}
