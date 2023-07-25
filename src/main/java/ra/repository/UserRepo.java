package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.Users;


@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    public Users findByUserName(String userName);
    public boolean existsByUserName(String userName);
    public boolean existsByEmail(String email);

}
