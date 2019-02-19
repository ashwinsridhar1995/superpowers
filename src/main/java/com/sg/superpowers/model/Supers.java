/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superpowers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author ashwinsridhar
 */
public class Supers {
    private int supersId;
    @NotEmpty(message = "You must supply a value for Bio.")
    @Length(max = 50, message = "Bio must be no more than 50 characters in length.")
    private String bio;
    @NotEmpty(message = "You must supply a value for Power.")
    @Length(max = 50, message = "Power must be no more than 50 characters in length.")
    private String power;
    @NotEmpty(message = "You must supply a value for Name.")
    @Length(max = 50, message = "Name must be no more than 50 characters in length.")
    private String name;
   
    private List<Organization> organizations = new ArrayList<>();

    public int getSupersId() {
        return supersId;
    }

    public void setSupersId(int supersId) {
        this.supersId = supersId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.supersId;
        hash = 19 * hash + Objects.hashCode(this.bio);
        hash = 19 * hash + Objects.hashCode(this.power);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.organizations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Supers other = (Supers) obj;
        return true;
    }
    
    
}
