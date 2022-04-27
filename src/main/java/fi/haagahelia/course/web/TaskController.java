package fi.haagahelia.course.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.course.domain.CategoryRepository;
import fi.haagahelia.course.domain.Task;
import fi.haagahelia.course.domain.TaskRepository;

@Controller
public class TaskController {
	@Autowired
	private TaskRepository repository; 

	@Autowired
	private CategoryRepository crepository; 
	
	// Show all tasks
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
    }	
	
	// Show all tasks
    @RequestMapping(value="/tasklist")
    public String taskList(Model model) {	
        model.addAttribute("tasks", repository.findAll());
        return "tasklist";
    }
  
	// RESTful service to get all tasks
    @RequestMapping(value="/tasks")
    public @ResponseBody List<Task> taskListRest() {	
        return (List<Task>) repository.findAll();
    }    

	// RESTful service to get task by id
    @RequestMapping(value="/task/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Task> findTaskRest(@PathVariable("id") Long taskId) {	
    	return repository.findById(taskId);
    }       
    
    // Add new task
    @RequestMapping(value = "/add")
    public String addTask(Model model){
    	model.addAttribute("task", new Task());
    	model.addAttribute("categories", crepository.findAll());
        return "addtask";
    }     
    
    // Save new task
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Task task){
        repository.save(task);
        return "redirect:tasklist";
    }    

    // Delete task
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteTask(@PathVariable("id") Long taskId, Model model) {
    	repository.deleteById(taskId);
        return "redirect:../tasklist";
    }     
}
