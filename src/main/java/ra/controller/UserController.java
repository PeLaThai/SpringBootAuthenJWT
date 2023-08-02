package ra.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.dto.User.UpdateUserDTO;
import ra.model.Users;
import ra.security.CustomUserDetails;
import ra.service.User.UserServiceImp;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
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

    @GetMapping("/profile")
    public ResponseEntity<CustomUserDetails> userProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUser(@RequestParam String keyword){
        return ResponseEntity.ok(userService.searchUser(keyword));
    }
}
