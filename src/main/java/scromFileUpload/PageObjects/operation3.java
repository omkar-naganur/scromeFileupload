package scromFileUpload.PageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class operation3 {
	
WebDriver driver;
	
	public operation3(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public Set<String> listFilesUsingJavaIO(String dir) {
	    return Stream.of(new File(dir).listFiles())
	      .filter(file -> !file.isDirectory())
	      .map(File::getName)
	      .collect(Collectors.toSet());
	}
	
	ArrayList<String> lastsArray = new ArrayList<String>();
	
	public ArrayList<String> ListFoldersInDirectory()
	{
		 String directoryPath = System.getProperty("user.dir")+"//ExtractedFolders";

	        File directory = new File(directoryPath);

	        if (directory.exists() && directory.isDirectory()) {
	            File[] subDirectories = directory.listFiles(File::isDirectory);

	            if (subDirectories != null) {
	                System.out.println("List of folders in " + directoryPath + ":");
	                for (File subDirectory : subDirectories) {
	                	System.out.println("hello boss i am done *****************");
	                    System.out.println(subDirectory.getAbsolutePath());
	                    lastsArray.add(subDirectory.getAbsolutePath());
	                }
	            } else {
	                System.out.println("No folders found in the directory.");
	            }
	        } else {
	            System.out.println("The specified path is not a valid directory.");
	        }
	        return lastsArray;
	}
	
	public void ZipExtractorabc() {
		
		String sourceFolder = 	"/media/kwsys4/nonOsPartition/AutomationSerien4.0/scromFileUpload/scromFileUpload/StorageOfZipFiles";
	//	  String sourceFolder = System.getProperty("user.dir")+"//StorageOfZipFiles";
	     //   String outputFolder = System.getProperty("user.dir")+"//ExtractedFolders";
	        String outputFolder =  "/media/kwsys4/nonOsPartition/AutomationSerien4.0/scromFileUpload/scromFileUpload/ExtractedFolders";

	        File sourceDir = new File(sourceFolder);
	        File[] zipFiles = sourceDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".zip"));

	        if (zipFiles != null) {
	            for (File zipFile : zipFiles) {
	                try {
	                    extractZipFile(sourceFolder, outputFolder);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        } else {
	            System.out.println("No zip files found in the source folder.");
	        }
	    }

	    public void extractZipFile(String zipFilePath, String outputFolderPath) throws IOException {
	        byte[] buffer = new byte[1024];

	        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {

	            ZipEntry zipEntry = zipInputStream.getNextEntry();

	            while (zipEntry != null) {
	                String fileName = zipEntry.getName();
	                String filePath = outputFolderPath + File.separator + fileName;

	                // Create directories if they do not exist
	                new File(new File(filePath).getParent()).mkdirs();

	                try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
	                    int len;
	                    while ((len = zipInputStream.read(buffer)) > 0) {
	                        fileOutputStream.write(buffer, 0, len);
	                    }
	                }

	                zipEntry = zipInputStream.getNextEntry();
	            }

	            System.out.println("Zip file extracted successfully: " + zipFilePath);

	        } catch (IOException e) {
	            e.printStackTrace();
	            throw e; // Rethrow the exception to indicate extraction failure
	        }

	}

}
