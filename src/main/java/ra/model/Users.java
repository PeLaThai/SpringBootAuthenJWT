package ra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_date;

    @Column(unique = true, nullable = false)
    private String email;


    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    @JsonManagedReference
    private Collection<Roles> listRoles;

}
