package ra.service.User;

import ra.model.Users;

import java.util.List;

public interface UserService {
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);

    List<Users> searchUser(String keyword);
}
