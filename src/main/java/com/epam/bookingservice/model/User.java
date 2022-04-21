package com.epam.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @OneToOne(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserAccount account;
//    @OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "id", referencedColumnName = "user_id")
//    private List<Ticket> tickets;

    public User(String name, String email, UserAccount userAccount) {
        this.name = name;
        this.email = email;
        this.account = userAccount;
    }
}
