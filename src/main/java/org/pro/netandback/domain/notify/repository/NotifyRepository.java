// package: org.pro.netandback.domain.notify.repository

package org.pro.netandback.domain.notify.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.pro.netandback.domain.notify.model.entity.Notify;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {

	List<Notify> findAllByReceiverOrderByCreatedAtDesc(User receiver);

	long deleteByIsReadTrueAndReadAtBefore(LocalDateTime threshold);

	void deleteByIdAndReceiverId(Long id, Long receiverId);
}
