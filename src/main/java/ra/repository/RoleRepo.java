package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.ERole;
import ra.model.Roles;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Integer> {
    public Optional<Roles> findByRoleName(ERole roleName);
}
