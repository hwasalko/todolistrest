package com.hwasalko.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 *  
 * - ToDoRef 모델 (할일의 참조정보 저장 엔티티)
 *   | seq | myId | refId
 * 
 * - getter와 setter는 lombok을 사용한다.
 */

@Entity
@Data
public class ToDoRef {
		
	// pk(seq)
	@Id
	@GeneratedValue
	private int seq;
	
	
	// 참조자(자신)의 id	
	private int myId;
	
	// 참조대상 할일 id
	private int refId;
	
	

}