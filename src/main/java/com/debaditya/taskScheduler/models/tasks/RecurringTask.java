package com.debaditya.taskScheduler.models.tasks;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debaditya.taskScheduler.models.taskMeta.TaskPriority;
import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.models.taskMeta.TaskType;
import com.debaditya.taskScheduler.taskCreator.creators.Task;

public class RecurringTask extends Task {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecurringTask.class);

	private String taskName;
	private Long taskDuration;
	private String taskSchedule;
	private TaskPriority taskPriority;
	private TaskStatus taskStatus;
	private Date initialTaskStartTime;
	private Long numberOfTaskRuns = 0L;
	private Map<Date, Long> executions = new LinkedHashMap<>();
	private TaskType taskType = TaskType.B;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getTaskDuration() {
		return taskDuration;
	}
	public void setTaskDuration(Long taskDuration) {
		this.taskDuration = taskDuration;
	}
	public String getTaskSchedule() {
		return taskSchedule;
	}
	public void setTaskSchedule(String taskSchedule) {
		this.taskSchedule = taskSchedule;
	}
	public TaskPriority getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
	}
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}
	public Date getTaskStartTime() {
		return null;
	}
	public TaskType getTaskType() {
		return taskType;
	}
	public Date getInitialTaskStartTime() {
		return initialTaskStartTime;
	}
	public Long getNumberOfTaskRuns() {
		return new Long(numberOfTaskRuns); // always return a new copy
	}
	public Map<Date, Long> getExecutions() {
		return new LinkedHashMap<Date, Long>(executions); // always return a new copy
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskDuration == null) ? 0 : taskDuration.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + ((taskPriority == null) ? 0 : taskPriority.hashCode());
		result = prime * result + ((taskSchedule == null) ? 0 : taskSchedule.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecurringTask other = (RecurringTask) obj;
		if (taskDuration == null) {
			if (other.taskDuration != null)
				return false;
		} else if (!taskDuration.equals(other.taskDuration))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (taskPriority != other.taskPriority)
			return false;
		if (taskSchedule == null) {
			if (other.taskSchedule != null)
				return false;
		} else if (!taskSchedule.equals(other.taskSchedule))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RecurringTask [taskName=" + taskName + ", taskDuration=" + taskDuration + ", taskSchedule="
				+ taskSchedule + ", taskPriority=" + taskPriority + "]";
	}
	
	@Override
	public Task call() throws Exception {
		Thread.currentThread().setName(this.taskName);
		Date now = new Date(System.currentTimeMillis());
		try {
			if(this.numberOfTaskRuns == 0L) {
				initialTaskStartTime = new Date(System.currentTimeMillis());
			}
			numberOfTaskRuns++;
			executions.put(now, numberOfTaskRuns);
			this.taskStatus = TaskStatus.ACTIVE;
			Thread.sleep(taskDuration * 1000);
			this.taskStatus = TaskStatus.INACTIVE;
			return this;
		} catch (InterruptedException e) {
			numberOfTaskRuns--; // If task fails, it'd be an unsuccessful execution
			executions.put(now, numberOfTaskRuns);
			this.taskStatus = TaskStatus.INACTIVE;
			throw new RuntimeException("Exception occured while running task " + taskName + ". Exception: " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
		Thread.currentThread().setName(this.taskName);
		Thread.currentThread().setPriority(this.taskPriority.getPriorityNumeral());
		Date now = new Date(System.currentTimeMillis());
		try {
			if(this.numberOfTaskRuns == 0L) {
				initialTaskStartTime = new Date(System.currentTimeMillis());
			}
			numberOfTaskRuns++;
			executions.put(now, numberOfTaskRuns);
			this.taskStatus = TaskStatus.ACTIVE;
			LOGGER.info("Executing recurring task {} for duration {}.", taskName, taskDuration);
			Thread.sleep(taskDuration * 1000);
			LOGGER.info("Completed execution of recurring task {} for duration {}.", taskName, taskDuration);
			this.taskStatus = TaskStatus.INACTIVE;
		} catch (Exception e) {
			numberOfTaskRuns--; // If task fails, it'd be an unsuccessful execution
			executions.put(now, numberOfTaskRuns);
			this.taskStatus = TaskStatus.INACTIVE;
			LOGGER.info("Exception occured while running task {}. Exception: {}", taskName, e.getMessage());
		}
	}
	
	@Override
	public int compareTo(Task task) {
		return this.taskPriority.getPriorityNumeral().compareTo(task.getTaskPriority().getPriorityNumeral());
	}
	
}
