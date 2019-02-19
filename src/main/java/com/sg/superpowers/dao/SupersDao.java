/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superpowers.dao;

import com.sg.superpowers.model.Location;
import com.sg.superpowers.model.Organization;
import com.sg.superpowers.model.Sightings;
import com.sg.superpowers.model.Supers;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ashwinsridhar
 */
public interface SupersDao {
   public void addSuper(Supers supers);
   
   public void deleteSuper(int superId);
   
   public void updateSuper(Supers supers);
   
   public Supers getSuperbyId(int id);
   
   public List<Supers> getAllSupers();
   
   public List<Supers> getAllSupersbyLocationId(int locationId);
   
   public List<Supers> getAllSupersbyOrganizationId(int organizationId);
   
   public void addLocation(Location location);
   
   public void deleteLocation(int locationId);
   
   public void updateLocation(Location location);
   
   public Location getLocationbyId(int id);
   
   public List<Location> getAllLocations();
   
   public List<Location> getAllLocationsbySuperId(int superId);
   
   public void addOrganization(Organization organization);
   
   public void deleteOrganization(int organizationId);
   
   public void updateOrganization(Organization organization);
   
   public Organization getOrganizationbyId(int id);
   
   public List<Organization> getAllOrganizations();
   
   public void addSighting(Sightings sighting);
   
   public void deleteSighting(int sightingId);
   
   public void updateSighting(Sightings sighting);
   
   public Sightings getSightingbyId(int id);
   
   public List<Sightings> getAllSightings();
   
   public List<Sightings> getAllSightingsbyDate(LocalDate date);
   
   public List<Sightings> getRecentSightingsbyDate();
}
