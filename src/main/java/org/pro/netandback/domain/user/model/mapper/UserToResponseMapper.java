package org.pro.netandback.domain.user.model.mapper;

import org.mapstruct.Mapper;
import org.pro.netandback.core.auth.dto.response.UserResponse;
import org.pro.netandback.domain.user.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserToResponseMapper {
	UserResponse toResponse(User user);
}
