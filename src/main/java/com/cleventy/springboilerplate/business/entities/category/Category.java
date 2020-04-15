package com.cleventy.springboilerplate.business.entities.category;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.cleventy.springboilerplate.business.entities.story.Story;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORY")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Category implements Serializable {

	private static final long serialVersionUID = 1528491723909281373L;
	
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME")    
    private String name;
    
    @ManyToMany(mappedBy = "categories")
    @EqualsAndHashCode.Exclude
    private Set<Story> stories;

    @Version
    @Column(name = "VERSION")
    private Long version;

}