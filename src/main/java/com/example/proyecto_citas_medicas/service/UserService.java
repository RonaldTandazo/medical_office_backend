package com.example.proyecto_citas_medicas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.repository.UserRepository;
import com.example.proyecto_citas_medicas.repository.UserRolesRepository;
import com.example.proyecto_citas_medicas.specifications.UserSpecification;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    protected final UserRepository userRepository;
    protected final UserRolesRepository userRolesRepository;
    protected final UserSpecification userSpecification;

    public UserService(UserRepository userRepository, UserSpecification userSpecification, UserRolesRepository userRolesRepository){
        this.userRepository = userRepository;
        this.userSpecification = userSpecification;
        this.userRolesRepository = userRolesRepository;
    }

    public Map<String, Object> getAllUsers(String identification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        Specification<User> spec = this.userSpecification.getAllUsers(identification);
    
        Page<User> usersPage = userRepository.findAll(spec, pageable);
    
        List<Map<String, Object>> dataUsers = new ArrayList<>();
    
        for (User user : usersPage) {
            Long user_id = user.getUserId();
            Character[] status = {'A', 'I', 'D'};
            List<Map<String, Object>> roles = userRolesRepository.findRolesByUserId(user_id, status);
    
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", user.getUserId());
            userData.put("fullname", user.getUsername());
            userData.put("identification", user.getIdentification());
            userData.put("email", user.getEmail());
            userData.put("phonenumber", user.getPhonenumber());
            userData.put("age", user.getAge());
            userData.put("gender", user.getGender());
            userData.put("status", user.getStatus());
            userData.put("roles", roles);
    
            dataUsers.add(userData);
        }
    
        Map<String, Object> response = new HashMap<>();
        response.put("users", dataUsers);
        response.put("pagination", Map.of(
            "totalElements", usersPage.getTotalElements(),
            "totalPages", usersPage.getTotalPages(),
            "currentPage", usersPage.getNumber(),
            "pageSize", usersPage.getSize()
        ));
        
        return response;
    } 

    public User verifyUser(String email) {
        return userRepository.findByEmail(email)
            .filter(u -> u.getStatus() == 'A')
            .orElse(null);
    }

    public User updatePassword(Long user_id, String new_password){
        return userRepository.updatePassword(user_id, new_password);
    }

    public User updateUserInformation(User user){
        Long userId = user.getUserId();
        String username = user.getUsername();
        String identification = user.getIdentification();
        Character gender = user.getGender();
        Long age = user.getAge();
        String phonenumber = user.getPhonenumber();

        return userRepository.updateUserInformation(userId, username, identification, gender, age, phonenumber);
    }

    public List<Map<String, Object>> getPermissions(Long user_id, Long role_id) {
        List<Map<String, Object>> menus = userRepository.getAsignedMenus(user_id, role_id);

        List<Map<String, Object>> mutableMenus = new ArrayList<>();

        for (Map<String, Object> menu : menus) {
            Map<String, Object> mutableMenu = new HashMap<>(menu);

            Long menu_id = (Long) mutableMenu.get("menu_id");

            List<Map<String, Object>> submenus = userRepository.getPermissions(user_id, role_id, menu_id);

            mutableMenu.put("submenus", submenus);
            mutableMenus.add(mutableMenu);
        }

        return mutableMenus;
    }

    public boolean userActivationControl(Long user_id, String state) {
        if (!"Activate".equalsIgnoreCase(state) && !"Inactivate".equalsIgnoreCase(state)) {
            throw new IllegalArgumentException("Invalid state: " + state);
        }

        Character status = "Activate".equalsIgnoreCase(state) ? 'A' : 'I';

        Optional<User> existingUser = userRepository.findById(user_id);

        if (existingUser.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        User user = existingUser.get();
        user.setStatus(status);
        userRepository.save(user);

        return true;
    }
}
