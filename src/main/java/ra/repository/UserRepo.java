package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.model.Users;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    public Users findByUserName(String userName);
    public boolean existsByUserName(String userName);
    public boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u WHERE " +
            "u.userName LIKE CONCAT('%', :keyword, '%')")
    public List<Users> searchUser(String keyword);

}
