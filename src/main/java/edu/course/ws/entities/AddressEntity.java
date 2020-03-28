package edu.course.ws.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, name = "address_Id")
    private String addressId;

    @Column(nullable = false , length = 30)
    private String country;

    @Column(nullable = false , length = 30)
    private String city;

    @Column(nullable = false , length = 100)
    private String streetName;

    @Column(nullable = false , length = 10)
    private String postalCode;

    @Column(nullable = false, length = 25)
    private String typeAddress;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private UserEntity userDTO;
}
