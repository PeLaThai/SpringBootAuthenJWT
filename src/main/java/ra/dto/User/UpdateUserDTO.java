package ra.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class UpdateUserDTO {
    private String email;
    private boolean status;
    private Date created_date;

}
