package com.craftwork.asia.taskScheduler.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.craftwork.asia.taskScheduler.AppEnums.Status;
import com.craftwork.asia.taskScheduler.entity.TaskEntity;

public interface TaskService {
	
	public ResponseEntity<TaskEntity> saveTask(TaskEntity task);
	
	public List<TaskEntity> fetchAllTask();
	
	public ResponseEntity<TaskEntity> fetchTask(int id);
	
	public ResponseEntity<TaskEntity> deleteTask(int id);
	
	public ResponseEntity<TaskEntity> updateTask(TaskEntity task);
	
	public ResponseEntity<TaskEntity> updateTaskStatus(int id, int status);

	ResponseEntity<TaskEntity> updateTaskPriority(int id, int status);

}
