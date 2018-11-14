package com.hwasalko.app.controller;

import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
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

	
	// Insert API
	@RequestMapping("/add")
	public ToDo add(@Valid ToDo toDo, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.error("########## 에러발생 ######### : " + bindingResult.toString());
	    }
		
		toDo.setRegDate( LocalDateTime.now() );			// 현재시간 주입
		toDo.setFianlUpdateDate( LocalDateTime.now() );	// 현재시간 주입
		toDo.setComplete(false);						// 완료여부 false 주입
		
		ToDo toDoData = toDoListRepasitoy.save(toDo);	// DB insert

		return toDoData;
	}

	// Select list API
	@RequestMapping("/list")
	public Page<ToDo> list(
			Model model, 
			@PageableDefault(sort = { "id" }, direction = Direction.DESC, size=5) Pageable pageable)	// 기본 페이지 정보 
	{	

		log.info("[pageable] " + pageable.toString() );
		Page<ToDo> toDoList = toDoListRepasitoy.findAll(pageable);

		return toDoList;
	}
	
	// view API
	@RequestMapping("/list/{id}")
	public Optional<ToDo> view(@PathVariable int id) {

		Optional<ToDo> toDo = toDoListRepasitoy.findById(id);

		return toDo;
	}

	
	// delete API
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable int id) {
		toDoListRepasitoy.deleteById(id);
		return true;
	}
	
	
	// delete All API
	@RequestMapping("/deleteAll")
	public boolean deleteAll() {
		toDoListRepasitoy.deleteAll();
		return true;
	}
	
	
	// Update API
	@RequestMapping("/edit/{id}")
	public ToDo edit(@PathVariable int id, @Valid ToDo toDo, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
	        //return "form";
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