package com.telecom.kanban;

import java.util.Collection;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.telecom.kanban.domain.Developer;
import com.telecom.kanban.domain.Task;
import com.telecom.kanban.domain.TaskStatus;
import com.telecom.kanban.repository.TaskRepository;
import com.telecom.kanban.repository.TaskStatusRepository;
import com.telecom.kanban.repository.TaskTypeRepository;
import com.telecom.kanban.service.serviceImpl.TaskServiceImpl;
import com.telecom.kanban.utils.Constants;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class TaskTest {
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskTypeRepository taskTypeRepository;
	
	@Autowired
	private TaskStatusRepository taskStatusRepository;
	
	@Autowired
	private TaskServiceImpl taskServiceImpl;
	
	@Test
	public void testAddDeveloper() {
		Developer developer = new Developer();
		Task task = new Task();
		task.addDeveloper(developer);
		Assert.assertEquals(1, task.getDevelopers().size());
	}

	@Test
	public void testFindAllTasks() {
		Collection<Task> tasks = this.taskRepository.findAll();
		Assert.assertEquals(1, tasks.size());
	}
	
	@Test
	public void testFindTask() {
		Task task = taskRepository.save(new Task());
		Long id = task.getId();
		Task taskFound = taskServiceImpl.findTask(id).orElse(null);
		Assert.assertEquals(task,taskFound);
	}
	
	@Test 
	public void testfindalltaskst() {
		Collection<TaskStatus> tasks = this.taskStatusRepository.findAll();
		Assert.assertEquals(3, tasks.size());
	}
	
	@Test
	public void testMoveRight() {
		
		TaskStatus enDev = taskStatusRepository.findById(Constants.TASK_STATUS_DEV_ID).orElse(null);
		TaskStatus finish = taskStatusRepository.findById(Constants.TASK_STATUS_FINISH_ID).orElse(null);
		
		Task task = new Task();
		task.setTaskStatus(taskStatusRepository.findById(Constants.TASK_STATUS_WAITING_ID).orElse(null));
		task = taskServiceImpl.moveRightTask(task);
		
		Assert.assertEquals(enDev, task.getTaskStatus());
		
		task = taskServiceImpl.moveRightTask(task);
		
		Assert.assertEquals(finish, task.getTaskStatus());
		
		task = taskServiceImpl.moveRightTask(task);
		
		Assert.assertEquals(finish, task.getTaskStatus());
	}
	
	@Test
	public void testMoveLeft() {
		
		TaskStatus waiting = taskStatusRepository.findById(Constants.TASK_STATUS_WAITING_ID).orElse(null);
		TaskStatus enDev = taskStatusRepository.findById(Constants.TASK_STATUS_DEV_ID).orElse(null);
		
		Task task = new Task();
		task.setTaskStatus(taskStatusRepository.findById(Constants.TASK_STATUS_FINISH_ID).orElse(null));

		task = taskServiceImpl.moveLeftTask(task);
		
		Assert.assertEquals(enDev, task.getTaskStatus());
		
		task = taskServiceImpl.moveLeftTask(task);
		
		Assert.assertEquals(waiting, task.getTaskStatus());
		
		task = taskServiceImpl.moveLeftTask(task);
		
		Assert.assertEquals(waiting, task.getTaskStatus());
	}
	
}
