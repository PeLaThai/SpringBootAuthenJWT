package ra.service.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.model.ERole;
import ra.model.Roles;
import ra.repository.RoleRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return roleRepo.findByRoleName(roleName);
    }
}
