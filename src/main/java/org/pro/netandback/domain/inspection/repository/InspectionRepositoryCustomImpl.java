package org.pro.netandback.domain.inspection.repository;

import java.util.List;

import org.pro.netandback.domain.inspection.model.entity.Inspection;
import org.pro.netandback.domain.inspection.model.entity.QInspection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InspectionRepositoryCustomImpl implements InspectionRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Inspection> searchByCompanyAndProduct(
		String companyName,
		String productName,
		Pageable pageable
	) {
		QInspection i = QInspection.inspection;
		BooleanBuilder builder = new BooleanBuilder();

		if (StringUtils.hasText(companyName)) {
			builder.and(i.company.name.containsIgnoreCase(companyName));
		}
		if (StringUtils.hasText(productName)) {
			builder.and(i.product.name.containsIgnoreCase(productName));
		}

		List<Inspection> content = queryFactory
			.selectFrom(i)
			.where(builder)
			.orderBy(i.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = queryFactory
			.select(i.count())
			.from(i)
			.where(builder)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}
}

