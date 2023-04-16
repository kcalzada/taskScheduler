package com.craftwork.asia.taskScheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.craftwork.asia.taskScheduler.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer>{
	
	public List<TaskEntity> findAllByOrderByCreatedAtDesc();
	
	public TaskEntity findTopByOrderByIdDesc();
}