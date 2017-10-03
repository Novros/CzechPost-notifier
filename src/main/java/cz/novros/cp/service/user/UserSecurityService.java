package cz.novros.cp.service.user;

import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableSet;
import com.google.common.hash.Hashing;

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
public class UserSecurityService {

	UserRepository userRepository;

	/**
	 * Register user in system.
	 *
	 * @param user User which will be registered to application.
	 *
	 * @return True if user was registered, otherwise false.
	 */
	public boolean registerUser(@Nonnull final User user) {
		final User databaseUser = new User(user.getEmail(), hashPassword(user.getPassword()), ImmutableSet.of());

		if (userRepository.findOne(databaseUser.getEmail()) != null) {
			return false;
		}

		return userRepository.save(databaseUser) != null;
	}

	/**
	 * Try login user to system.
	 *
	 * @param user User which will be logged into application.
	 *
	 * @return True if user is logged in, otherwise false.
	 */
	public boolean loginUser(@Nonnull final User user) {
		return userRepository.findByEmailAndPassword(user.getEmail(), hashPassword(user.getPassword())) != null;
	}

	private static String hashPassword(@Nonnull final String password) {
		return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
	}
}
