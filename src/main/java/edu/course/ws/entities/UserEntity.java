package edu.course.ws.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, unique = true, length = 120)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;

    @OneToMany(mappedBy = "userDTO", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

    private String emailVerificationToken;
    private Boolean emailVerificationStatus;



}
