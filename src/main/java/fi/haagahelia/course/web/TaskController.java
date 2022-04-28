package fi.haagahelia.course.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.course.domain.CategoryRepository;
import fi.haagahelia.course.domain.Task;
import fi.haagahelia.course.domain.TaskRepository;
import fi.haagahelia.course.domain.User;
import fi.haagahelia.course.domain.UserRepository;

@Controller
public class TaskController {
	@Autowired
	private TaskRepository repository; 

	@Autowired
	private CategoryRepository crepository; 

    @Autowired
	private UserRepository urepository;
	
	// Show all tasks
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
    }

    // Show all tasks of a user
	@RequestMapping(value={"/", "/tasklist"})
	public String userTasksList(Model model) {
		
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        User userNow = urepository.findByUsername(username);
		System.out.println("USER FIND START");
		System.out.println(user);
		System.out.println("USER FIND END");
		model.addAttribute("tasks", repository.findByUser(userNow));
		return "tasklist";
	}
	
	// Show all tasks
    // @RequestMapping(value="/tasklist")
    // public String taskList(Model model) {	
    //     model.addAttribute("tasks", repository.findAll());
    //     return "tasklist";
    // }
  
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
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        User userNow = urepository.findByUsername(username);
        task.setUser(userNow);
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

    // Set a task as complete
    @RequestMapping(value = "/complete/{id}", method = RequestMethod.GET)
    public String completeTask(@PathVariable("id") Long taskId, Model model) {
        Optional<Task> t = repository.findById(taskId);
    	t.get().setCompleted(true);
        repository.save(t.get());
        return "redirect:../tasklist";
    }

    // Set a task as NOT complete
    @RequestMapping(value = "/uncomplete/{id}", method = RequestMethod.GET)
    public String uncompleteTask(@PathVariable("id") Long taskId, Model model) {
        Optional<Task> t = repository.findById(taskId);
        t.get().setCompleted(false);
        repository.save(t.get());
        return "redirect:../tasklist";
    }
}
