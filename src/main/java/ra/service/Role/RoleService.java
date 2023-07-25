package ra.service.Role;

import ra.model.ERole;
import ra.model.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
