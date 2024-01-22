package scromFileUpload.PageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.zip.ZipInputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class operations {

	WebDriver driver;
	
	public operations(WebDriver driver) throws Throwable
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
	//String dates =date();

	 public void ZipExtractor() {
	        String sourceFolderPath = System.getProperty("user.dir")+"//StorageOfZipFiles";
	        String destinationFolderPath = System.getProperty("user.dir")+"//ExtractedFolders";

	        // Call the method to extract zip files
	        extractZipFiles(sourceFolderPath, destinationFolderPath);
	    }

	    public void extractZipFiles(String sourceFolderPath, String destinationFolderPath) {
	        File sourceFolder = new File(sourceFolderPath);
	        File[] zipFiles = sourceFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".zip"));

	        if (zipFiles != null) {
	            for (File zipFile : zipFiles) {
	                extractZipFile(zipFile, destinationFolderPath);
	            }
	        } else {
	            System.out.println("No zip files found in the source folder.");
	        }
	    }

	    private void extractZipFile(File zipFile, String destinationFolderPath) {
	        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
	            byte[] buffer = new byte[1024];
	            ZipEntry zipEntry;

	            // Create destination folder if not exists
	            File destinationFolder = new File(destinationFolderPath);
	            if (!destinationFolder.exists()) {
	                destinationFolder.mkdirs();
	            }

	            // Iterate through each entry in the zip file
	            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
	                String entryName = zipEntry.getName();
	                File entryFile = new File(destinationFolder, entryName);

	                // Create parent directories if not exists
	                entryFile.getParentFile().mkdirs();

	                // Extract the entry
	                try (FileOutputStream fos = new FileOutputStream(entryFile)) {
	                    int length;
	                    while ((length = zipInputStream.read(buffer)) > 0) {
	                        fos.write(buffer, 0, length);
	                    }
	                }
	                System.out.println("Extracted: " + entryFile.getAbsolutePath());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	
}
