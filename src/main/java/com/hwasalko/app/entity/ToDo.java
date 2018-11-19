package com.hwasalko.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class ToDo {

	// id (pk)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	
	// 할일(Todo)의 참조 Id 
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="myId")	// 여러 할일을 참조할 수 있으니 1:N 관계로 설정
	private Collection<ToDoRef> toDoRefs;
	
	
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
	
	
	
	// 생성자
	public ToDo(String job) {
		this.job = job;
	}

	
	// 추가 매소드
	public boolean addToDoRef(ToDoRef t){
		if( toDoRefs == null ){
			toDoRefs = new ArrayList<ToDoRef>();
		}
		return toDoRefs.add(t);
	}
	

	

}