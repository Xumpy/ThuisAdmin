/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security;

import com.xumpy.thuisadmin.model.db.Personen;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/**
 *
 * @author nicom
 */
public class UserService implements UserDetailsService {
    
    @Autowired 
    private Personen persoon;
    
    private SessionFactory sessionFactory;
    
    public void setPersonen(Personen persoon){
        this.persoon = persoon;
    }
    
    public  void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session session = sessionFactory.openSession();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        Query query = session.createQuery("From Personen where lower(username) = :username");
        query.setString("username", username.toLowerCase());
        
        Personen internalPersoon = (Personen)query.list().get(0);
        
        persoon.setPk_id(internalPersoon.getPk_id());
        persoon.setUsername(internalPersoon.getUsername());
        persoon.setMd5_password(internalPersoon.getMd5_password());
        persoon.setNaam(internalPersoon.getNaam());
        persoon.setVoornaam(internalPersoon.getVoornaam());
        
        User user = new User(persoon.getUsername(), persoon.getMd5_password(), authorities);
        
        session.close();
        return user;
    }
}
