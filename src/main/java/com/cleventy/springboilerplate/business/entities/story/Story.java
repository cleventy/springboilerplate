package com.cleventy.springboilerplate.business.entities.story;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.cleventy.springboilerplate.business.entities.category.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STORY")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Story implements Serializable {

	private static final long serialVersionUID = 1528491723909281373L;
	
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "TITLE")    
    private String title;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "WEB_URL")
    private String webUrl;
    
    @Column(name = "IMAGE")
    private String image;
    
    @Column(name = "IMAGE_MINI")
    private String imageMini;
    
    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(
        name = "STORY_CATEGORY", 
        joinColumns = { @JoinColumn(name = "STORY_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID") }
    )
    private Set<Category> categories;
    

    @Column(name = "DIFFICULTY")
    private Double difficulty;
    
    @Column(name = "BOOK_ENABLED")
    private Boolean bookEnabled;
    
    @Column(name = "BOOK_PRICE")
    private Double bookPrice;
    
    @Column(name = "BOOK_DESCRIPTION")
    private String bookDescription;
    
    @Column(name = "ORDER_N")
    private Integer order;
    
    @Column(name = "STATE")
    private Integer state;
    
    @Version
    @Column(name = "VERSION")
    private Long version;

}