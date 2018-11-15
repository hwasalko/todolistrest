package com.hwasalko.app.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 *  
 * - ToDo 모델
 *   | id | 할일 | 작성일시 | 최종수정일시 | 완료처리 |
 * 
 * - getter와 setter는 lombok을 사용한다.
 */

@Entity
@Data
public class ToDo {

	// id (pk)
	@Id
	@GeneratedValue
	private int id;

	
	// 할일 (text)
	@NotNull
	@Size(min = 1, max = 4000)
	@Column(nullable = false, length=4000)
	private String job;
	
	
	// 작성일시
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime regDate;
	
	
	// 최종수정일시
	@Column(nullable = false, updatable = true)
	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime finalUpdateDate;
	
	
	// 완료여부
	@NotNull
	@Column(nullable = false)
	private boolean complete;
	
	


	

}