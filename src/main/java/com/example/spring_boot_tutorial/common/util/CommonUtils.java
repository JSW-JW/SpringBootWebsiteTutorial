package com.example.spring_boot_tutorial.common.util;

import java.util.UUID;


public class CommonUtils {
    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
