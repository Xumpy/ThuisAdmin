/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.root;

import com.xumpy.security.model.InvoiceType;
import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
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
import org.springframework.stereotype.Service;
/**
 *
 * @author nicom
 */
@Service("userService")
public class UserService implements UserDetailsService {
    
    @Autowired UserInfo userInfo;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        
        Session session = sessionFactory.openSession();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        Query query = session.createQuery("From PersonenDaoPojo where lower(username) = :username");
        query.setString("username", username.toLowerCase());
        
        PersonenDaoPojo internalPersoon = (PersonenDaoPojo)query.list().get(0);
        
        userInfo.setPersoon(internalPersoon);
        userInfo.setInvoiceType(InvoiceType.PERSONAL);

        User user = new User(userInfo.getPersoon().getUsername(), userInfo.getPersoon().getMd5_password(), authorities);
        
        session.close();
        return user;
    }
}
