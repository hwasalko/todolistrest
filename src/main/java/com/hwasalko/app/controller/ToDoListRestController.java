package com.hwasalko.app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwasalko.app.entity.Response;
import com.hwasalko.app.entity.ToDo;
import com.hwasalko.app.entity.ToDoRef;

import com.hwasalko.app.dao.ToDoListRepasitoy;
import com.hwasalko.app.dao.ToDoRefListRepasitoy;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 * 
 * ToDo 리스트 REST API 컨트롤러
 */
@RestController
public class ToDoListRestController {

	@Autowired
	private ToDoListRepasitoy toDoListRepasitoy;

	@Autowired
	private ToDoRefListRepasitoy toDoRefListRepasitoy;
	
	
	// todo 등록(insert) API (methoed = POST)
	@PostMapping("/todos")
	public ToDo add(
			@Valid ToDo toDo,
			String refIds) 
	{
		
		// 1. 할일 DB 저장
		ToDo toDoData = toDoListRepasitoy.save(toDo);	// DB insert
		
		// 2. 할일 참조값 저장
		if( refIds != null && !refIds.equals("") ) {
			for( String refId : refIds.split(",") ) {
					toDoData.addToDoRef(
										toDoRefListRepasitoy.save( new ToDoRef( toDoData.getId(), Integer.parseInt(refId)  )) 
					);
			}
		}
		
		
		return toDoData;	
	}

	// todo 조회(select) API (method = GET)
	// 페이징 처리를 위해 Pagination 정보를 포함하여 리턴
	@GetMapping("/todos")
	public Page<ToDo> list(
				@PageableDefault(sort = { "id" }, direction = Direction.ASC, size=5) Pageable pageable // 기본 페이징 정보 
			)	
	{	

		Page<ToDo> toDoList = toDoListRepasitoy.findAll(pageable);

		return toDoList;
	}
	
	// todo 조회(select) API (method = GET)
	// 페이징 요소가 없는 단순 전체리스트 조회
	@GetMapping("/todos/all")
	public List<ToDo> totalList()	
	{	
		List<ToDo> toDoList = toDoListRepasitoy.findAll();

		return toDoList;
	}
	
	// todo 상세조회(select) API (method = GET)
	@GetMapping("/todos/{id}")
	public Optional<ToDo> view(@PathVariable int id) {

		Optional<ToDo> toDo = toDoListRepasitoy.findById(id);

		return toDo;
	}

	
	// todo 삭제 API (Method = DELETE)
	@DeleteMapping("/todos/{id}")
	public Response delete(@PathVariable int id) {
		
		// 처리결과 JSON 리턴용 객체
		Response response = new Response();
		
		try {
			
			// 1. 유효성 검사(처리하려는 id를 참조하는 다른 TODO가 있는지 확인)
			List<ToDo> todoList = toDoListRepasitoy.findByRefIdIsAll(id);
			
			
			// 유효성검증 실패
			if( todoList.size() > 0 ) {	// 참조하고 있는 데이터가 남아있다면
			  
				String refIds = "";
				
				for( int i=0 ; i < todoList.size() ; i++ ) {
					if( i != 0 ) refIds += ",";
					refIds += todoList.get(i).getId();
				}
				
				response.setResult(false);
				response.setMsg( todoList.size() + "건의 참조 ID가 존재하여 삭제처리를 할 수 없습니다. 참조 ID를 먼저 삭제처리하세요.\n[참조ID] : " + refIds );
				
			}else { //유효성검증 성공
			
				// 1. DB 삭제처리
				toDoListRepasitoy.deleteById(id);
				
				// 2. 처리결과 셋팅
				response.setResult(true);
				response.setMsg("삭제처리가 정상적으로 완료 되었습니다.");
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			
			response.setResult(false);
			response.setMsg("삭제 처리가 정상적으로 완료 되지 않았습니다. 관리자에게 문의하세요");
		}
		
		
		return response;
	}
	
	
	
	
	// todo 수정(Update) API (Method = PUT)
	@PutMapping("/todos/{id}")
	public Response edit(
			@PathVariable int id, 
			@Valid ToDo toDo) 
	{
		
		// 처리결과 JSON 리턴용 객체
		Response response = new Response();
		
		try {
			
			// 1. DB의 객체를 불러와 필요한 부분 수정함
			ToDo updateToDo = toDoListRepasitoy.getOne(id);
			updateToDo.setJob(toDo.getJob()); 			
			
			// 2. DB 처리
			toDoListRepasitoy.save(updateToDo);	
			
			// 3. 처리결과 셋팅
			response.setResult(true);
			response.setMsg("수정 처리가 정상적으로 완료 되었습니다.");
			
		}catch(Exception e){
			e.printStackTrace();
			
			response.setResult(false);
			response.setMsg("수정 처리가 정상적으로 완료 되지 않았습니다. 관리자에게 문의하세요");
		}
				
		return response;
		
	}
	
	
	
	// todo 완료처리 API (Method = PATCH)
	@PatchMapping("/todos/{id}")
	public Response complete(
			@PathVariable int id) 
	{
		
		// 처리결과 JSON 리턴용 객체
		Response response = new Response();
		
		try {
			
			// 1. 유효성 검사(완료처리하려는 id를 참조하는 다른 TODO가 있는지 확인)
			List<ToDo> todoList = toDoListRepasitoy.findByRefIdIsNotCompleted(id);
			
			
			// 유효성검증 실패
			if( todoList.size() > 0 ) {	// 참조하고 있는 데이터가 남아있다면
			  
				String refIds = "";
				
				for( int i=0 ; i < todoList.size() ; i++ ) {
					if( i != 0 ) refIds += ",";
					refIds += todoList.get(i).getId();
				}
				
				response.setResult(false);
				response.setMsg( todoList.size() + "건의 참조 ID가 존재하여 완료처리를 할 수 없습니다. 참조 ID를 먼저 완료처리하세요.\n[참조ID] : " + refIds );
				
			}else { //유효성검증 성공
			
				// 1. DB의 객체를 불러와 필요한 부분 수정함
				ToDo updateToDo = toDoListRepasitoy.getOne(id);
				updateToDo.setComplete( true );		// 완료처리여부 UPDATE			
				toDoListRepasitoy.save(updateToDo);
				
				// 2. 처리결과 셋팅
				response.setResult(true);
				response.setMsg("완료처리가 정상적으로 완료 되었습니다.");
				
			}
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
			response.setResult(false);
			response.setMsg( "완료처리가 정상적으로 완료 되지 않았습니다. 관리자에게 문의하세요.");
		}
		
		
		return response;
		
		
	}
	
	
}