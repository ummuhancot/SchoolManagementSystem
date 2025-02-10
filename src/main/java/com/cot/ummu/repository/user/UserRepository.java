package com.cot.ummu.repository.user;

import com.cot.ummu.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsBySsn(String ssn);
    boolean existsByPhoneNumber(String phoneNumber);
}
