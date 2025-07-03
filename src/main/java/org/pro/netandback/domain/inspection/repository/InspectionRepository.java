package org.pro.netandback.domain.inspection.repository;

import org.pro.netandback.domain.inspection.model.entity.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> ,InspectionRepositoryCustom{
}
