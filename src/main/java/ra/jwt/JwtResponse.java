package ra.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String userName;
    private List<String> listRoles;

    public JwtResponse(String token, String email, String userName, List<String> listRoles) {
        this.token = token;
        this.email = email;
        this.userName = userName;
        this.listRoles = listRoles;
    }
}
