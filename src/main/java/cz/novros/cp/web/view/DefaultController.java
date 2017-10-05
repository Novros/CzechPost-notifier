package cz.novros.cp.web.view;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.common.CommonConstants;
import cz.novros.cp.entity.Parcel;
import cz.novros.cp.service.ApplicationService;
import cz.novros.cp.service.parcel.ParcelService;
import cz.novros.cp.service.user.UserService;
import cz.novros.cp.web.view.entity.TrackingNumbersForm;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultController {

	UserService userService;
	ParcelService parcelService;
	ApplicationService applicationService;

	@GetMapping(value = {"/", "/home"})
	public String home(final Model model) {
		return displayParcels(model, null);
	}

	@PostMapping("/add-tracking")
	public String addTrackingNumbers(@ModelAttribute @Nullable final TrackingNumbersForm trackingNumbers, @Nonnull final Model model) {
		if (trackingNumbers != null && !trackingNumbers.getTrackingNumbersCollection().isEmpty()) {
			applicationService.refreshParcels(trackingNumbers.getTrackingNumbersCollection());
			return displayParcelByTrackingNumbers(model, userService.addTrackingNumbers(getLoggedUsername(), trackingNumbers.getTrackingNumbersCollection()));
		} else {
			return home(model);
		}
	}

	@GetMapping("/remove-tracking") // FIXME - Maybe POST?
	public String removeTrackingNumbers(@RequestParam @Nullable final String trackingNumbers, @Nonnull final Model model) {
		if (trackingNumbers != null && !trackingNumbers.isEmpty()) {
			final Collection<String> numbers = Arrays.asList(trackingNumbers.split(CommonConstants.TRACKING_NUMBER_DELIMITER));
			parcelService.removeParcels(numbers);
			return displayParcelByTrackingNumbers(model, userService.removeTrackingNumbers(getLoggedUsername(), numbers));
		} else {
			return home(model);
		}
	}

	@GetMapping("/refresh-parcels")
	public String refresh(@Nonnull final Model model) {
		return displayParcels(model, applicationService.refreshParcels(getTrackingNumbersOfCurrentUser()));
	}

	private String displayParcelByTrackingNumbers(@Nonnull final Model model, @Nullable final Collection<String> parcels) {
		return displayParcels(model, getParcels(parcels));
	}

	private String displayParcels(@Nonnull final Model model, @Nullable final Collection<Parcel> parcels) {
		model.addAttribute("formTrackingNumbers", new TrackingNumbersForm());
		model.addAttribute("parcels", parcels == null ? getParcels(getTrackingNumbersOfCurrentUser()) : parcels);
		return "home";
	}

	private Collection<String> getTrackingNumbersOfCurrentUser() {
		return userService.readTrackingNumbers(getLoggedUsername());
	}

	private Collection<Parcel> getParcels(@Nullable final Collection<String> trackingNumbers) {
		return trackingNumbers == null ? null : parcelService.readParcels(trackingNumbers);
	}

	private String getLoggedUsername() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User user = (User) auth.getPrincipal();
		return user.getUsername();
	}
}
