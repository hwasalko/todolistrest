package com.hwasalko.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

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


@Getter
@Setter
@Entity
public class ToDo {

	// id (pk)
	@Id
	@GeneratedValue
	int id;

	
	// 할일 (text)
	@NotNull
	@Column(nullable = false)
	@Size(min = 1, max = 4000)
	String job;
	
	
	// 작성일시
	@NotNull
	@Column(nullable = false)
	Date regDate;
	
	
	// 최종수정일시
	@NotNull
	@Column(nullable = false)
	Date fianlUpdateDate;
	
	
	// 완료여부
	@NotNull
	@Column(nullable = false)
	boolean isComplete;



}