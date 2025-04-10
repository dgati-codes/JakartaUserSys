package com.tech11.queries;

public class UserQueries {
    public static final String FIND_ALL_USERS = "SELECT u FROM Users u";
    public static final String FIND_USER_BY_ID = "SELECT u FROM Users u WHERE u.id = :id";
    public static final String EXISTS_USER_BY_ID = "SELECT COUNT(u) FROM Users u WHERE u.id = :id";
}
