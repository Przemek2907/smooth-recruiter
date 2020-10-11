package com.app.usersservice.repository;

import com.app.usersservice.model.InviteUserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteUserRepository extends JpaRepository<InviteUserToken, Long> {
}
