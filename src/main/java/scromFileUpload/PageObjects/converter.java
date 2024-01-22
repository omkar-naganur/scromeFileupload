package scromFileUpload.PageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.Zip;
import org.openqa.selenium.support.PageFactory;

public class converter {
	
WebDriver driver;
	
	public converter(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	static ArrayList<String> foldersNamepath = new ArrayList<String>();
	
	public ArrayList<String> newZipFileConverter() throws FileNotFoundException, Throwable {
		
        // Create a File object for the folder.
        File folder = new File(System.getProperty("user.dir")+"//StorageOfZipFiles");

        // Get a list of all the files and folders in the folder.
        File[] files = folder.listFiles();

        // Iterate over the list and print the filename and path of each file.
        for (File file : files) {
        	//listOfZipFIlesName.add(file.getName());
        	String folderName = file.getName().replace(".zip", "").replace(" ", "_");
        	   // Create a new File object for the parent folder
            File parentFolder = new File(System.getProperty("user.dir")+"//ExtractedFolders");

            // Create a new File object for the child folder
            File childFolder = new File(parentFolder, folderName);

            // Create the child folder
            childFolder.mkdir();
            
            // Get the path of the child folder
            String path = childFolder.getAbsolutePath();

            // Print the path of the child folder
        //    System.out.println("The path of the child folder is: " + path);
            foldersNamepath.add(path);
        	
//            System.out.println(file.getName());
//            System.out.println(file.getPath());
            
            Zip.unzip(new FileInputStream(file.getPath()), new File(path));
        	    
    }

System.out.println(foldersNamepath);
return foldersNamepath ;
		
	}
	
	
}
