package me.fatamorgana.ttb.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import me.fatamorgana.ttb.crm.constant.StatusCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String firstname;
    
    private String lastname;
    
    private LocalDateTime customerDate;
    
    private Boolean isVip;
    
    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    private LocalDateTime createdOn;
    
    private LocalDateTime modifiedOn;

    @PrePersist
    protected void onCreate() {
    	LocalDateTime now = LocalDateTime.now();
        createdOn = now;
        modifiedOn = now;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedOn = LocalDateTime.now();
    }
}

