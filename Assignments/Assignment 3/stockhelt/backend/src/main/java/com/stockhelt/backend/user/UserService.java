package com.stockhelt.backend.user;

import com.stockhelt.backend.user.dto.DoctorDTO;
import com.stockhelt.backend.user.dto.UserCreationDTO;
import com.stockhelt.backend.user.dto.UserDTO;
import com.stockhelt.backend.user.dto.UserMinimalDTO;
import com.stockhelt.backend.user.mapper.UserMapper;
import com.stockhelt.backend.user.model.Doctor;
import com.stockhelt.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::toMinimalDto)
                .collect(toList());
    }

    public List<UserDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(toList());
    }

    public UserDTO edit(Long id, UserDTO user) {
        User actUser = findById(id);
        actUser.setEmail(user.getEmail());
        actUser.setUsername(user.getUsername());

        return userMapper.toDto(
                userRepository.save(actUser)
        );
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public UserDTO changePassword(Long id, String newPassword) {
        return null;
    }

    public UserDTO get(Long id) {
        return userMapper.toDto(userRepository.findById(id).get());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO create(UserCreationDTO user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new RuntimeException("User with username:" + user.getUsername()+" already exists");
        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("User with email:" + user.getUsername()+" already exists");

        if(user.getRole().equals("Doctor")) {
            Doctor actUser = userMapper.docFromUserCreationDto(user);


            actUser.setPassword(passwordEncoder.encode(actUser.getPassword()));
            return userMapper.toDto(
                    userRepository.save(actUser)
            );
        }
        return null;
    }

    public List<DoctorDTO> allDoctors() {
        return null;
    }
}
