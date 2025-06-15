package org.pro.netandback.domain.notify.domain.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.pro.netandback.domain.notify.domain.entity.Notify;
import org.pro.netandback.domain.notify.dto.NotifyResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotifyMapper {
	@Mapping(source = "id", target = "notificationId")
	NotifyResponse toNotifyDto(Notify notify);

	List<NotifyResponse> toNotifyDtoList(List<Notify> notifies);
}
