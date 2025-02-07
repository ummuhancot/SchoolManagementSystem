package com.cot.ummu.entity.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student"),
    MANAGER("Manager"),
    ASSISTANT_MANAGER("ViceDean");


    //boş constacker kullanmamamızın nedeni parametreli sadece consstackerı kullanmasını istediğimiz icin
    public final String name;

    RoleType(String name) {
        this.name = name;
    }


}
