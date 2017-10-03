package cz.novros.cp.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attributes {

	String parcelType;

	BigDecimal weight;

	String currency;

	int phoneType;

	String phoneName;

	String phoneNumber;

	BigDecimal cashOnDelivery;

	int quantity;

	Date storedTo;

	int storedDays;

	String originCountry;

	String targetCountry;

	Date deliveryDate;

	Date deliveryFrom;

	Date deliveryTo;
	
	// TODO Maybe rewrite to something better - with null checking?
	public String displayBasicInfo() {
		return "Quantity: " + quantity + "  Weight: " + weight + " kg\nType: " + parcelType + "  Cash on delivery: " + cashOnDelivery;
	}

	public String displayPhone() {
		return phoneName + " " + phoneNumber;
	}

	public String displayStored() {
		return "Stored: " + storedTo + " Max stored days: " + storedDays;
	}

	public String displayCountry() {
		return "Target: " + targetCountry + " Origin: " + originCountry;
	}

	public String displayDelivery() {
		return "Date " + deliveryDate;
	}
}
