package com.back.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {

    CAREGIVER(1, "CAREGIVER"), OWNER(2, "OWNER");

    private final int code;
    private final String name;
}
