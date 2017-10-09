package cz.novros.cp.service.notification;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import cz.novros.cp.Application;
import cz.novros.cp.entity.Parcel;
import cz.novros.cp.entity.User;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MailService {

	//@Value("${mail.sender.name}")
	private static String SENDER = "info@czech-post-notifier"; // FIXME

	JavaMailSender mailSender;

	public void sendParcelsChange(@Nonnull final User user, @Nonnull final Collection<Parcel> parcels) {
		log.info("Sending information mail about parcels changed ({}) to user({}).", parcels.size(), user.getEmail());

		final SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject(getSubject(parcels));
		mailMessage.setText(getMessageBody(parcels));
		mailMessage.setFrom(SENDER);
		mailMessage.setSentDate(new Date());

		log.debug("Sending mail: {}", mailMessage);

		mailSender.send(mailMessage);
	}

	private String getSubject(@Nonnull final Collection<Parcel> parcels) {
		return Application.APPLICATION_NAME + " - changed parcels(" + parcels.size() + ") states";
	}

	private String getMessageBody(@Nonnull final Collection<Parcel> parcels) {
		final StringBuilder builder = new StringBuilder();

		builder.append("Hello,\n\n");
		builder.append("These parcels has changed state:\n");

		for (final Parcel parcel : parcels) {
			builder.append("- ").append(getParcelInfo(parcel)).append("\n");
		}

		builder.append("\nHave a nice day,\n").append(Application.APPLICATION_NAME);

		return builder.toString();
	}

	@Nonnull
	private String getParcelInfo(@Nonnull final Parcel parcel) {
		return parcel.getParcelTrackingNumber() + ": " + (parcel.getLastState() != null ? parcel.getLastState().displayTitle() : "");
	}
}
