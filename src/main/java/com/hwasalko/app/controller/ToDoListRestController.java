package com.hwasalko.app.controller;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public boolean delete(@PathVariable int id) {
		toDoListRepasitoy.deleteById(id);
		return true;
	}
	
	
	
	
	// todo 수정(Update) API (Method = PUT)
	@PutMapping("/todos/{id}")
	public ToDo edit(
			@PathVariable int id, 
			@Valid ToDo toDo) 
	{
		
		// 1. DB의 객체를 불러와 필요한 부분 수정함
		ToDo updateToDo = toDoListRepasitoy.getOne(id);
		updateToDo.setJob(toDo.getJob()); 			
		
		return toDoListRepasitoy.save(updateToDo);	
		
	}
	
}