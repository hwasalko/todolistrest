package com.hwasalko.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 *  
 * - 수정, 삭제 처리 시 response에 담아줄 처리결과 Entity
 * 
 * - getter와 setter는 lombok을 사용한다.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
		
	
	// 처리결과(true/false)
	private boolean result;
	
	
	// 메세지(json에 담겨질 메세지) - 서버측 메세지 등...
	private String msg;
	
}