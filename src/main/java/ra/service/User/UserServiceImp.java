package ra.service.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.dto.User.UpdateUserDTO;
import ra.model.Users;
import ra.repository.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepo userRepo;
    @Override
    public Users findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepo.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Users saveOrUpdate(Users user) {
        return userRepo.save(user);
    }

    @Override
    public List<Users> searchUser(String keyword) {
        return userRepo.searchUser(keyword);
    }

    public List<Users> getAllUsers(){
        return userRepo.findAll();
    }

    public boolean deleteUser(Integer id){
        boolean exist = userRepo.existsById(id);
        if(exist){
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Users updateUser(UpdateUserDTO user, Integer id){
        Users updateUser = userRepo.findById(id).orElseThrow(RuntimeException::new);
        updateUser.setEmail(user.getEmail());
        updateUser.setStatus(user.isStatus());
        updateUser.setCreated_date(user.getCreated_date());
        return userRepo.save(updateUser);

    }
}
