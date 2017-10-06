package cz.novros.cp.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.common.CommonConstants;
import cz.novros.cp.rest.entity.Parcel;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CzechPostRestClient {

	private static final int MAXIMAL_COUNT_OF_PARCELS = 10;

	private static final String PARCEL_HISTORY_URL = "https://b2c.cpost.cz/services/ParcelHistory/getDataAsJson";
	private static final String PARCEL_PARAM = "idParcel";
	private static final String LANGUAGE_PARAM = "language";
	private static final String ENGLISH_LANGUAGE = "en";

	RestTemplate restTemplate;

	@Autowired
	public CzechPostRestClient(@Nonnull final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Nonnull
	public Collection<Parcel> readParcels(@Nonnull final Collection<String> parcelIds) {
		final Collection<Parcel> parcelHistories = new HashSet<>();

		partitionCollection(parcelIds, MAXIMAL_COUNT_OF_PARCELS)
				.forEach(ids -> parcelHistories.addAll(Arrays.asList(readParcelsForMaxN(ids))));

		return parcelHistories;
	}

	@Nonnull
	private Parcel[] readParcelsForMaxN(@Nonnull final Collection<String> parcelIds) {
		final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PARCEL_HISTORY_URL)
				.queryParam(LANGUAGE_PARAM, ENGLISH_LANGUAGE)
				.queryParam(PARCEL_PARAM, parcelIds.stream().collect(Collectors.joining(CommonConstants.TRACKING_NUMBER_DELIMITER)));
		return restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, Parcel[].class).getBody();
	}

	@Nonnull
	private <T> Collection<Collection<T>> partitionCollection(final Collection<T> collection, final int size) {
		final Collection<Collection<T>> returnList = new ArrayList<>();
		final Collection<T> tenList = new HashSet<>(size);

		for (final T item : collection) {
			if (tenList.size() == size) {
				returnList.add(new ArrayList<>(tenList));
				tenList.clear();
			}

			tenList.add(item);
		}

		if (!tenList.isEmpty()) {
			returnList.add(tenList);
		}

		return returnList;
	}
}
