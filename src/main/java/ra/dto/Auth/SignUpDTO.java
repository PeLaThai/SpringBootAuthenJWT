package ra.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String userName;
    private String password;
    private String email;
    private Date created_date;
    private boolean status;
    private List<String> listRoles;
}
