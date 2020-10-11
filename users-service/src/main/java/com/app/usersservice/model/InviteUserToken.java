package com.app.usersservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "unique_user_token", columnNames = {"USER_ID", "VERIFICATION_TOKEN" }))
@Entity
public class InviteUserToken {

    private static final int EXPIRES_IN_MINUTES = 60 *24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "VERIFICATION_TOKEN")
    private String token;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;

    private LocalDateTime expirationDate;

    public boolean hasTokenExpired() {
        return this.expirationDate.isAfter(LocalDateTime.now());
    }

    @PrePersist
    public void setExpirationDate() {
        this.expirationDate = LocalDateTime.now().plusMinutes(EXPIRES_IN_MINUTES);
    }
}
