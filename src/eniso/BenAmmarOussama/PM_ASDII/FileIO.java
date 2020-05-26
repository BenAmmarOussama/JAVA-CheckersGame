package eniso.BenAmmarOussama.PM_ASDII;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

abstract public class FileIO {
	
	public static String requestFileName() {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Please type the filename :");
		String filename = input.nextLine(); 
		return filename;
	}
	
	// Method to save an object to file in case the file name is given during execution
	public static void saveObjectToFile(Object serObj, String filepath) {
		String fileName = requestFileName();
		saveObjectToFile(serObj, filepath, fileName);
	}
	
	// Method to load an object from file in case the file name is given during execution
	public static Object loadObjectFromFile(String filepath) {
		String fileName = requestFileName();
		return loadObjectFromFile(filepath, fileName);
	}
	
	// Method to save an object to file in case the file name is known before execution
	public static void saveObjectToFile(Object serObj, String filepath, String fileName) {
		try {
            FileOutputStream fileOut = new FileOutputStream(filepath+fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            fileOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	// Method to load an object from file in case the file name is known before execution
	public static Object loadObjectFromFile(String filepath, String filename) {
		
		try {
            FileInputStream fileIn = new FileInputStream(filepath+filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            Object obj = objectIn.readObject();
 
            System.out.println("The Object has been read from the file");
            objectIn.close();
            return obj;
            
		} catch (FileNotFoundException e) {
        	System.out.println("File not found");
        	return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
	}
	
	public static void createDirectory(String filepath) {
    	
  		File file = new File(filepath);
  		//Creating the directory
  		boolean bool = file.mkdir();
  		if(bool){
     		System.out.println("Directory created successfully: "+filepath);
  		}else{
     		System.out.println("Sorry couldn’t create specified directory");
  		}
    }

}
