package com.javarush.services;

import com.javarush.dao.TaskDAO;
import com.javarush.domain.Status;
import com.javarush.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskDAO taskRepository;

    public TaskService(TaskDAO taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getAll(int offset, int count){
        return taskRepository.getAllTasks(offset, count);
    }

    public int getAllCount(){
        return taskRepository.getAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status){
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (!optionalTask.isPresent())
            throw new RuntimeException("ERROR couldn't find task with id - " + id);

        Task task = optionalTask.get();

        task.setDescription(description);
        task.setStatus(status);

        taskRepository.saveOrUpdateTask(task);

        return task;
    }

    public Task create(String description, Status status){
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskRepository.saveOrUpdateTask(task);
        return task;
    }

    @Transactional
    public void delete(int id){
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (!optionalTask.isPresent())
            throw new RuntimeException("ERROR couldn't find task with id - " + id);

        Task task = optionalTask.get();

        taskRepository.deleteTask(task);
    }
}
