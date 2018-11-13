package com.hwasalko.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hwasalko.app.entity.ToDo;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 * 
 * JpaRepository 상속 인터페이스
 */

public interface ToDoListDao extends JpaRepository <ToDo, Integer> {

}