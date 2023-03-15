package atm;

import java.util.*;
import java.io.*;

public class FileManager {

	private String fileName;
	private File file;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	public FileManager(String fileName) {
		this.fileName = fileName;
		this.file = new File(fileName);
	}
}
