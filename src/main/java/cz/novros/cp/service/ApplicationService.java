package cz.novros.cp.service;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import cz.novros.cp.entity.Parcel;
import cz.novros.cp.service.cp.CzechPostService;
import cz.novros.cp.service.parcel.ParcelService;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ApplicationService {

	CzechPostService czechPostService;
	ParcelService parcelService;

	/**
	 * Refresh parcels by tracking numbers.
	 *
	 * @param trackingNumbers Parcel which will be updated.
	 *
	 * @return Refreshed parcels.
	 */
	public Collection<Parcel> refreshParcels(@RequestBody @Nonnull final String[] trackingNumbers) {
		log.info("Reading parcels with tracking numbers({}) from czech post service.", Arrays.toString(trackingNumbers));

		Collection<Parcel> parcels = czechPostService.readParcels(trackingNumbers);

		log.info("Parcels(count={}) with tracking numbers({}) were read from czech post service.", parcels.size(), Arrays.toString(trackingNumbers));
		log.info("Saving updated parcels(count={}) to parcel service.", parcels.size());

		parcels = parcelService.saveParcels(parcels);

		log.info("Updated parcels(count={}) in parcel service and application.", parcels.size());

		return parcels;
	}
}
