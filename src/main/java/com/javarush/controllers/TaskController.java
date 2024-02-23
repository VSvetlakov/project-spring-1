package com.javarush.controllers;

import com.javarush.domain.Task;
import com.javarush.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String showTasks(Model model,
                            @RequestParam(value = "count",required = false,defaultValue = "10") int count,
                            @RequestParam(value = "page",required = false,defaultValue = "1") int page){
        List<Task> tasks = taskService.getAll((page-1)*count,count);

        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount()/count);

        model.addAttribute("tasks",tasks);
        model.addAttribute("current_page",page);
        if (totalPages > 1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers",pageNumbers);
        }

        return "tasks";
    }

    @PostMapping("/{id}")
    public void editTask(Model model,
                           @PathVariable(name = "id") int id,
                           @RequestBody TaskInfo taskInfo){
        if (isNull(id) || id <= 0)
            throw new RuntimeException("Invalid id");

        taskService.edit(id, taskInfo.getDescription(), taskInfo.getStatus());
    }

    @PostMapping("/")
    public void addTask(Model model,
                           @RequestBody TaskInfo taskInfo){

        taskService.create(taskInfo.getDescription(), taskInfo.getStatus());
    }

    @DeleteMapping("/{id}")
    public void deleteTask(Model model,
                             @PathVariable(name = "id") int id) {

        if (isNull(id) || id <= 0)
            throw new RuntimeException("Invalid id");

        taskService.delete(id);
    }


}
