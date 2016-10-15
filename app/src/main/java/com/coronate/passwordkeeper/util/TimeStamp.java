package com.coronate.passwordkeeper.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {

	public static String getCurrentTimeStamp(){
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
		Date currentDate = new Date();
		String strDate = sdfDate.format(currentDate);
		return strDate;
	}

	public static String getCurrentDate(){
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
		Date currentDate = new Date();
		String strDate = sdfDate.format(currentDate);
		return strDate;
	}

}
