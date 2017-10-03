package cz.novros.cp.web.security;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import cz.novros.cp.service.user.UserSecurityService;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CpAuthenticationProvider implements AuthenticationProvider {

	UserSecurityService userSecurityService;

	@Nullable
	public Authentication authenticate(@Nonnull final Authentication authentication) throws AuthenticationException {
		final String userName = authentication.getName().trim();
		final String password = authentication.getCredentials().toString().trim();

		final boolean logged = userSecurityService.loginUser(new cz.novros.cp.entity.User(userName, password, ImmutableSet.of()));

		if (logged) {
			final Collection<GrantedAuthority> grantedAuths = ImmutableSet.of(new SimpleGrantedAuthority("USER"));
			final User appUser = new User(userName, password, grantedAuths);
			return new UsernamePasswordAuthenticationToken(appUser, password, grantedAuths);
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(@Nonnull final Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}