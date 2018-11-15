package com.hwasalko.app.controller;

import java.util.Optional;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
			@Valid ToDo toDo, 
			BindingResult bindingResult) 
	{

		if (bindingResult.hasErrors()) {
			log.error("########## 에러발생 ######### : " + bindingResult.toString());
	    }
		
		toDo.setRegDate( LocalDateTime.now() );			// 현재시간 주입
		toDo.setFianlUpdateDate( LocalDateTime.now() );	// 현재시간 주입
		toDo.setComplete(false);								// 완료여부 false 주입
		
		ToDo toDoData = toDoListRepasitoy.save(toDo);	// DB insert

		return toDoData;
	}

	// todo 조회(select) API (method = GET)
	// 페이징 처리를 위해 Pagination 정보를 포함하여 리턴
	@GetMapping("todo")
	public Page<ToDo> list(
			Model model, 
			@PageableDefault(sort = { "id" }, direction = Direction.ASC, size=5) Pageable pageable)	// 기본 페이징 정보 
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
	
	
	// todo 전체삭제(DeleteAll) API (Method = DELETE)
	@DeleteMapping("/todo")
	public boolean deleteAll() {
		toDoListRepasitoy.deleteAll();
		return true;
	}
	
	
	// todo 수정(Update) API (Method = PUT)
	@PutMapping("/todo/{id}")
	public ToDo edit(
			@PathVariable int id, 
			@Valid ToDo toDo, 
			BindingResult bindingResult) 
	{

		if (bindingResult.hasErrors()) {
			log.error("########## 에러발생 ######### : " + bindingResult.toString());
	    }
		
		// DB의 객체를 불러와 필요한 부분만 수정함
		ToDo updateToDo = toDoListRepasitoy.getOne(id);
		
		updateToDo.setJob(toDo.getJob()); 						// 할일
		updateToDo.setComplete(toDo.isComplete()); 				// 완료여부
		updateToDo.setFianlUpdateDate( LocalDateTime.now() );	// 최종수정시간
		
		ToDo toDoData = toDoListRepasitoy.save(updateToDo);	// DB Update

		return toDoData;
	}
	
}