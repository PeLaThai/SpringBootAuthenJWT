package ra.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.model.Users;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private int userId;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    private Date created_date;
    private boolean isUserStatus;
    private Collection<? extends GrantedAuthority> authorities;

    //Mapping info User -> CustomUserDetails
    public static CustomUserDetails mapUserToUserDetail(Users user) {
        //get all roles on user and set to CustomUserDetail
        List<GrantedAuthority> listAuthorities = user.getListRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getCreated_date(),
                user.isStatus(),
                listAuthorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
