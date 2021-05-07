package com.stockhelt.backend.user;

import com.stockhelt.backend.user.model.Doctor;
import com.stockhelt.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Doctor findDoctorByUsername(String doctorName);

    Doctor findDoctorById(Long id);
}
