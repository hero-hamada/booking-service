package com.epam.bookingservice.model;

import com.epam.bookingservice.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

import static com.epam.bookingservice.security.ApplicationUserRole.USER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NonNull
    @Column(name = "username")
    private String name;
    @NonNull
    private String email;
    private String password;
    @NonNull
    private String role = "USER";
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked = true;
    @Column(name = "register_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate = LocalDate.now();
    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    @OneToOne(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserAccount account;


    public User(String name, String email, UserAccount userAccount) {
        this.name = name;
        this.email = email;
        this.account = userAccount;
    }
}
