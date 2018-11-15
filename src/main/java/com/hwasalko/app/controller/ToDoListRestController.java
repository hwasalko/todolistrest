package com.hwasalko.app.controller;

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

import lombok.extern.slf4j.Slf4j;

import com.hwasalko.app.dao.ToDoListRepasitoy;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 * 
 * ToDo 리스트 REST API 컨트롤러
 */
@Slf4j
@RestController
public class ToDoListRestController {

	@Autowired
	private ToDoListRepasitoy toDoListRepasitoy;

	
	// todo 등록(insert) API (methoed = POST)
	@PostMapping("/todo")
	public ToDo add(
			@Valid ToDo toDo) 
	{
		
		ToDo toDoData = toDoListRepasitoy.save(toDo);	// DB insert

		return toDoData;
	}

	// todo 조회(select) API (method = GET)
	// 페이징 처리를 위해 Pagination 정보를 포함하여 리턴
	@GetMapping("todo")
	public Page<ToDo> list(
				@PageableDefault(sort = { "id" }, direction = Direction.ASC, size=5) Pageable pageable // 기본 페이징 정보 
			)	
	{	

		log.debug("[pageable] " + pageable.toString() );
		Page<ToDo> toDoList = toDoListRepasitoy.findAll(pageable);

		return toDoList;
	}
	
	// todo 상세조회(select) API (method = GET)
	@GetMapping("/todo/{id}")
	public Optional<ToDo> view(@PathVariable int id) {

		Optional<ToDo> toDo = toDoListRepasitoy.findById(id);

		return toDo;
	}

	
	// todo 삭제 API (Method = DELETE)
	@DeleteMapping("/todo/{id}")
	public boolean delete(@PathVariable int id) {
		toDoListRepasitoy.deleteById(id);
		return true;
	}
	
	
	
	
	// todo 수정(Update) API (Method = PUT)
	@PutMapping("/todo/{id}")
	public ToDo edit(
			@PathVariable int id, 
			@Valid ToDo toDo) 
	{
		
		// DB의 객체를 불러와 필요한 부분 수정함
		ToDo updateToDo = toDoListRepasitoy.getOne(id);
		
		updateToDo.setJob(toDo.getJob()); 						// 할일
		
		ToDo toDoData = toDoListRepasitoy.save(updateToDo);	// DB Update

		return toDoData;
	}
	
}