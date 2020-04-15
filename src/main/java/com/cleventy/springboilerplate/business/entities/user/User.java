package com.cleventy.springboilerplate.business.entities.user;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "USER")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User implements Serializable {

    private static final long serialVersionUID = 3233149207833106460L;
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "USERNAME")    
    private String username;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "ROLE")
    private String role;
    
    @Column(name = "STATE")
    private Integer state;
    
    @Column(name = "REGISTER_DATE")
    private Calendar registerDate;
    
    @Version
    @Column(name = "VERSION")
    private Long version;

}
