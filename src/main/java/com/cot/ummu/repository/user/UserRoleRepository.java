package com.cot.ummu.repository.user;

import com.cot.ummu.entity.concretes.user.UserRole;
import com.cot.ummu.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    @Query("SELECT r FROM UserRole r WHERE r.roleType = ?1")
    Optional<UserRole> findByUserRoleType(RoleType roleType);
    //null controlu icin optinal kullandık.

}
