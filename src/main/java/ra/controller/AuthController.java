package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.Auth.LoginDTO;
import ra.dto.Auth.SignUpDTO;
import ra.jwt.JwtResponse;
import ra.service.Auth.AuthService;

@RestController()
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {
    private final AuthService service;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok(service.registerUser(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> handleLogin(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(service.handleLogin(loginDTO));
    }

}
