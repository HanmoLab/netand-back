package org.pro.netandback.domain.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.domain.user.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "emailVerified", constant = "false")
	@Mapping(target = "password", ignore = true)
	User toEntity(SignUpRequest dto);
}
