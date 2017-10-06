package cz.novros.cp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.common.BigDecimalComparator;
import cz.novros.cp.common.InfoBuilder;
import cz.novros.cp.common.InfoMapBuilder;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attributes {

	String parcelType;

	BigDecimal weight;

	String currency;

	Integer phoneType;

	String phoneName;

	String phoneNumber;

	BigDecimal cashOnDelivery;

	Integer quantity;

	Date storedTo;

	Integer storedDays;

	String originCountry;

	String targetCountry;

	Date deliveryDate;

	Date deliveryFrom;

	Date deliveryTo;

	@Nonnull
	public Map<String, String> getDisplayInfo() {
		return InfoMapBuilder.getBuilder()
				.add("Info", displayBasicInfo())
				.add("Country", displayCountry())
				.add("Phone", displayPhone())
				.add("Stored", displayStored())
				.add("Delivery", displayDelivery())
				.build();
	}

	@Nullable
	private String displayBasicInfo() {
		return InfoBuilder.getBuilder()
				.add("Quantity", quantity)
				.add("Weight", BigDecimalComparator.isZero(weight) ? null : weight, "kg")
				.add("Parcel type", parcelType)
				.add("Cash on delivery", BigDecimalComparator.isZero(cashOnDelivery) ? null : cashOnDelivery, currency)
				.build();
	}

	@Nullable
	private String displayPhone() {
		return InfoBuilder.getBuilder()
				.add("Name", phoneName)
				.add("Number", phoneNumber)
				.build();
	}

	@Nullable
	private String displayStored() {
		return InfoBuilder.getBuilder()
				.add("Stored", storedTo)
				.add("Max stored days", storedDays)
				.build();
	}

	@Nullable
	private String displayCountry() {
		return InfoBuilder.getBuilder()
				.add("Target", targetCountry)
				.add("Origin", originCountry)
				.build();
	}

	@Nullable
	private String displayDelivery() {
		return InfoBuilder.getBuilder()
				.add("Date", deliveryDate)
				.build();
	}
}
