package com.craftwork.asia.taskScheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.craftwork.asia.taskScheduler.entity.TaskEntity;
import com.craftwork.asia.taskScheduler.service.TaskService;

@RestController
@RequestMapping("tasks")
public class TaskSchedulerController {
	
	@Autowired
	TaskService taskService;
	
	@GetMapping(value="/")
	public List<TaskEntity> fetchAllTasks() {
		
		return taskService.fetchAllTask();
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<TaskEntity> fetchbyId(@PathVariable int id) {
		
		return taskService.fetchTask(id);
	}
	
	@PostMapping(value="/save")
	public ResponseEntity<TaskEntity> saveTask(@RequestBody TaskEntity task) {
		
		return taskService.saveTask(task);
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<TaskEntity> updateTask(@RequestBody TaskEntity tempTask) {
		
		return taskService.updateTask(tempTask);
	}
	
	@PutMapping(value="/update/status/{id}")
	public ResponseEntity<TaskEntity> updateTaskStatus(@PathVariable int id, @RequestParam int status) {

		return taskService.updateTaskStatus(id,status);
	}
	
	@PutMapping(value="/update/priority/{id}")
	public ResponseEntity<TaskEntity> updateTaskPriority(@PathVariable int id, @RequestParam int priority) {

		return taskService.updateTaskPriority(id,priority);
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<TaskEntity> deleteTask(@PathVariable int id) {
		
		return taskService.deleteTask(id);
	}
}
