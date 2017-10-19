package cz.novros.cp.web.view;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public ModelAndView home() {
		return displayHome(null);
	}

	@RequestMapping(value = "/add-tracking", method = RequestMethod.POST)
	public ModelAndView addTrackingNumbers(@ModelAttribute @Nullable final TrackingNumbersForm trackingNumbers) {
		if (trackingNumbers != null && !trackingNumbers.getTrackingNumbersCollection().isEmpty()) {
			applicationService.refreshParcels(trackingNumbers.getTrackingNumbersCollection());
			return displayParcelByTrackingNumbers(userService.addTrackingNumbers(getLoggedUsername(), trackingNumbers.getTrackingNumbersCollection()));
		} else {
			return home();
		}
	}

	@RequestMapping(value = "/remove-tracking", method = RequestMethod.GET) // FIXME - Maybe POST?
	public ModelAndView removeTrackingNumbers(@RequestParam @Nullable final String trackingNumbers) {
		if (trackingNumbers != null && !trackingNumbers.isEmpty()) {
			final Collection<String> numbers = Arrays.asList(trackingNumbers.split(CommonConstants.TRACKING_NUMBER_DELIMITER));
			parcelService.removeParcels(numbers);
			return displayParcelByTrackingNumbers(userService.removeTrackingNumbers(getLoggedUsername(), numbers));
		} else {
			return home();
		}
	}

	@RequestMapping(value = "/refresh-parcels", method = RequestMethod.GET)
	public ModelAndView refresh() {
		return displayHome(applicationService.refreshParcels(getTrackingNumbersOfCurrentUser()));
	}

	private ModelAndView displayParcelByTrackingNumbers(@Nullable final Collection<String> parcels) {
		return displayHome(getParcels(parcels));
	}

	private ModelAndView displayHome(@Nullable final Collection<Parcel> parcels) {
		final ModelAndView model = new ModelAndView("home");
		model.addObject("formTrackingNumbers", new TrackingNumbersForm());
		model.addObject("parcels", parcels == null ? getParcels(getTrackingNumbersOfCurrentUser()) : parcels);
		return model;
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
