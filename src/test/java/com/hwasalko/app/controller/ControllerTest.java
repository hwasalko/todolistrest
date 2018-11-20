package com.hwasalko.app.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


/**
 * 컨트롤러 테스트
 * @author DongJin Lee
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {


	@Autowired
	private MockMvc mockMvc;

	
	
	
	
	/**
	 *  index 페이지(웹페이지) 호출 테스트
	 * @throws Exception
	 */
	@Test
	public void indexPageRequestTest() throws Exception {
		
		mockMvc.perform( get("/") )
				.andExpect( status().isOk() )
				.andExpect( content().string(containsString("<html")) );
	}
	
	
	/**
	 *  POST 테스트 (/todos)
	 * @throws Exception
	 */
	@Test
	public void postTodosTest() throws Exception {
		
		mockMvc.perform( post("/todos").param("job", "테스트 할일") )
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
	            .andDo(print());
		
	}
	

	
	/**
	 *  GET 테스트 (/todos)
	 * @throws Exception
	 */
	@Test
	public void getTodosTest() throws Exception {
		
		mockMvc.perform(get("/todos"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
	            .andDo(print());
		
	}
	
	
	/**
	 *  GET 테스트 (/todos/{id})
	 * @throws Exception
	 */
	@Test
	public void getTodosByIdTest() throws Exception {
		
		mockMvc.perform(get("/todos/1"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
	            .andDo(print());
		
	}
	
	
	
	/**
	 *  GET 테스트 (/todos/all)
	 * @throws Exception
	 */
	@Test
	public void getTodosAllTest() throws Exception {
		
		mockMvc.perform(get("/todos/all"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
	            .andDo(print());
		
	}
	
	
	
	
	/**
	 *  DELETE 테스트 (/todos/{id})
	 * @throws Exception
	 */
	@Test
	public void deleteTodosTest() throws Exception {
		
		// 삭제 테스트를 위해 데이터 1건 저장
		postTodosTest();
		
		mockMvc.perform(delete("/todos/1"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
	            .andDo(print());
		
	}
	
	
	
	/**
	 *  PUT 테스트 (/todos/{id})
	 * @throws Exception
	 */
	@Test
	public void putTodosTest() throws Exception {
		
		// 수정 테스트를 위해 데이터 1건 저장
		postTodosTest();
		
		mockMvc.perform( put("/todos/1")
										.param("job", "할일(수정사항)")
				)
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
	            .andDo(print());
		
	}

	
	
	/**
	 *  PATCH 테스트 (/todos/{id})
	 * @throws Exception
	 */
	@Test
	public void patchTodosTest() throws Exception {
		
		// 처리완료 테스트를 위해 데이터 1건 저장
		postTodosTest();
		
		mockMvc.perform( patch("/todos/1")
										.param("complete", "true")
				)
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
	            .andDo(print());
		
	}


}
