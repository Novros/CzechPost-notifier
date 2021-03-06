package cz.novros.cp.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Collections2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.entity.Parcel;
import cz.novros.cp.entity.User;
import cz.novros.cp.service.cp.CzechPostService;
import cz.novros.cp.service.notification.MailService;
import cz.novros.cp.service.parcel.ParcelService;
import cz.novros.cp.service.user.UserService;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationService {

	CzechPostService czechPostService;
	ParcelService parcelService;
	UserService userService;
	MailService mailService;

	/**
	 * Refresh parcels by tracking numbers.
	 *
	 * @param trackingNumbers Parcel which will be updated.
	 *
	 * @return Refreshed parcels.
	 */
	public Collection<Parcel> refreshParcels(@RequestBody @Nonnull final Collection<String> trackingNumbers) {
		return parcelService.saveParcels(czechPostService.readParcels(trackingNumbers));
	}

	@Scheduled(fixedRate = 3600000) // Run every 60 minutes
	public void refreshAllParcels() {
		final Set<String> allTrackingNumbers = userService.readAllTrackingNumbers();
		Collection<Parcel> updatedParcels = czechPostService.readParcels(allTrackingNumbers);

		updatedParcels = parcelService.saveParcels(updatedParcels);

		Collection<Parcel> oldParcels = parcelService.readParcels(allTrackingNumbers);
		checkParcels(oldParcels, updatedParcels);
	}

	private void checkParcels(@Nonnull final Collection<Parcel> oldParcels, @Nonnull final Collection<Parcel> newParcels) {
		for (final User user : userService.readAllUsers()) {
			sendChangedParcelsMail(user, oldParcels, Collections2.filter(newParcels, input -> user.getTrackingNumbers().contains(input.getParcelTrackingNumber())));
		}
	}

	private void sendChangedParcelsMail(@Nonnull final User user, @Nonnull final Collection<Parcel> oldParcels, @Nonnull final Collection<Parcel> newParcels) {
		final Collection<Parcel> changedParcels = new HashSet<>();

		for (final Parcel newParcel : newParcels) {
			for (final Parcel oldParcel : oldParcels) {
				if (newParcel.getParcelTrackingNumber().equals(oldParcel.getParcelTrackingNumber()) && isParcelChanged(oldParcel, newParcel)) {
					changedParcels.add(newParcel);
				}
			}
		}

		if (!changedParcels.isEmpty()) {
			mailService.sendParcelsChange(user, changedParcels);
		}
	}

	private boolean isParcelChanged(@Nonnull final Parcel a, @Nonnull final Parcel b) {
		if (a.getLastState() == null && b.getLastState() == null) {
			return false;
		} else if (a.getLastState() == null) {
			return true;
		} else if (b.getLastState() == null) {
			return true;
		} else {
			return !a.getLastState().getText().equals(b.getLastState().getText());
		}
	}
}
