package com.debaditya.taskScheduler.threadPool;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.taskCreator.creators.Task;

public interface ThreadPool {

	public void execute(Task task, String taskName, Integer taskPriority);
	public void interruptTask(Task task, String taskName, TaskStatus taskStatus) throws InterruptedException, ExecutionException;
	public List<Task> getActiveTasks();
	public List<Task> getInactiveTasks();
	public List<Task> getCompletedTasks();
	
}
