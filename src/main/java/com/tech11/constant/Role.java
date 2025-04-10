package com.tech11.constant;

import jakarta.json.bind.annotation.JsonbCreator;

public enum Role {
    USER,          
    ADMIN,         
    MANAGER,       
    MODERATOR,     
    SUPPORT,       
    GUEST;
    
    
    @JsonbCreator
    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}
