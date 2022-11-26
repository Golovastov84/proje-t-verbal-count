package main;

import main.model.User;
import main.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> ListUser() {
        Iterable<User> userIterable = userRepository.findAll();

        ArrayList<User> users = new ArrayList<>();
        for (User user : userIterable) {
            users.add(user);
        }
        return users;
    }

    @PostMapping("/users")
    public int addUser(User user) {
        if (userRepository.count() == 0) {
            user.setId(1);
        }
//        User newUser = userRepository.save(putDeadline(user));
        User newUser = userRepository.save(user);

        return newUser.getId();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> dellUser(@PathVariable int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(userRepository.count(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> putUserId(User newUser, @PathVariable int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        User modifiedUser = putDeadline(newUser);
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity dellAllUsers() {
        if (userRepository.count() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The list is already empty.");
        }
        userRepository.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

//    public static Task putDeadline(Task task) {
//        task.setDeadline(task.getYearTask(), task.getMonthTask(), task.getDayTask());
//        return task;
//    }

}