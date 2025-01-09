/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.security;

import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import com.movilidad.service.UsersService;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexander
 */
@Service("usersDetailService") 
public class CustomServiceDetailLogin implements UserDetailsService{
    private UsersService usersService;

    public CustomServiceDetailLogin() {
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users findUser = this.usersService.findUser(username);
//        System.out.println("Entre aqui en el detail service");
        if(findUser==null){
         throw new UsernameNotFoundException("usuario no existe: " + username);
        }else
        return new User(findUser.getUsername(), findUser.getPassword(), true, true, true, true, makeGrantedAuthorities(findUser));
        
    }
    private Collection<GrantedAuthority> makeGrantedAuthorities(Users user) {
        Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        int i = 0;
        for (UserRoles role: user.getUserRolesCollection()) {
            result.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return result;
    }
    
}
