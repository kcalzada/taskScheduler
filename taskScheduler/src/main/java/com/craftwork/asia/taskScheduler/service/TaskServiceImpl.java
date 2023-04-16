package com.craftwork.asia.taskScheduler.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.craftwork.asia.taskScheduler.AppEnums.Priority;
import com.craftwork.asia.taskScheduler.AppEnums.Status;
import com.craftwork.asia.taskScheduler.entity.TaskEntity;
import com.craftwork.asia.taskScheduler.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	TaskRepository taskRepository;

	@Override
	public ResponseEntity<TaskEntity> saveTask(TaskEntity task) {
		
		if(CreateAtandDueDateFieldValidation(task)) {	
			
			return new ResponseEntity<TaskEntity>(HttpStatus.BAD_REQUEST);
		}
		
		if(NullChecking(task)) {
			return new ResponseEntity<TaskEntity>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<TaskEntity>(taskRepository.save(task), HttpStatus.OK);
	}

	@Override
	public List<TaskEntity> fetchAllTask() {
		
		return taskRepository.findAllByOrderByCreatedAtDesc();
	}

	@Override
	public ResponseEntity<TaskEntity> fetchTask(int id) {
		
		Optional<TaskEntity> task = taskRepository.findById(id);
		
		if(task.isEmpty()) {
			return new ResponseEntity<TaskEntity>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TaskEntity>(task.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TaskEntity> deleteTask(int id) {
		
		ResponseEntity<TaskEntity> task = fetchTask(id);
		
		if(HttpStatus.NOT_FOUND.equals(task.getStatusCode())) {
			return task;
		}
		
		taskRepository.deleteById(id);
		
		return task;
	}

	@Override
	public ResponseEntity<TaskEntity> updateTask(TaskEntity task) {
		
		ResponseEntity<TaskEntity> tempTask = fetchTask(task.getId());
		
		if(HttpStatus.NOT_FOUND.equals(tempTask.getStatusCode())) {
			return tempTask;
		}
		
		return saveTask(task);
	}
	
	@Override
	public ResponseEntity<TaskEntity> updateTaskStatus(int id, int status) {
		
		ResponseEntity<TaskEntity> tempTask = fetchTask(id);
		
		Status st = getStatus(status);
		
		if(HttpStatus.NOT_FOUND.equals(tempTask.getStatusCode())) {
			return tempTask;
		}
		
		if(Status.Error.equals(st)) {
			return new ResponseEntity<TaskEntity>(HttpStatus.BAD_REQUEST);
		}
		
		if(tempTask.getBody().getStatus() == getStatus(status).getStatus()) {
			return new ResponseEntity<TaskEntity>(HttpStatus.CONFLICT);
		}
		
		if(Status.Done.equals(st)) {
			tempTask.getBody().setResolvedAt(getCurrentDate());
		}
		
		tempTask.getBody().setStatus(st.getStatus());
		
		saveTask(tempTask.getBody());
		
		return tempTask;
	}
	
	@Override
	public ResponseEntity<TaskEntity> updateTaskPriority(int id, int prio) {
		
		ResponseEntity<TaskEntity> tempTask = fetchTask(id);
		
		if(HttpStatus.NOT_FOUND.equals(tempTask.getStatusCode())) {
			return tempTask;
		}
		
		Priority priority = getPriority(prio);
		
		if(Priority.Error.equals(priority)) {
			return new ResponseEntity<TaskEntity>(HttpStatus.BAD_REQUEST);
		}
		
		if(tempTask.getBody().getPriority() == getPriority(prio).getPriority()) {
			return new ResponseEntity<TaskEntity>(HttpStatus.CONFLICT);
		}
		
		tempTask.getBody().setPriority(priority.getPriority());
		
		saveTask(tempTask.getBody());
		
		return tempTask;
	}
	
	@Scheduled(fixedDelay = 23000)
	private void taskSchedule() {
		
		TaskEntity taskEntity = new TaskEntity();
		
		Date now = getDueDate();
		
		String title = "Title from "+now.toString();
		String description = "description from "+now.toString();
		
		taskEntity.setDueDate(now);
		taskEntity.setTitle(title);
		taskEntity.setDescription(description);
		
		saveTask(taskEntity);
		
		System.out.println(getCurrentDate());
	}
	
	private Date getDueDate() {
		LocalDateTime now = LocalDateTime.now().plusDays(3);
		
		Date dueDate = Date.from(now.plusDays(3).atZone(ZoneId.systemDefault()).toInstant());
		
		return dueDate;
	}
	
	private Date getCurrentDate() {
		LocalDateTime now = LocalDateTime.now().plusDays(3);
		
		Date dueDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		
		return dueDate;
	}
	
	private boolean CreateAtandDueDateFieldValidation(TaskEntity task) {
		if(task.getCreatedAt() != null) {			
			return true;
		}
		
		if(task.getUpdatedAt() != null) {
			return true;
		}
		
		return false;
	}
	
	private boolean NullChecking(TaskEntity task) {
		if(task.getTitle() == null) {
			return true;
		}
		
		if(task.getDescription() == null) {
			return true;
		}
		
		return false;
	}
	
	public Status getStatus(int status) {
		
		if(status == 2) {
			return Status.Done;
		}
		
		if(status == 1) {
			return Status.In_Progress;
		}
		
		if(status == 0) {
			return Status.Not_Started;
		}
		
		return Status.Error;
	}
	
	public Priority getPriority(int priority) {
		
		if(priority == 2) {
			return Priority.High;
		}
		
		if(priority == 1) {
			return Priority.Medium;
		}
		
		if(priority == 0) {
			return Priority.Not_Important;
		}
		
		return Priority.Error;
	}
	
}
