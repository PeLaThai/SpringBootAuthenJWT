package ra.service.User;

import ra.model.Users;

public interface UserService {
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);


}
