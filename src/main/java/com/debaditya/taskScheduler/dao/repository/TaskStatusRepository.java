package com.debaditya.taskScheduler.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.debaditya.taskScheduler.models.TaskStatusModel;

@Repository
public interface TaskStatusRepository extends CrudRepository<TaskStatusModel, String> {

}
