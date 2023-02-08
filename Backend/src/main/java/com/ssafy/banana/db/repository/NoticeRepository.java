package com.ssafy.banana.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.banana.db.entity.Notice;
import com.ssafy.banana.dto.response.NoticeResponse;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
	@Query("select new com.ssafy.banana.dto.response.NoticeResponse(n) "
		+ "from Notice n "
		+ "where n.artist.id = :userSeq")
	List<NoticeResponse> findByUserSeq(Long userSeq);
}