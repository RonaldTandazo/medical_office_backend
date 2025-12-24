package com.example.proyecto_citas_medicas.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.proyecto_citas_medicas.entities.User;
import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password = :new_password WHERE user_id = :user_id AND status = 'A'", nativeQuery = true)
    int updateUserPassword(@Param("user_id") Long user_id, @Param("new_password") String new_password);

    @Query(value = "UPDATE users SET username = :username, identification = :identification, gender = :gender, "
        + "age = :age, phonenumber = :phonenumber "
        + "WHERE user_id = :userId AND status = 'A' "
        + "RETURNING user_id, username, email, identification, gender, age, phonenumber, avatar, password, status",
        nativeQuery = true)
    User updateUserInformation(
        @Param("userId") Long userId,
        @Param("username") String username,
        @Param("identification") String identification,
        @Param("gender") Character gender,
        @Param("age") Long age,
        @Param("phonenumber") String phonenumber
    );

    @Query(value = "Select distinct menus.menu_id, menus.name as menu, menus.icon, menus.path from user_roles "
        + "join roles on roles.role_id = user_roles.role_id and roles.status = 'A'"
        + "join role_permissions on role_permissions.role_id = roles.role_id and role_permissions.status = 'A' "
        + "join permissions on permissions.permission_id = role_permissions.permission_id and permissions.status = 'A' "
        + "join submenus on submenus.submenu_id = permissions.submenu_id and submenus.status = 'A' "
        + "join menus on submenus.menu_id = menus.menu_id and menus.status = 'A' "
        + "Where user_roles.status = 'A' and user_roles.user_id = :user_id and user_roles.role_id = :role_id"
        , nativeQuery = true
    )
    List<Map<String, Object>> getAsignedMenus(@Param("user_id") Long user_id, @Param("role_id") Long role_id);

    @Query(value = "Select distinct submenus.submenu_id, submenus.name, submenus.path, submenus.icon from user_roles "
        + "join roles on roles.role_id = user_roles.role_id and roles.status = 'A' "
        + "join role_permissions on role_permissions.role_id = roles.role_id and role_permissions.status = 'A' "
        + "join permissions on permissions.permission_id = role_permissions.permission_id and permissions.status = 'A' "
        + "join submenus on submenus.submenu_id = permissions.submenu_id and submenus.status = 'A' "
        + "join menus on submenus.menu_id = menus.menu_id and menus.status = 'A' "
        + "Where user_roles.status = 'A' and user_roles.user_id = :user_id and user_roles.role_id = :role_id and submenus.menu_id = :menu_id"
        , nativeQuery = true
    )
    List<Map<String, Object>> getPermissions(@Param("user_id") Long user_id, @Param("role_id") Long role_id, @Param("menu_id") Long menu_id);
}
