package com.stockhelt.backend.user;

import com.stockhelt.backend.user.dto.DoctorDTO;
import com.stockhelt.backend.user.dto.UserCreationDTO;
import com.stockhelt.backend.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.stockhelt.backend.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.allUsersForList();
    }

    @GetMapping(DOCTORS)
    public List<DoctorDTO> allDoctors(){
       return userService.allDoctors();
    }
    @PostMapping
    public UserDTO create(@RequestBody UserCreationDTO user){
        return userService.create(user);
    }

    @PutMapping(ENTITY)//full edit
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO user){
        return userService.edit(id, user);
    }

    @PatchMapping(ENTITY)//partial edit
    public UserDTO changePassword(@PathVariable Long id, @RequestBody String newPassword){
        return userService.changePassword(id, newPassword);
    }

    @GetMapping(ENTITY)
    public UserDTO getUser(@PathVariable Long id){
        return userService.get(id);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id)
    {
        userService.delete(id);
    }

}
