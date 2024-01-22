package scromFileUpload.PageObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class LandingPage {

	WebDriver driver;
	
	public LandingPage(WebDriver driver) throws Throwable
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (xpath ="//td[@role='rowheader']//a[@href]")
	List<WebElement> names;
	
	@FindBy (xpath = "//div[@class='cfc-snack-bar-content']")
	WebElement snakeBar;
	
	//important variables
	ArrayList<String> lastsArray = new ArrayList<String>();
	ArrayList<String> foldersName = new ArrayList<String>();
	String dates =date();
	
//	public void getFoldersName()
//	{
//		String directoryPath = "/media/kwsys4/nonOsPartition/AutomationSerien4.0/scromFileUpload/scromFileUpload/ExtractedFolders";
//
//        File directory = new File(directoryPath);
//
//        if (directory.exists() && directory.isDirectory()) {
//            String[] subDirectories = directory.list((current, name) -> new File(current, name).isDirectory());
//
//            if (subDirectories != null && subDirectories.length > 0) {
//                System.out.println("List of folders in " + directoryPath + ":");
//                for (String subDirectory : subDirectories) {
//                    System.out.println(subDirectory);
//                    foldersName.add(subDirectory);
//                }
//            } else {
//                System.out.println("No folders found in the directory.");
//            }
//        } else {
//            System.out.println("The specified path is not a valid directory.");
//        }
//	}

	public void gotoLoginPage () throws InterruptedException 
	{
		// production
	//	driver.get("https://console.cloud.google.com/storage/browser/kw-mumbai-test/temp_scorm?pageState=(%22StorageObjectListTable%22:(%22f%22:%22%255B%255D%22))&project=kw-store&prefix=&forceOnObjectsSortingFiltering=false");
		
		// development
		driver.get("https://console.cloud.google.com/storage/browser/sereindevcontent.kdev.co.in;tab=objects?forceOnBucketsSortingFiltering=true&project=serein-devqa-internal-gcp&prefix=&forceOnObjectsSortingFiltering=false");
		
	}
	
	public void gooleColudLogin (String Username, String password) throws InterruptedException 
	{
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Username);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[@jscontroller='soHxf'])[2]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[@jscontroller='soHxf'])[2]")).click();
		
	}
	
	public void gooleColudFileUpload (String filePath) throws InterruptedException, FileNotFoundException 
	{
		Thread.sleep(5000);
	    WebElement fileInput = driver.findElement(By.xpath("(//div[@jslog='54826'])//input[2]"));
	    fileInput.sendKeys(filePath);
	    Thread.sleep(2000); 
	}

	public void linkValidation() throws Throwable {
		
		FileWriter fw = new FileWriter(System.getProperty("user.dir")+"//reports//googleClodeFoldersLink.txt");
		
		for(int i=0; i<names.size(); i++) {
			String CoursesNames= names.get(i).getText().replace("/", "");
			System.out.println(CoursesNames);
			
			if(foldersName.contains(CoursesNames)) {
			String links=names.get(i).getAttribute("href");
			String CoursesName= names.get(i).getText();
			System.out.println(CoursesName);
			System.out.println(links);
			fw.write(CoursesName+" = "+links);
			fw.write("\n");
			names.get(i).click();
			driver.findElement(By.xpath("//*[contains(text(),'res/ ')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("( //*[contains(text(),'index.html ')])[1]")).click();
			Thread.sleep(5000);
			String mailLINK=driver.findElement(By.xpath("//div[@data-test-id='public-url-row']//a")).getAttribute("href");
			fw.write(mailLINK);
			fw.write("\n");
			driver.get("https://console.cloud.google.com/storage/browser/sereindevcontent.kdev.co.in;tab=objects?project=serein-devqa-internal-gcp&prefix=&forceOnObjectsSortingFiltering=false");
			Thread.sleep(5000);
			}
			else {
				if(i==99) {
					System.out.println(i);
					driver.findElement(By.xpath("(//button[@jslog='54138;track:generic_click'])[4]")).click();
				//	driver.findElement(By.xpath("//button[@id='_0rif_cfc-labelledby-message-goog_1565628776']")).click();
					i=0;
				}
			}
		}
			fw.close();
		}
	
	
	 public void DeleteFoldersInsideMainFolder() {
	        // Specify the path of the parent folder
	        String parentFolderPath = System.getProperty("user.dir")+"//ExtractedFolders";

	        // Call the method to delete folders inside the parent folder
	        deleteFoldersInFolder(parentFolderPath);
	    }

	    public static void deleteFoldersInFolder(String parentFolderPath) {
	        // Create a File object for the parent folder
	        File parentFolder = new File(parentFolderPath);

	        // Check if the parent folder exists
	        if (parentFolder.exists() && parentFolder.isDirectory()) {
	            // List all files and subdirectories in the parent folder
	            File[] files = parentFolder.listFiles();

	            // Iterate through the files and folders
	            for (File file : files) {
	                // Check if it's a directory (folder)
	                if (file.isDirectory()) {
	                    // Delete the folder and its contents
	                	System.out.println("i am Deleteing = "+ file);
	                    deleteFolder(file);
	                }
	            }
	        } else {
	            System.out.println("Parent folder does not exist or is not a directory.");
	        }
	    }

	    public static void deleteFolder(File folder) {
	        // Check if the folder exists
	        if (folder.exists() && folder.isDirectory()) {
	            // List all files and subdirectories in the folder
	            File[] files = folder.listFiles();

	            // Iterate through the files and folders
	            for (File file : files) {
	                if (file.isDirectory()) {
	                    // Recursive call to delete subfolders
	                    deleteFolder(file);
	                } else {
	                    // Delete the file
	                    file.delete(); 
	                }
	            }

	            // Delete the empty folder
	         
	            folder.delete();
	            System.out.println("Folders Deleted");
	           
	        }
	    }

		 public ArrayList<String> extractingZipFileToFolder() {
			 
			 String sourceFolderPath = System.getProperty("user.dir")+"//StorageOfZipFiles";
			 System.out.println("hello baby"
					 +sourceFolderPath);
		  //      String sourceFolderPath = "/media/kwsys4/nonOsPartition/AutomationSerien4.0/scromFileUpload/scromFileUpload/StorageOfZipFiles";
		        String destinationRootFolder = System.getProperty("user.dir")+"//ExtractedFolders";
		    //    String destinationRootFolder = "/media/kwsys4/nonOsPartition/AutomationSerien4.0/scromFileUpload/scromFileUpload/ExtractedFolders";
		        
		        extractZipFiles(sourceFolderPath, destinationRootFolder);
		        System.out.println(lastsArray);
		        return lastsArray ;
		    }
		 
		    public void extractZipFiles(String sourceFolderPath, String destinationRootFolder) {
		        File sourceFolder = new File(sourceFolderPath);

		        // Check if the source folder exists
		        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
		            System.out.println("Source folder does not exist or is not a directory.");
		            return;
		        }

		        // Get a list of all files in the source folder
		        File[] files = sourceFolder.listFiles();
		        for(File file : files) {
		        	System.out.println(file.getName());
		        }
		        if (files == null || files.length == 0) {
		            System.out.println("Source folder is empty.");
		            return;
		        }

		        // Iterate through each file in the source folder
		        for (File file : files) {
		            if (file.isFile() && file.getName().toLowerCase().endsWith(".zip")) {
		            	System.out.println("okk");
		                // Extract the contents of the ZIP file
		                extractZipFile(file, destinationRootFolder);
		            }
		        }
		    }

		    public void extractZipFile(File zipFile, String destinationRootFolder) {
		    	
		        try (ZipFile zip = new ZipFile(zipFile)) {
		        	
		            // Create a folder for each ZIP file in the destination root folder
		        	String destinationFolderName = zipFile.getName().replaceAll(".zip$", "").replaceAll(" ", "_") + "_" + System.currentTimeMillis();
		            foldersName.add(destinationFolderName);
		            Path destinationFolderPath = Paths.get(destinationRootFolder, destinationFolderName);
		            Files.createDirectories(destinationFolderPath);

		            // Extract each entry in the ZIP file
		            Enumeration<? extends ZipEntry> entries = zip.entries();
		            while (entries.hasMoreElements()) {
		                ZipEntry entry = entries.nextElement();
		                Path entryPath = Paths.get(destinationFolderPath.toString(), entry.getName());

		                // Ensure that the entry is within the destination folder to avoid extraction outside
		                if (!entryPath.normalize().startsWith(destinationFolderPath.normalize())) {
		                    throw new IOException("Entry is outside the target directory: " + entry.getName());
		                }

		                // Create parent directories if necessary
		            	   Files.createDirectories(entryPath.getParent());
		               
		                

		                // Extract the entry
		                Files.copy(zip.getInputStream(entry), entryPath, StandardCopyOption.REPLACE_EXISTING);
		            }

		            System.out.println("ZIP file " + zipFile.getName() + " extracted to " + destinationFolderPath);
		            lastsArray.add(destinationFolderPath.toString());
		            
		        } catch (IOException e) {
		            System.err.println("Error extracting ZIP file "  + e);
		        }
		    }
	
		    public void ReadSnackbarMessage() throws Throwable {
		      
		    	Boolean sucessmessage=false;
				String exptedText = "files successfully uploaded";
				while(!sucessmessage) {
					Thread.sleep(5000);
					waitForElementToAppear(snakeBar);
				 // Read the text content of the Snackbar
				    String snackbarText = snakeBar.getText();
				    if(snackbarText.contains(exptedText))
				    {
				    	System.out.println("Snackbar Message: " + snackbarText);
				    	sucessmessage=true;
				    }
				    else {
				    	System.out.println("Snackbar Message: " + snackbarText);
					}
				        
				}
				
		    }
		    
		    public void waitForElementToAppear(WebElement snakeBar2) {

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
				wait.until(ExpectedConditions.visibilityOf(snakeBar2));
			}
		    
		   			
			public void RenameTheZipsFilseFolder () throws Throwable
			{
				// Specify the path to the folder you want to rename
		        Path oldFolderPath = Paths.get(System.getProperty("user.dir")+"//StorageOfZipFiles");

		        // Specify the new name for the folder
		        String newFolderName = dates;

		        try {
		            // Resolve the new path with the updated folder name
		            Path newFolderPath = oldFolderPath.resolveSibling(newFolderName);

		            // Rename the folder
		            Files.move(oldFolderPath, newFolderPath, StandardCopyOption.REPLACE_EXISTING);

		            System.out.println("Folder renamed successfully.");
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

			}
			 
				public void createNewFolder () throws Throwable
				{
				  // Specify the path for the new folder
			        Path folderPath = Paths.get(System.getProperty("user.dir")+"//StorageOfZipFiles");

			        try {
			            // Create the new folder
			            Files.createDirectories(folderPath);

			            System.out.println("Folder created successfully.");
			        } catch (Exception e) {
			            e.printStackTrace();
			        }

				}
			
			public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
			public String date() {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
				return sdf.format(cal.getTime());
				}

}
