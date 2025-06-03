package org.pro.netandback.core.auth.jwt;

import java.util.Collection;
import java.util.Collections;

import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.model.type.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class UserDetailsImpl implements UserDetails {

	private final User user;

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		UserType type = user.getUserType();
		String roleName = "ROLE_" + (type == null ? "GUEST" : type.name());
		return Collections.singletonList(new SimpleGrantedAuthority(roleName));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getEmailVerified() != null && user.getEmailVerified();
	}
}
