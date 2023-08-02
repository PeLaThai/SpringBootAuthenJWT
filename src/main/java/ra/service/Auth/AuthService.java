package ra.service.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.dto.Auth.LoginDTO;
import ra.dto.Auth.SignUpDTO;
import ra.jwt.JwtResponse;
import ra.jwt.TokenProvider;
import ra.model.ERole;
import ra.model.Roles;
import ra.model.Users;
import ra.security.CustomUserDetails;
import ra.service.Role.RoleService;
import ra.service.User.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    public JwtResponse handleLogin(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUserName(),
                        loginDTO.getPassword()
                )
        );
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwtToken = tokenProvider.generateToken(customUserDetails);
            List<String> listRoles = customUserDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());

            return new JwtResponse(
                    jwtToken,
                    customUserDetails.getEmail(),
                    customUserDetails.getUsername(),
                    listRoles
            );
        }
        return null;
    }

    public ResponseEntity<?> registerUser(SignUpDTO signUpDTO) {
        if (userService.existsByUserName(signUpDTO.getUserName())) {
            return ResponseEntity.badRequest().body("User name is existing!");
        }
        if (userService.existsByEmail(signUpDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email is existing!");
        }
        Users new_user = new Users();
        new_user.setUserName(signUpDTO.getUserName());
        new_user.setPassword(encoder.encode(signUpDTO.getPassword()));
        new_user.setEmail(signUpDTO.getEmail());
        new_user.setStatus(true);
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNow = new Date();
        String strNow = date.format(dateNow);
        try {
            new_user.setCreated_date(date.parse(strNow));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> strRoles = signUpDTO.getListRoles();
        List<Roles> listRoles = new ArrayList<>();
        if (strRoles == null || strRoles.size() == 0) {
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Role is not found"));
                        listRoles.add(adminRole);
                        break;
                    case "moderator":
                        Roles moderatorRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Role is not found"));
                        listRoles.add(moderatorRole);
                        break;
                }
            });
        }
        new_user.setListRoles(listRoles);
        userService.saveOrUpdate(new_user);
        return ResponseEntity.ok().body("Create new user Successfully!");
    }


}
