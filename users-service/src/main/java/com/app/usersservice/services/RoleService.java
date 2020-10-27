package com.app.usersservice.services;

import com.app.usersservice.dto.RoleDto;
import com.app.usersservice.mapper.ModelMapper;
import com.app.usersservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(ModelMapper::toRoleDto)
                .collect(Collectors.toList());
    }
}
