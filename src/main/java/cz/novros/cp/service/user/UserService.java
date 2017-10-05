package cz.novros.cp.service.user;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import cz.novros.cp.dao.UserRepository;
import cz.novros.cp.entity.User;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

	UserRepository userRepository;

	/**
	 * Add tracking numbers to user.
	 *
	 * @param username        Username of user.
	 * @param trackingNumbers Tracking numbers which will be added.
	 *
	 * @return Return actualized tracking numbers of user.
	 */
	@Nonnull
	public Collection<String> addTrackingNumbers(@Nonnull final String username, @Nonnull final Collection<String> trackingNumbers) {
		User user = userRepository.findOne(username);

		if (user == null) {
			return ImmutableSet.of();
		}

		user.getTrackingNumbers().addAll(trackingNumbers);
		user = userRepository.save(user);

		return user.getTrackingNumbers();
	}

	/**
	 * Remove tracking numbers from user.
	 *
	 * @param username        Username of user.
	 * @param trackingNumbers Tracking numbers which will be added.
	 *
	 * @return Return actualized tracking numbers of user.
	 */
	@Nonnull
	public Collection<String> removeTrackingNumbers(@Nonnull final String username, @Nonnull final Collection<String> trackingNumbers) {
		User user = userRepository.findOne(username);

		if (user == null) {
			return ImmutableSet.of();
		}

		user.getTrackingNumbers().removeAll(trackingNumbers);
		user = userRepository.save(user);

		return user.getTrackingNumbers();
	}

	/**
	 * Read all tracking number of user.
	 *
	 * @param username Username of user.
	 *
	 * @return Collection of tracking number for user.
	 */
	@Nonnull
	public Set<String> readTrackingNumbers(@Nonnull final String username) {
		final User user = userRepository.findOne(username);

		if (user == null) {
			return ImmutableSet.of();
		}

		return user.getTrackingNumbers();
	}
}
