package com.cot.ummu;

import com.cot.ummu.entity.concretes.user.UserRole;
import com.cot.ummu.entity.enums.RoleType;
import com.cot.ummu.repository.user.UserRoleRepository;
import com.cot.ummu.service.user.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolManagementBackendB326Application implements CommandLineRunner {


  private final UserRoleService userRoleService;
  private final UserRoleRepository userRoleRepository;

  public SchoolManagementBackendB326Application(UserRoleService userRoleService, UserRoleRepository userRoleRepository) {
    this.userRoleService = userRoleService;
    this.userRoleRepository = userRoleRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(SchoolManagementBackendB326Application.class, args);
  }


  @Override//bütün uygulamadan önce calışır.
  public void run(String... args) throws Exception {
    if(userRoleService.getAllUserRoles().isEmpty()) {
      //admin
      UserRole admin = new UserRole();
      admin.setRoleType(RoleType.ADMIN);
      admin.setRoleName(RoleType.ADMIN.name());
      userRoleRepository.save(admin);
      //dean
      UserRole dean = new UserRole();
      dean.setRoleType(RoleType.MANAGER);
      dean.setRoleName(RoleType.MANAGER.name());
      userRoleRepository.save(dean);
      //vice-dean
      UserRole viceDean = new UserRole();
      viceDean.setRoleType(RoleType.ASSISTANT_MANAGER);
      viceDean.setRoleName(RoleType.ASSISTANT_MANAGER.name());
      userRoleRepository.save(viceDean);
      //student
      UserRole student = new UserRole();
      student.setRoleType(RoleType.STUDENT);
      student.setRoleName(RoleType.STUDENT.name());
      userRoleRepository.save(student);
      //teacher
      UserRole teacher = new UserRole();
      teacher.setRoleType(RoleType.TEACHER);
      teacher.setRoleName(RoleType.TEACHER.name());
      userRoleRepository.save(teacher);

    }
  }
}
