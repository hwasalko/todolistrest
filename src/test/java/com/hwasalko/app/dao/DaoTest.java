package com.hwasalko.app.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.LessThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.hwasalko.app.dao.ToDoListRepasitoy;
import com.hwasalko.app.dao.ToDoRefListRepasitoy;
import com.hwasalko.app.entity.ToDo;

import lombok.extern.slf4j.Slf4j;



/**
 * dao 테스트
 * @author DongJin Lee
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {


	@Autowired
	private ToDoListRepasitoy toDoListRepasitoy;

	
	
	
	/**
	 *  ToDo 레파지토리 저장 테스트
	 * @throws Exception
	 */
	@Test
	public void toDoSaveTest() throws Exception {
		
		// 1. 데이터 저장
		ToDo savedToDo = toDoListRepasitoy.save(new ToDo("테스트 할일"));
		
		// 2. 검증
		assertEquals(savedToDo.getId(), 1);
		assertEquals(savedToDo.getJob(), "테스트 할일");
		assertEquals(savedToDo.isComplete(), false);
		assertNotNull(savedToDo.getFinalUpdateDate());
		assertNotNull(savedToDo.getRegDate());
		assertNull(savedToDo.getToDoRefs());
		
	}
	
	
	/**
	 *  ToDo 레파지토리 조회 테스트
	 * @throws Exception
	 */
	@Test
	public void toDofindAllTest() throws Exception {
		
		// 1. 데이터 저장
		toDoListRepasitoy.save(new ToDo("테스트 할일"));	
		
		// 2. 데이터 조회
		List<ToDo> resultToDoList = toDoListRepasitoy.findAll();
		
		// 2. 검증
		assertTrue( resultToDoList.size() > 0 );
		assertNotNull(resultToDoList);
	}
	
	
	/**
	 *  ToDo 레파지토리 수정 테스트
	 * @throws Exception
	 */
	@Test
	public void toDoUpdateTest() throws Exception {
			
		// 1. 데이터 저장
		toDoListRepasitoy.save(new ToDo("테스트 할일"));	
		
		// 2. 데이터 조회
		Optional<ToDo> toDo = toDoListRepasitoy.findById(1);
		
		// 3. 검증(수정전)
		assertNotNull( toDo );
		assertFalse( toDo.get().isComplete() );
		
		// 4. 수정
		toDo.get().setJob("수정후");
		toDo.get().setComplete(true);
		
		// 5. 검증(수정후)
		ToDo updatedToDo = toDoListRepasitoy.save(toDo.get());
		assertEquals(updatedToDo.getJob(), "수정후");
		assertTrue(updatedToDo.isComplete());
		
	}
	
	
	
	/**
	 *  ToDo 레파지토리 삭제 테스트
	 * @throws Exception
	 */
	@Test
	public void toDoDeleteTest() throws Exception {
		
		// 1. 데이터 저장
		ToDo todo = toDoListRepasitoy.save(new ToDo("테스트 할일"));	
		assertNotNull(todo);
		
		// 2. 데이터 삭제
		int count = toDoListRepasitoy.findAll().size();
		toDoListRepasitoy.deleteById(count-1);
		assertEquals(toDoListRepasitoy.findAll().size(), count-1);
		
		
		
		
		// 데이터 삭제
	}


}
