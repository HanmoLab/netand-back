package org.pro.netandback.core.auth.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class SignUpMapper {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	public abstract User toEntity(SignUpRequest dto);

	@AfterMapping
	protected void encodePassword(SignUpRequest dto, @MappingTarget User user) {
		String rawPassword = dto.getPassword();
		if (rawPassword != null && !rawPassword.isBlank()) {
			user.setPassword(passwordEncoder.encode(rawPassword));
		}
	}
}
