package com.debaditya.taskScheduler.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.debaditya.taskScheduler.models.taskMeta.TaskPriority;
import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.models.taskMeta.TaskType;

@Entity
public class TaskStatusModel {

	@Id
	@Column(name = "TASK_ID", columnDefinition = "varchar(100)", unique = true, nullable = false)
	private String taskId;
	
	@Column(name = "TASK_NAME", columnDefinition = "varchar(500)", unique = false, nullable = true)
	private String taskName;
	
	@Column(name = "TASK_DURATION", columnDefinition = "integer", unique = false, nullable = false)
	private Long taskDuration;
	
	@Column(name = "TASK_TYPE", columnDefinition = "varchar(1)", unique = false, nullable = false)
	private TaskType taskType;
	
	@Column(name = "TASK_STATUS", columnDefinition = "varchar(50)", unique = false, nullable = false)
	private TaskStatus taskStatus;
	
	@Column(name = "TASK_PRIORITY", columnDefinition = "varchar(50)", unique = false, nullable = false)
	private TaskPriority taskPriority;
	
	@Column(name = "TASK_ARRIVAL_TIME", columnDefinition = "timestamp", unique = false, nullable = false)
	private Date taskArrivalTime;
	
	@Column(name = "TASK_START_TIME", columnDefinition = "timestamp", unique = false, nullable = true)
	private Date taskStartTime;
	
	@Column(name = "TASK_END_TIME", columnDefinition = "timestamp", unique = false, nullable = true)
	private Date taskEndTime;
	
	@Column(name = "TASK_NUMBER_OF_PREEMPTIONS", columnDefinition = "integer", unique = false, nullable = true)
	private Integer taskNumberOfPreemptions;
	
}
