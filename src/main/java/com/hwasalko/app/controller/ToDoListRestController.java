package com.hwasalko.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwasalko.app.entity.ToDo;

import lombok.extern.slf4j.Slf4j;

import com.hwasalko.app.dao.ToDoListDao;

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
	private ToDoListDao toDoListDao;

	
	// Insert API
	@RequestMapping("/add")
	public ToDo add(@Valid ToDo toDo, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
	        //return "form";
			log.error("########## 에러발생 ######### : " + bindingResult.toString());
	    }
		
		toDo.setRegDate(new Date());			// 현재시간 주입
		toDo.setFianlUpdateDate(new Date());	// 현재시간 주입
		
		ToDo toDoData = toDoListDao.save(toDo);	// DB insert

		return toDoData;
	}

	// Select list API
	@RequestMapping("/list")
	public List<ToDo> list(Model model) {

		List<ToDo> toDoList = toDoListDao.findAll();

		return toDoList;
	}
	
	// view API
	@RequestMapping("/list/{id}")
	public Optional<ToDo> view(@PathVariable int id) {

		Optional<ToDo> toDo = toDoListDao.findById(id);

		return toDo;
	}

	
	// delete API
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable int id) {
		toDoListDao.deleteById(id);
		return true;
	}
	
	
	// delete All API
	@RequestMapping("/deleteAll")
	public boolean deleteAll() {
		toDoListDao.deleteAll();
		return true;
	}
	
	
	// Update API
	@RequestMapping("/edit/{id}")
	public ToDo edit(@PathVariable int id, @Valid ToDo toDo, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
	        //return "form";
			log.error("########## 에러발생 ######### : " + bindingResult.toString());
	    }
		
		toDo.setFianlUpdateDate(new Date());	// 현재시간 주입
		
		ToDo toDoData = toDoListDao.save(toDo);	// DB Update

		return toDoData;
	}
	
}