package com.app.usersservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {


    @Id
    @GeneratedValue
    private Long id;

    private String userEmail;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private BusinessRole businessRole;

    @Column(name = "IS_ENABLED")
    private Boolean enabled;

}
