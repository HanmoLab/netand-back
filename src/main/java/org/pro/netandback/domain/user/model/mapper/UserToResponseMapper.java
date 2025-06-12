package org.pro.netandback.domain.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pro.netandback.core.auth.dto.response.UserResponse;
import org.pro.netandback.domain.s3.service.ProfileImageService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses= ProfileImageService.class)
public abstract class UserToResponseMapper {

	@Autowired
	protected ProfileImageService profileImageService;

	@Mapping(target= "profileImageUrl", expression = "java(profileImageService.getProfileImageUrl(user.getId()))")
	public abstract UserResponse toResponse(User user);
}
