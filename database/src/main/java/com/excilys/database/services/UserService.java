package com.excilys.database.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.database.entities.role.User;
import com.excilys.database.entities.role.UserRole;
import com.excilys.database.persistence.UserDAO;

@Service("userService")
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;


    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userDAO.findByLogin(login);
        return buildUserForAuthentication(user,
                setUserAuthority(user.getRole()));
    }

    /**
     * getting UserDetails from User object.
     * @param user user to convert
     * @param authorities user authorities
     * @return UserDetail use by Spring Security
     */
    private org.springframework.security.core.userdetails.User buildUserForAuthentication(
            User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(), authorities);
    }

    /**
     * getting list of GrantedAuthority from UserRole.
     * @param userRole userRole to convert
     * @return List of GrantedAuthority
     */
    private List<GrantedAuthority> setUserAuthority(UserRole userRole) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList
        .add(new SimpleGrantedAuthority(userRole.toString()));
        return grantedAuthorityList;
    }

}