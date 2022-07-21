package com.ntsuong.web.config;

import com.ntsuong.web.model.Authority;
import com.ntsuong.web.model.Privilege;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object accessType, Object permission) {
        if (authentication != null && accessType instanceof String) {
            boolean hasAccess = validateAccess(authentication, String.valueOf(accessType),String.valueOf(permission));
            return hasAccess;
        }
        return false;
    }

    private boolean validateAccess(Authentication authentication,  String accessType, String permission) {
        List<Authority> authorities = (List<Authority>) authentication.getAuthorities();
        AtomicBoolean isAccess = new AtomicBoolean(false);
        authorities.forEach(x->{
            List<Privilege> privileges = x.getPrivileges();
            boolean isComponent = privileges.stream().filter(s ->
                    Objects.equals(s.getComponent(), String.valueOf(accessType))).count() > 0;
            boolean isPermission = privileges.stream().filter(s ->
                    Objects.equals(s.getValue(), String.valueOf(permission))).count() > 0;

            if(isComponent && isPermission){
                isAccess.set(true);
            }
        });
        return isAccess.get();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String targetType,
                                 Object permission) {
        return false;
    }
}
