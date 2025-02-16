package com.cot.ummu;

import com.cot.ummu.entity.concretes.user.UserRole;
import com.cot.ummu.entity.enums.Gender;
import com.cot.ummu.entity.enums.RoleType;
import com.cot.ummu.payload.request.user.UserRequest;
import com.cot.ummu.repository.user.UserRepository;
import com.cot.ummu.repository.user.UserRoleRepository;
import com.cot.ummu.service.user.UserRoleService;
import com.cot.ummu.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SchoolManagementBackendB326Application implements CommandLineRunner {


  private final UserRoleService userRoleService;
  private final UserRoleRepository userRoleRepository;
  private final UserService userService;

  public SchoolManagementBackendB326Application(UserRoleService userRoleService, UserRoleRepository userRoleRepository, UserService userService) {
    this.userRoleService = userRoleService;
    this.userRoleRepository = userRoleRepository;
      this.userService = userService;
  }

  public static void main(String[] args) {
    SpringApplication.run(SchoolManagementBackendB326Application.class, args);
  }


  @Override
  public void run(String... args) throws Exception {
    if(userRoleService.getAllUserRoles().isEmpty()){
      //admin
      UserRole admin = new UserRole();
      admin.setRoleType(RoleType.ADMIN);
      admin.setRoleName(RoleType.ADMIN.getName());
      userRoleRepository.save(admin);
      //dean
      UserRole dean = new UserRole();
      dean.setRoleType(RoleType.MANAGER);
      dean.setRoleName(RoleType.MANAGER.getName());
      userRoleRepository.save(dean);
      //vice-dean
      UserRole viceDean = new UserRole();
      viceDean.setRoleType(RoleType.ASSISTANT_MANAGER);
      viceDean.setRoleName(RoleType.ASSISTANT_MANAGER.getName());
      userRoleRepository.save(viceDean);
      //student
      UserRole student = new UserRole();
      student.setRoleType(RoleType.STUDENT);
      student.setRoleName(RoleType.STUDENT.getName());
      userRoleRepository.save(student);
      //teacher
      UserRole teacher = new UserRole();
      teacher.setRoleType(RoleType.TEACHER);
      teacher.setRoleName(RoleType.TEACHER.getName());
      userRoleRepository.save(teacher);
    }
    if (userService.getAllUsers().isEmpty()){
      userService.saveUser(getUserRequest(),RoleType.ADMIN.getName());
    }
  }


  private static UserRequest getUserRequest(){
    UserRequest userRequest = new UserRequest();
    userRequest.setUsername("admin");
    userRequest.setEmail("admin@admin.com");
    userRequest.setSsn("111-11-1111");
    userRequest.setPassword("Ankara06*");
    userRequest.setBuildIn(true);
    userRequest.setName("adminName");
    userRequest.setSurname("adminSurname");
    userRequest.setPhoneNumber("111-111-1111");
    userRequest.setGender(Gender.FEMALE);
    userRequest.setBirthDay(LocalDate.of(1980,1,1));
    userRequest.setBirthPlace("Texas");
    return userRequest;
  }


}
