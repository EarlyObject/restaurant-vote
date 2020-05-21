package com.earlyobject.ws.shared.utils;

import com.earlyobject.ws.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static long getId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserPrincipal) principal).getId();
    }
}
