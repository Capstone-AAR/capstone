package com.capstone.demo.models;

/////////////////////////////////////////////////////
// Libraries imported and being used in this class.
/////////////////////////////////////////////////////

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;

//////////////////////////////////////////////////////////////////////////////////////////
// User with roles is the model used in order to facilitate authentication and security.
//////////////////////////////////////////////////////////////////////////////////////////
public class UserWithRoles extends User implements UserDetails {

    ///////////////////////////////////////////////////////////////////
    // Constructor method that initializes this class and it calls the
    // copy constructor defined in parent.
    ///////////////////////////////////////////////////////////////////
    public UserWithRoles(User user) {

        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        String roles = StringUtils.collectionToCommaDelimitedString(Collections.emptyList());

        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }


    ///////////////////////////////////////////
    // Methods used in authentication process.
    ///////////////////////////////////////////
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
