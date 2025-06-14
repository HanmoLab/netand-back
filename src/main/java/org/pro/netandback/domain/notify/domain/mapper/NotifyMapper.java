package org.pro.netandback.domain.notify.domain.mapper;

import org.mapstruct.*;
import org.pro.netandback.domain.notify.domain.entity.Notify;
import org.pro.netandback.domain.notify.dto.NotifyResponse;

@Mapper(componentModel = "spring")
public interface NotifyMapper {
	@Mapping(target = "createdAt", source = "entity.createdAt")
	NotifyResponse toDto(Notify entity);
}