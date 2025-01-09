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
import java.util.Iterator;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author alexander
 */
public class CustomAuthenticationManager implements AuthenticationProvider {

    private UsersService usersService;

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    // comprobar contraseñas
    boolean compararContrasenia(String contrasenia_encrip, String contrasenia_aut) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return bcpe.matches(contrasenia_aut, contrasenia_encrip);
    }

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        Users u = null;

        try {
//            System.out.println(a.getPrincipal().getClass().getCanonicalName());
            u = this.usersService.findUser(a.getName());

        } catch (Exception e) {

            throw new BadCredentialsException("Usuario no existe");
        }
        if (u != null) {

            WebAuthenticationDetails credentials = (WebAuthenticationDetails) a.getDetails();
            String remoteAddress = credentials.getRemoteAddress();
            Collection<UserRoles> userRolesCollection = u.getUserRolesCollection();
            Iterator<UserRoles> iterator = userRolesCollection.iterator();
            UserRoles rol = iterator.next();
            // esto hay que eliminarlo cuando todos los usuarios tengan contraseña encriptada.
            if (u.getPassword().toCharArray().length < 20) {
                if (!u.getPassword().equals(a.getCredentials().toString())) {
                    throw new BadCredentialsException("Password o Usuario  no coincide..!");
                }
            } else {
                boolean comparar = compararContrasenia(u.getPassword(), a.getCredentials().toString());
                if (!comparar) {
                    throw new BadCredentialsException("Password o Usuario  no coincide..!");
                }
            }

            if (!u.getEnabled()) {
                throw new BadCredentialsException("Usuario Inhabilitado..");
            }
        } else {
            throw new BadCredentialsException("Usuario Inexistente..");
        }

        UserExtended userExt = new UserExtended(u.getUsername(), u.getPassword(), u.getEnabled(), true, true, true, makeGrantedAuthorities(u), u.getEmail(), u.getNombres(), u.getUltimoAcceso().toString());

        return new UsernamePasswordAuthenticationToken(
                userExt,
                a.getCredentials(),
                makeGrantedAuthorities(u));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private Collection<GrantedAuthority> makeGrantedAuthorities(Users user) {
        Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        int i = 0;
        for (UserRoles role : user.getUserRolesCollection()) {
            result.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return result;
    }
}
