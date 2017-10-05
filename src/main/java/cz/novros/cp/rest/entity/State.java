package cz.novros.cp.rest.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
public class State {

	@JsonProperty("id")
	String id;

	@JsonProperty("date")
	Date date;

	@JsonProperty("text")
	String text;

	@JsonProperty("postcode")
	Integer postcode;

	@JsonProperty("postOffice")
	String postOffice;

	@JsonProperty("idIcon")
	Integer idIcon;

	@JsonProperty("latitude")
	Double latitude;

	@JsonProperty("longitude")
	Double longitude;

	@JsonProperty("timeDeliveryAttempt")
	Date timeDeliveryAttempt;

	@JsonProperty("publicAccess")
	Boolean publicAccess;
}
