package fi.haagahelia.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.course.domain.Category;
import fi.haagahelia.course.domain.CategoryRepository;
import fi.haagahelia.course.domain.Task;
import fi.haagahelia.course.domain.TaskRepository;
import fi.haagahelia.course.domain.User;
import fi.haagahelia.course.domain.UserRepository;

@SpringBootApplication
public class TaskOrganizerApplication {
	private static final Logger log = LoggerFactory.getLogger(TaskOrganizerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TaskOrganizerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner taskDemo(TaskRepository trepository, CategoryRepository crepository, UserRepository urepository) {
		return (args) -> {
			log.info("save a couple of tasks");
			crepository.save(new Category("Frontend"));
			crepository.save(new Category("Backend"));
			crepository.save(new Category("Softala"));
			
			trepository.save(new Task("Final project", "Finish final project", "5.5.22", crepository.findByName("Backend").get(0)));
			trepository.save(new Task("Complete documentation", "Write necessary documentation", "4.5.22", crepository.findByName("Softala").get(0)));	
			
			// Create users: admin/admin user/user
			// User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			// User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			// urepository.save(user1);
			// urepository.save(user2);
			
			log.info("fetch all tasks");
			for (Task task : trepository.findAll()) {
				log.info(task.toString());
			}

		};
	}
}
