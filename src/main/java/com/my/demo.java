package com.my;

import com.my.util.Security;

public class demo {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            System.out.println(Security.generateCvv());
        }

    }
}
