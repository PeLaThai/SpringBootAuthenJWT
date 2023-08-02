package ra.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SignUpDTO {
    private String userName;
    private String password;
    private String email;
    private Date created_date;
    private boolean status;
    private List<String> listRoles;
}
