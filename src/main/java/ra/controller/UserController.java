package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.User.UpdateUserDTO;
import ra.model.Users;
import ra.service.User.UserServiceImp;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserServiceImp userService;

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Users> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.findByUserName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        boolean result = userService.deleteUser(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Delete user Successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    @PutMapping("{id}")
    public ResponseEntity<Users> updateUser(@RequestBody UpdateUserDTO user, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateUser(user, id));
    }
}
