package cz.novros.cp.common;

import java.text.SimpleDateFormat;

import lombok.experimental.UtilityClass;

/**
 * This class contains all common constants.
 */
@UtilityClass
public class CommonConstants {

	public static final String TRACKING_NUMBER_DELIMITER = ";";
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
}
