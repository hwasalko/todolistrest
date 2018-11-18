package com.hwasalko.app.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class ToDoRef {
		
	
	
	// PK
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int seq;
	
	
	//참조자 ID
	private int myId;
	
	
	// 참조대상 ID
	private int refId;
	
	
	// 생성자
	public ToDoRef(int myId, int refId ) {
		this.myId = myId;
		this.refId = refId;
	}
	
	

}