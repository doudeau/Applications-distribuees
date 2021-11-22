package com.telecom.kanban.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TaskType {
	@Id
	@GeneratedValue
	private Long id;
	
	private String label;

	public TaskType(String label) {
		this.label = label;
	}
}
