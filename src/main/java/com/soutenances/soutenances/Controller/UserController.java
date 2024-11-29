package com.soutenances.soutenances.Controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soutenances.soutenances.DTO.SalleDTO;
import com.soutenances.soutenances.DTO.UserDTO;
import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Models.User;
import com.soutenances.soutenances.Service.UserService;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/UserController")
public class UserController {

    private final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/CreateUser")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO)
    {
       try{
           User user = userService.CreateUser(userDTO);
           return ResponseEntity.ok("User Created with Success"+user);


       }catch (Exception e ){

           return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR WHILE CREATING"+e.getMessage());
       }
    }

    @DeleteMapping("/DeleteUser/{id}")
    public ResponseEntity<String>DeleteUser(@PathVariable int id){
        try{
            userService.DeleteUser(id);
            return ResponseEntity.ok("user deleted with Success");

        }
        catch(Exception e){

            throw new RuntimeException("Error="+e.getMessage(),e);

        }

    }

    @GetMapping("/getAllUser")

    public ResponseEntity<List<User>>GetAllUsers(){
        try
        {
          List<User> users = userService.GetAllUsers();
          return ResponseEntity.ok(users);
        }catch(Exception e){
            throw new RuntimeException("Error = "+e.getMessage(),e);
        }

    }

    @PutMapping("UpdateUser/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable int id,@RequestBody UserDTO userDTO){

        try
        {
            User editedUser = userService.EditUser(id, userDTO);
            return ResponseEntity.ok(editedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }

    @GetMapping("/getAllUserAreNotInproject")

    public ResponseEntity<List<User>>GetAllUsersWithoutProject(){
        try
        {
            List<User> users = userService.findAvailableStudents();
            return ResponseEntity.ok(users);
        }catch(Exception e){
            throw new RuntimeException("Error = "+e.getMessage(),e);
        }

    }

    @GetMapping("/available")
    public ResponseEntity<List<User>> getAvailableTeacher(@RequestParam LocalDate date, @RequestParam LocalTime time) {
        List<User> availableTeacher = userService.findAvailableTeacher(date, time);
        return ResponseEntity.ok(availableTeacher);
    }




}
