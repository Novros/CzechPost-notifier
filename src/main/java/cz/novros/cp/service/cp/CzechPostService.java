package cz.novros.cp.service.cp;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.entity.Parcel;
import cz.novros.cp.rest.CzechPostRestClient;
import cz.novros.cp.rest.EntityConverter;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CzechPostService {

	CzechPostRestClient restClient;

	/**
	 * Read parcels by tracking numbers.
	 *
	 * @param trackingNumbers Collections of tracking numbers, which will be read from rest api.
	 *
	 * @return Collection of read parcels, it might not have some parcels, because they not exists.
	 */
	public Collection<Parcel> readParcels(@Nullable final Collection<String> trackingNumbers) {
		if (trackingNumbers == null || trackingNumbers.isEmpty()) {
			return ImmutableList.of();
		} else {
			return EntityConverter.convertParcels(restClient.readParcels(trackingNumbers));
		}
	}
}
