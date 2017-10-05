package cz.novros.cp.web.view.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.common.CommonConstants;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackingNumbersForm {

	String trackingNumbers;

	@Nonnull
	public Collection<String> getTrackingNumbersCollection() {
		if (trackingNumbers == null) {
			return ImmutableList.of();
		}

		return Arrays.asList(trackingNumbers.split(CommonConstants.TRACKING_NUMBER_DELIMITER));
	}
}
