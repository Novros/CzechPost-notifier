package cz.novros.cp.service.parcel;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.dao.ParcelRepository;
import cz.novros.cp.entity.Parcel;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ParcelService {

	ParcelRepository parcelRepository;

	/**
	 * Read parcel for tracking numbers.
	 *
	 * @param trackingNumbers Tracking numbers for which will be parcels read.
	 *
	 * @return Parcels with given tracking numbers.
	 */
	@Nonnull
	public Collection<Parcel> readParcels(@Nonnull final Collection<String> trackingNumbers) {
		final Collection<Parcel> databaseParcels = parcelRepository.findByParcelTrackingNumberIn(trackingNumbers);
		return databaseParcels == null ? ImmutableList.of() : databaseParcels;
	}

	/**
	 * Save parcels into database.
	 *
	 * @param parcels Parcels which will be saved.
	 *
	 * @return Return currently saved parcels.
	 */
	@Nonnull
	public Collection<Parcel> saveParcels(@Nonnull Collection<Parcel> parcels) {
		return Lists.newArrayList(parcelRepository.save(parcels));
	}

	/**
	 * Remove parcels from database.
	 *
	 * @param trackingNumbers Parcels which will be deleted.
	 */
	public void removeParcels(@Nonnull final Collection<String> trackingNumbers) {
		for (final String tracking : trackingNumbers) {
			parcelRepository.delete(tracking);
		}
	}
}
