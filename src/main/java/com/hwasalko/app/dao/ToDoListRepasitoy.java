package com.hwasalko.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hwasalko.app.entity.ToDo;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 * 
 * JpaRepository 상속 인터페이스
 */

public interface ToDoListRepasitoy extends JpaRepository <ToDo, Integer> {

	// 파라미터로 넘겨준 ID를 참조하고있는 할일 목록이 있다면 반환(완료처리 시 validation을 위해 사용)
	@Query("select a from ToDo a, ToDoRef b where a.id = b.myId and a.complete = false and a.id != :paramRefId and b.refId = :paramRefId")
	List<ToDo> findByRefIdIsNotCompleted(@Param("paramRefId") int paramRefId);
	
	// 파라미터로 넘겨준 ID를 참조하고있는 할일 목록이 있다면 반환(삭제처리 시 validation을 위해 사용)
	@Query("select a from ToDo a, ToDoRef b where a.id = b.myId and a.id != :paramRefId and b.refId = :paramRefId")
	List<ToDo> findByRefIdIsAll(@Param("paramRefId") int paramRefId);
	
}