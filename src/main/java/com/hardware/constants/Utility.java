package com.hardware.constants;

import org.joda.time.DateTime;

public class Utility {

	
	public static DateTime convertDateToDateTime(java.util.Date date){
		return new DateTime(date);
	}
}