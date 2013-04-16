package com.domo.selenium.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class logger {
	
	public static void Log(String LOG_FILE, String MESSAGE) throws IOException{
	
	FileWriter fstream; 
	fstream = new FileWriter(LOG_FILE,true); 
	BufferedWriter out_file = new BufferedWriter(fstream);
	Date timeStamp = new Date();
	
	out_file.write("["+timeStamp+"] " + MESSAGE); // REPORT.log file writer
	System.out.println("["+timeStamp+"] " + MESSAGE); //console file writer
	//Reporter.log("["+timeStamp+"] " + MESSAGE); // TestNG reporter writer
	out_file.newLine();
	out_file.flush();
	out_file.close();
	
	}
// definire static String LOG_FILE = "output/logs/REPORT.log";
// apelare logger.Log(LOG_FILE, "blablaba");
}
