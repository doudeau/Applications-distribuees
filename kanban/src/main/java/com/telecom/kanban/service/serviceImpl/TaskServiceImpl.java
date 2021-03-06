package com.telecom.kanban.service.serviceImpl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.kanban.LoadData;
import com.telecom.kanban.domain.Task;
import com.telecom.kanban.domain.TaskStatus;
import com.telecom.kanban.repository.TaskRepository;
import com.telecom.kanban.repository.TaskStatusRepository;
import com.telecom.kanban.repository.TaskTypeRepository;
import com.telecom.kanban.service.TaskService;
import com.telecom.kanban.utils.Constants;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskStatusRepository taskStatusRepository;
	
	@Autowired
	private TaskTypeRepository taskTypeRepository;

	@Override
	public Collection<Task> findAllTasks() {
		return this.taskRepository.findAll();
	}

	@Override
	public Optional<Task> findTask(Long id) {
		return this.taskRepository.findById(id);
	}

	@Override
	public Task moveRightTask(Task task) {
		
		Long statusId = task.getTaskStatus().getId();
		
		if(statusId.equals(Constants.TASK_STATUS_WAITING_ID)) {
			task.setTaskStatus(taskStatusRepository.findById(Constants.TASK_STATUS_DEV_ID).orElse(null));
			
		}else if (statusId.equals(Constants.TASK_STATUS_DEV_ID)) {
			task.setTaskStatus(taskStatusRepository.findById(Constants.TASK_STATUS_FINISH_ID).orElse(null));
		}
		return this.taskRepository.save(task);
	}

	@Override
	public Task moveLeftTask(Task task) {
		
		Long statusId = task.getTaskStatus().getId();
		
		if(statusId.equals(Constants.TASK_STATUS_DEV_ID)) {
			task.setTaskStatus(taskStatusRepository.findById(Constants.TASK_STATUS_WAITING_ID).orElse(null));
		
		}else if (statusId.equals(Constants.TASK_STATUS_FINISH_ID)) {
			task.setTaskStatus(taskStatusRepository.findById(Constants.TASK_STATUS_DEV_ID).orElse(null));
		}
		return this.taskRepository.save(task);
	}

}
