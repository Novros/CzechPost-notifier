package cz.novros.cp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	Date date;

	String text;

	int postcode;

	String postOffice;

	double latitude;

	double longitude;

	Date timeDeliveryAttempt;

	public String displayPostOffice() {
		return postOffice + " " + postcode;
	}

	public String displayGps() {
		return latitude + " " + longitude;
	}

	public String displayTitle() {
		return text + " " + date.toString();
	}
}
