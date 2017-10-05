package cz.novros.cp.rest.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attributes {

	@JsonProperty("parcelType")
	String parcelType;

	@JsonProperty("weight")
	BigDecimal weight;

	@JsonProperty("currency")
	String currency;

	@JsonProperty("telefonTyp")
	Integer phoneType;

	@JsonProperty("telefonNazev")
	String phoneName;

	@JsonProperty("telefonCislo")
	String phoneNumber;

	@JsonProperty("dobirka")
	BigDecimal cashOnDelivery;

	@JsonProperty("kusu")
	Integer picies;

	@JsonProperty("ulozeniDo")
	Date storedTo;

	@JsonProperty("ulozeniDoba")
	Integer storedDays;

	@JsonProperty("zemePuvodu")
	String originCountry;

	@JsonProperty("zemeUrceni")
	String targetCountry;

	@JsonProperty("dorucovaniDate")
	Date deliveryDate;

	@JsonProperty("dorucovaniOd")
	Date deliveryFrom;

	@JsonProperty("dorucovaniDo")
	Date deliveryTo;
}
