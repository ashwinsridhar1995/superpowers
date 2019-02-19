/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superpowers.model;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
/**
 *
 * @author ashwinsridhar
 */
public class Location {
    private int locationId;
    @NotEmpty(message = "You must supply a value for Name.")
    @Length(max = 50, message = "Name must be no more than 50 characters in length.")
    private String name;
    @NotEmpty(message = "You must supply a value for Description.")
    @Length(max = 50, message = "Description must be no more than 50 characters in length.")
    private String description;
    @NotEmpty(message = "You must supply a value for Address.")
    @Length(max = 50, message = "Address must be no more than 50 characters in length.")
    private String address;
    @NotNull(message = "You must supply a value for Latitude.")
    private BigDecimal latitude;
    @NotNull(message = "You must supply a value for Longitude.")
    private BigDecimal longitude;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.locationId;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.address);
        hash = 71 * hash + Objects.hashCode(this.latitude);
        hash = 71 * hash + Objects.hashCode(this.longitude);
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
        final Location other = (Location) obj;
        return true;
    }
    
    
}
