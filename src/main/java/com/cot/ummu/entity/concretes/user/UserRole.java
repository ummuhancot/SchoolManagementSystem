package com.cot.ummu.entity.concretes.user;

import com.cot.ummu.entity.enums.RoleType;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
@AllArgsConstructor//(access = AccessLevel.MODULE) burdan belirlenebiliyor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String roleName;






}
