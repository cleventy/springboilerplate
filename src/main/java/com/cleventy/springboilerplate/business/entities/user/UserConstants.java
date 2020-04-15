package com.cleventy.springboilerplate.business.entities.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserConstants {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_SHOP = "SHOP";
    public static final Set<String> ROLES = new HashSet<>(Arrays.asList(ROLE_ADMIN, ROLE_USER, ROLE_SHOP));

    public static final Integer STATE_INACTIVE = Integer.valueOf(0);
    public static final Integer STATE_ACTIVE = Integer.valueOf(1);
    public static final Set<Integer> STATES = new HashSet<>(Arrays.asList(STATE_INACTIVE, STATE_ACTIVE));

}
