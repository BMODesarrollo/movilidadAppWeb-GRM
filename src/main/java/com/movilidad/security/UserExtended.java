/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author alexander
 */
public class UserExtended extends User{
    private String email;
    private String nombre;
    private String apellidos;

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public UserExtended(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserExtended(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    public UserExtended(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, 
            boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,String email,String nombre,String apellidos) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.email=email;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(nombre);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (super.getUsername() != null ? super.getUsername().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserExtended other = (UserExtended) obj;
        if ((super.getUsername() == null) ? (other.getUsername() != null) : !super.getUsername().equals(other.getUsername())) {
            return false;
        }
        return true;
    }
    
}
