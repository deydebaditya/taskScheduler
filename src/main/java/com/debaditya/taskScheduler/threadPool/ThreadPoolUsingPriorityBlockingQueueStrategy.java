package com.debaditya.taskScheduler.threadPool;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.models.tasks.RecurringTask;
import com.debaditya.taskScheduler.taskCreator.creators.Task;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
@PropertySource("classpath:application.properties")
public class ThreadPoolUsingPriorityBlockingQueueStrategy implements ThreadPool {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolUsingPriorityBlockingQueueStrategy.class);

	private final PriorityBlockingQueue<Task> queuedTasksForExecution; // All ACTIVE status tasks reside in this
	private final Map<String, Task> inactiveTasks; // All INACTIVE tasks reside in this
	private final Map<String, ScheduledFuture<Task>> activeScheduledFutures; // All ACTIVE tasks which are executing
																				// reside in this
	private final Map<String, Task> activeTasks; // All ACTIVE tasks which are executing reside in this
	private final Map<String, Task> completedTasks; // All COMPLETED tasks reside here
	private final PoolWorker daemonSchedulerThread;
	private final ScheduledExecutorService scheduler;
	private final CronDefinition cronDefinition;
	private final CronParser cronParser;
	private final TaskStatusUpdateWorker statusUpdateWorker;

	@Value("${numberOfWorkerThreads}")
	private final Integer workerThreads = 2;

	public ThreadPoolUsingPriorityBlockingQueueStrategy() {
		cronDefinition = CronDefinitionBuilder.defineCron().withMinutes().and().withHours().and().withDayOfWeek()
				.withValidRange(0, 7).withMondayDoWValue(1).withIntMapping(7, 0).and().instance();
		cronParser = new CronParser(cronDefinition);
		queuedTasksForExecution = new PriorityBlockingQueue<>();
		inactiveTasks = Maps.newConcurrentMap();
		activeScheduledFutures = Maps.newConcurrentMap();
		activeTasks = Maps.newConcurrentMap();
		completedTasks = Maps.newConcurrentMap();
		scheduler = Executors.newScheduledThreadPool(workerThreads);
		daemonSchedulerThread = new PoolWorker();
		LOGGER.info("Started Scheduler thread: {}.", daemonSchedulerThread.getId());
		daemonSchedulerThread.setName("Scheduler");
		daemonSchedulerThread.start();
		statusUpdateWorker = new TaskStatusUpdateWorker();
		LOGGER.info("Started Daemon Task Status Update Worker thread: {}", statusUpdateWorker.getId());
		statusUpdateWorker.setName("upd-worker");
		statusUpdateWorker.setDaemon(true);
		statusUpdateWorker.start();
	}

	@Override
	public void execute(Task task, String taskName, Integer taskPriority) {

		synchronized (queuedTasksForExecution) {
			LOGGER.info("Adding task {} to queue.", task.getTaskName());
			queuedTasksForExecution.add(task);
			queuedTasksForExecution.notify();
			synchronized (scheduler) {
				LOGGER.info("Notifying scheduler to start execution.");
				scheduler.notify();
			}
		}

	}

	@Override
	public void interruptTask(Task task, String taskName, TaskStatus taskStatus)
			throws InterruptedException, ExecutionException {

		synchronized (this) {
			switch (taskStatus) {
			case ACTIVE:
				if (!queuedTasksForExecution.contains(task) && inactiveTasks.containsKey(taskName)
						&& !activeScheduledFutures.containsKey(taskName)) {
					inactiveTasks.remove(taskName);
					execute(task, taskName, task.getTaskPriority().getPriorityNumeral());
				}
				break;
			case INACTIVE:
				if ((queuedTasksForExecution.contains(task) && !activeScheduledFutures.containsKey(taskName))
						&& !inactiveTasks.containsKey(taskName)) {
					queuedTasksForExecution.remove(task);
					inactiveTasks.put(taskName, task);
				} else if ((!queuedTasksForExecution.contains(task) && activeScheduledFutures.containsKey(taskName))
						&& !inactiveTasks.containsKey(taskName)) {
					ScheduledFuture<Task> activeTask = activeScheduledFutures.remove(taskName);
					activeTask.cancel(true);
					inactiveTasks.put(taskName, activeTasks.remove(taskName));
				}
				break;
			case COMPLETED:
			}
			this.notify();
		}

	}

	@Override
	public List<Task> getActiveTasks() {
		List<Task> tasks = Lists.newArrayList();
		synchronized (queuedTasksForExecution) {
			tasks.addAll(queuedTasksForExecution);
		}
		synchronized (activeTasks) {
			activeTasks.entrySet().stream().forEach(task -> {
				tasks.add(task.getValue());
			});
		}
		return tasks;
	}

	@Override
	public List<Task> getInactiveTasks() {
		List<Task> tasks = Lists.newArrayList();
		synchronized (inactiveTasks) {
			inactiveTasks.entrySet().stream().forEach(task -> {
				tasks.add(task.getValue());
			});
		}
		return tasks;
	}
	
	@Override
	public List<Task> getCompletedTasks() {
		List<Task> tasks = Lists.newArrayList();
		synchronized(completedTasks) {
			completedTasks.entrySet().stream().forEach(task -> {
				tasks.add(task.getValue());
			});
		}
		return tasks;
	}

	private class TaskStatusUpdateWorker extends Thread {

		@Override
		public void run() {

			while (true) {
				activeScheduledFutures.forEach((futureName, future) -> {
					try {
						LOGGER.info("Update worker running. Checking for task completion.");
						Task task = future.get();
						if (task.getTaskStatus() == TaskStatus.COMPLETED) {
							LOGGER.info("Task {} has been completed.", task.getTaskName());
							completedTasks.put(task.getTaskName(), task);
							activeScheduledFutures.remove(futureName);
							activeTasks.remove(futureName);
						}
					} catch (InterruptedException e) {
						LOGGER.error("Task {} got interrupted.", futureName);
						LOGGER.error("Exception: {}", e.getMessage());
					} catch (ExecutionException e) {
						LOGGER.error("Task {} failed to execute.", futureName);
						LOGGER.error("Exception: {}", e.getMessage());
					}
				});
			}

		}

	}

	private class PoolWorker extends Thread {

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			Task task;

			while (true) {
				synchronized (queuedTasksForExecution) {
					while (queuedTasksForExecution.isEmpty()) {
						try {
							LOGGER.info("No pending tasks to execute. Waiting for task.");
							queuedTasksForExecution.wait();
						} catch (InterruptedException e) {
							LOGGER.error("An error occurred while executor queue is waiting: {}", e.getMessage());
							throw new RuntimeException(
									"Caught InterruptedException while waiting on executor queue: " + e.getMessage());
						}
					}
					task = queuedTasksForExecution.poll();
				}

				ScheduledFuture<Task> future = null;
				switch (task.getTaskType()) {
				case A:
					future = scheduler.schedule((Callable<Task>) task,
							task.getTaskStartTime().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
					LOGGER.info("Scheduled stand alone task {} for executing.", task.getTaskName());
					synchronized (activeScheduledFutures) {
						activeScheduledFutures.put(task.getTaskName(), future);
						activeTasks.put(task.getTaskName(), task);
					}
					break;
				case B:
					RecurringTask recurringTask = (RecurringTask) task;
					String schedule = recurringTask.getTaskSchedule();
					Cron cron = cronParser.parse(schedule);
					ZonedDateTime nextExecution = null;
					Long initialDelay = null;
					Long periodOfExecution = null;
					ZonedDateTime now = ZonedDateTime.now();
					Optional<ZonedDateTime> executionTime = ExecutionTime.forCron(cron).nextExecution(now);
					if (executionTime.isPresent()) {
						nextExecution = executionTime.get();
						initialDelay = nextExecution.toEpochSecond() - now.toEpochSecond();
						periodOfExecution = ExecutionTime.forCron(cron).nextExecution(nextExecution).get()
								.toEpochSecond() - nextExecution.toEpochSecond();
					} else {
						LOGGER.error("No valid execution schedule found. Deferring schedule of task {}.",
								task.getTaskName());
						synchronized (inactiveTasks) {
							recurringTask.setTaskStatus(TaskStatus.INACTIVE);
							inactiveTasks.put(task.getTaskName(), task);
						}
						break;
					}
					future = (ScheduledFuture<Task>) scheduler.scheduleAtFixedRate(task, initialDelay,
							periodOfExecution, TimeUnit.SECONDS);
					LOGGER.info("Scheduled recurring task {} for executing.", task.getTaskName());
					synchronized (activeScheduledFutures) {
						activeScheduledFutures.put(task.getTaskName(), future);
						activeTasks.put(task.getTaskName(), task);
					}
					break;
				}
			}

		}

	}

}
