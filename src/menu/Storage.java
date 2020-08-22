package menu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Storage {
	static List<String> lines;
	static Path path = FileSystems.getDefault().getPath("resources", "storage.txt");	
	
	public Storage() {
		// indexes storage
    	try {lines = Files.readAllLines(path);
		} catch (IOException e) {e.printStackTrace();}
    	for (int i = 0; i < 3; i++) {System.out.println("Storage line " + i + ": " + lines.get(i));}
	}
	
	// gives specific storage-line
    public static double load(String purpose) throws IOException {
    	double iReturn = 0;
    	switch (purpose) {
	    	case "difficulty":
	    		iReturn = Double.parseDouble(lines.get(0));
	    		break;
    		case "maxLifes":
    			iReturn = Double.parseDouble(lines.get(1));
    			break;
    		case "highscore":
    			iReturn = Double.parseDouble(lines.get(2));
    	}
        return iReturn;
    }
    
    // overwrites specific storage-line
    public static void save(String purpose, String data) throws IOException {
    	if (data.length() <= 10) {
        	switch (purpose) {
    	    	case "difficulty":
    	    		lines.set(0, data);
    	    		break;
        		case "maxLifes":
        			lines.set(1, data);
        			break;
        		case "highscore":
        			lines.set(2, data);
        	}
        	
        	Files.write(path, lines, StandardCharsets.UTF_8);
    	} else {
    		System.out.println("wrong input for saving");
    	}
    	
    }
}
