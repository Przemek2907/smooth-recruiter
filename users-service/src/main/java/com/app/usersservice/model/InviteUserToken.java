package com.app.usersservice.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

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

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime();
    }
}
