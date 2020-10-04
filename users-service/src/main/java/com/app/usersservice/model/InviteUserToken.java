package com.app.usersservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InviteUserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expirationDate;


}
