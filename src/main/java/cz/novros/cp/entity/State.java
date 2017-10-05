package cz.novros.cp.entity;

import java.util.Date;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.common.CommonConstants;
import cz.novros.cp.common.InfoBuilder;
import cz.novros.cp.common.InfoMapBuilder;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	Date date;

	String text;

	Integer postcode;

	String postOffice;

	Double latitude;

	Double longitude;

	Date timeDeliveryAttempt;

	@Nonnull
	public Map<String, String> getDisplayInfo() {
		return InfoMapBuilder.getBuilder()
				.add("Post office", displayPostOffice())
				.add("GPS", displayGps())
				.build();
	}

	@Nullable
	private String displayPostOffice() {
		return InfoBuilder.getBuilder()
				.add("Name", postOffice)
				.add("Number", postcode)
				.build();
	}

	@Nullable
	private String displayGps() {
		return InfoBuilder.getBuilder()
				.add("Latitude", latitude)
				.add("Longitude", longitude)
				.build();
	}

	@Nonnull
	public String displayTitle() {
		return CommonConstants.DATE_FORMAT.format(date) + " " + text;
	}
}
