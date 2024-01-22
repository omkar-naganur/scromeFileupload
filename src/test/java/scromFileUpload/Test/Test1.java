package scromFileUpload.Test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import TestComponents.BaseTest;
import scromFileUpload.PageObjects.LandingPage;
import scromFileUpload.PageObjects.converter;
import scromFileUpload.PageObjects.operation3;
import scromFileUpload.PageObjects.operations;

import org.testng.AssertJUnit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test1  extends BaseTest {
	
	ArrayList<String> lastsArray = new ArrayList<String>();
	
	@Test(dataProvider = "getdata")
	public void scromFileUpload(HashMap<String, String> input) throws Throwable
	{		
	
		operation3 op=new operation3(driver);
		lastsArray=op.ListFoldersInDirectory();

		LandingPage lp= new LandingPage(driver);
	//	lp.getFoldersName();		
		lp.gooleColudLogin(input.get("email"), input.get("pass"));
		
		for(int i=0; i<lastsArray.size(); i++) {
			Thread.sleep(10000);
		lp.gooleColudFileUpload(lastsArray.get(i));
		lp.ReadSnackbarMessage();
		}
		Thread.sleep(5000);
		
		//*************************
//		lp.linkValidation();
		
		// this line write for old code 
//		lp.DeleteFoldersInsideMainFolder();
//		lp.RenameTheZipsFilseFolder();
//		lp.createNewFolder();
//		
	}
	
	@Test(dataProvider = "getdata")
	public void Test(HashMap<String, String> input) throws Throwable
	{		
	
		operation3 op=new operation3(driver);
		lastsArray=op.ListFoldersInDirectory();

		LandingPage lp= new LandingPage(driver);
		lp.extractingZipFileToFolder();
	//	lp.getFoldersName();		
	//	lp.gooleColudLogin(input.get("email"), input.get("pass"));
				
	}
	
	
	// this below code working fine now
	@Test(dataProvider = "getdata")
	public void TestPart4(HashMap<String, String> input) throws Throwable
	{		
		converter cv= new converter(driver);
		lastsArray=cv.newZipFileConverter();
		
//		operation3 op=new operation3(driver);
//		lastsArray=op.ListFoldersInDirectory();

		LandingPage lp= new LandingPage(driver);
//		lp.gooleColudLogin(input.get("email"), input.get("pass"));
		
		for(int i=0; i<lastsArray.size(); i++) {
			System.out.println("I am uploading = "+lastsArray.get(i));
			System.out.println(i);
//			Thread.sleep(10000);
//		lp.gooleColudFileUpload(lastsArray.get(i));
//		lp.ReadSnackbarMessage();
		}
	//	Thread.sleep(5000);
		
		//*************************
//		lp.linkValidation();
		
		// this line write for old code 
		lp.DeleteFoldersInsideMainFolder();
		lp.RenameTheZipsFilseFolder();
		lp.createNewFolder();	
				
	}
		
	@DataProvider
	public Object[][] getdata()
	{
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("email","omkar@krishworks.com");
		map.put("pass","sisshribha");
		return new Object[][] {{map}};
	}

}
