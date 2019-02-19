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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ashwinsridhar
 */
public class SupersDaoJdbcTemplateImpl implements SupersDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_SUPER
        = "insert into supers(name,bio,power) values (?,?,?)";
    
    private static final String SQL_INSERT_SUPERS_ORGANIZATIONS
        = "insert into supers_organizations (supers_id, organizations_id) values(?, ?)";
    
    private static final String SQL_DELETE_SUPER
        = "delete from supers where supers_Id = ?";
    
    private static final String SQL_DELETE_SUPERS_ORGANIZATIONS
        = "delete from supers_organizations where supers_id = ?";
    
    private static final String SQL_DELETE_SUPERS_SIGHTINGS
        = "delete from supers_sightings where supers_id = ?";
    
    private static final String SQL_UPDATE_SUPER
        = "update supers set name = ?, bio = ?, power = ? where supers_id = ?";
    
    private static final String SQL_SELECT_SUPER
        = "select * from supers where supers_id = ?";
    
    private static final String SQL_SELECT_ORGANIZATIONS_BY_SUPER_ID
        = "select * from organizations org join supers_organizations suor on org.organizations_id = suor.organizations_id "
            + "where suor.supers_id = ?";
    
    private static final String SQL_SELECT_SUPERS_BY_LOCATION_ID
        = "select * from sightings si join supers_sightings susi on si.sightings_id = susi.sightings_id "
            + "join supers su on susi.supers_id = su.supers_id "
            + "where si.locations_Id = ?";
    private static final String SQL_SELECT_SUPERS_BY_SIGHTINGS_ID
        = "select * from sightings si join supers_sightings susi on si.sightings_id = susi.sightings_id "
            + "join supers su on susi.supers_id = su.supers_id "
            + "where si.sightings_Id = ?";
    
    private static final String SQL_SELECT_ALL_SUPERS
        = "select * from supers";
    
    private static final class SuperMapper implements RowMapper<Supers> {
        @Override
        public Supers mapRow(ResultSet rs, int i) throws SQLException {
            Supers su = new Supers();
            su.setSupersId(rs.getInt("supers_id"));
            su.setBio(rs.getString("bio"));
            su.setName(rs.getString("name"));
            su.setPower(rs.getString("power"));
            return su;
        }
    }
    
    private void insertSupersOrganization(Supers supers) {
        final int supersId = supers.getSupersId();
        final List<Organization> organizations = supers.getOrganizations();
        
        for(Organization currentOrganization : organizations) {
            jdbcTemplate.update(SQL_INSERT_SUPERS_ORGANIZATIONS,
                    supersId,currentOrganization.getOrganizationId());
        }
    }
    
    private List<Organization> findOrganizationsforSuper(Supers supers) {
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_SUPER_ID,
                                    new OrganizationMapper(),supers.getSupersId());
    }
    
    private List<Location> findLocationsforSuper(Location location) {
        return jdbcTemplate.query(SQL_SELECT_SUPERS_BY_LOCATION_ID,
                                    new LocationMapper(),location.getLocationId());
    }
    
    private List<Supers> associateOrganizationsWithSupers(List<Supers> superList) {
        for (Supers currentSuper : superList) {
            currentSuper.setOrganizations(findOrganizationsforSuper(currentSuper));
        }
        return superList;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuper(Supers supers) {
        // first insert into books table and get newly generated book_id
        jdbcTemplate.update(SQL_INSERT_SUPER,
                supers.getName(),
                supers.getBio(),
                supers.getPower());
        supers.setSupersId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", 
                                                   Integer.class));
        // now update the books_authors table
        insertSupersOrganization(supers);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuper(int superId) {
        jdbcTemplate.update(SQL_DELETE_SUPERS_SIGHTINGS,superId);
        // delete books_authors relationship for this book
        jdbcTemplate.update(SQL_DELETE_SUPERS_ORGANIZATIONS, superId);
        // delete book
        jdbcTemplate.update(SQL_DELETE_SUPER, superId);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuper(Supers supers) {
        // update books table
        jdbcTemplate.update(SQL_UPDATE_SUPER,
                supers.getName(),
                supers.getBio(),
                supers.getPower(),
               // supers.getOrganizations(),
                supers.getSupersId());
        // delete books_authors relationships and then reset them
        jdbcTemplate.update(SQL_DELETE_SUPERS_ORGANIZATIONS, supers.getSupersId());
        insertSupersOrganization(supers);
    }
    
    @Override
    public Supers getSuperbyId(int id) {
        try {
            // get the properties from the books table
            Supers supers = jdbcTemplate.queryForObject(SQL_SELECT_SUPER, 
                                                    new SuperMapper(), 
                                                    id);
            supers.setOrganizations(findOrganizationsforSuper(supers));
            return supers;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Supers> getAllSupersbyOrganizationId(int organizationId) {
        // get the books written/cowritten by this author
        List<Supers> superList = 
                jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_SUPER_ID, 
                                   new SuperMapper(), 
                                   organizationId);
        // set the publisher and list of Authors for each book
        return associateOrganizationsWithSupers(superList);
    }
    
    @Override
    public List<Supers> getAllSupersbyLocationId(int locationId) {
        // get the books written/cowritten by this author
        List<Supers> superList = 
                jdbcTemplate.query(SQL_SELECT_LOCATIONS_BY_SUPER_ID, 
                                   new SuperMapper(), 
                                   locationId);
        // set the publisher and list of Authors for each book
        return associateOrganizationsWithSupers(superList);
    }
    
    @Override
    public List<Supers> getAllSupers() {
        // get all the books
        List<Supers> superList = jdbcTemplate.query(SQL_SELECT_ALL_SUPERS, 
                                                 new SuperMapper());
        // set the publisher and list of Authors for each book
        return associateOrganizationsWithSupers(superList);
    }
    
    private static final String SQL_INSERT_LOCATION
        = "insert into locations (address,description,latitude,longitude,name) values (?,?,?,?,?)";
    
    private static final String SQL_DELETE_LOCATION
        = "delete from locations where locations_id = ?";
    
    private static final String SQL_DELETE_SIGHTING_BY_LOCATION_ID
        = "delete from sightings where sightings.locations_id = ?";
    
    private static final String SQL_DELETE_SUPERS_SIGHTINGS_BY_LOCATION
        = "delete susi from supers_sightings susi join sightings si on susi.sightings_id = si.sightings_id "
            + "where locations_id = ?";
    
    private static final String SQL_UPDATE_LOCATION
        = "update locations set name = ?, description = ?, address = ?, latitude = ?, longitude = ? where locations_id = ?";
    
    private static final String SQL_SELECT_LOCATION
        = "select * from locations where locations_id = ?";
    
    private static final String SQL_SELECT_LOCATIONS_BY_SUPER_ID
        = "select * from supers su join supers_sightings susi on su.supers_id = susi.supers_id " 
            + "join sightings si on susi.sightings_id = si.sightings_id "
            + "join locations lo on si.locations_id = lo.locations_id "
            + "where su.supers_Id = ?";
    
    private static final String SQL_SELECT_ALL_LOCATIONS
        = "select * from locations";
    
    private static final class LocationMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location lo = new Location();
            lo.setLocationId(rs.getInt("locations_id"));
            lo.setAddress(rs.getString("address"));
            lo.setDescription(rs.getString("description"));
            lo.setLatitude(rs.getBigDecimal("latitude"));
            lo.setLongitude(rs.getBigDecimal("longitude"));
            lo.setName(rs.getString("name"));
            return lo;
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getAddress(),
                location.getDescription(),
                location.getLatitude(),
                location.getLongitude(),
                location.getName());

        int locationId = 
                jdbcTemplate.queryForObject("select LAST_INSERT_ID()", 
                                             Integer.class);

        location.setLocationId(locationId);
    }
    
    @Override
    public void deleteLocation(int locationId) {
        jdbcTemplate.update(SQL_DELETE_SUPERS_SIGHTINGS_BY_LOCATION,locationId);
        jdbcTemplate.update(SQL_DELETE_SIGHTING_BY_LOCATION_ID,locationId);
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
    }
    
    
    
    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                    location.getName(),
                    location.getDescription(),
                    location.getAddress(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getLocationId());
    }
    
    @Override
    public Location getLocationbyId(int locationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION, 
                                               new LocationMapper(), 
                                               locationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS, 
                                  new LocationMapper());
    }
    
    @Override
    public List<Location> getAllLocationsbySuperId(int superId) {
        List<Location> locations = jdbcTemplate.query(SQL_SELECT_LOCATIONS_BY_SUPER_ID, new LocationMapper(),superId);
        return locations;
    }
    
    
    
    private static final String SQL_INSERT_ORGANIZATION
        = "insert into organizations (address,contact,description,name) values (?,?,?,?)";
    
    private static final String SQL_DELETE_ORGANIZATION
        = "delete from organizations where organizations_id = ?";
    
    private static final String SQL_DELETE_SUPER_ORGANIZATION
       = "delete from supers_organizations where organizations_id = ?";
    
    private static final String SQL_UPDATE_ORGANIZATION
        = "update organizations set address = ?, contact = ?, description = ?, name = ? where organizations_id = ?";
    
    private static final String SQL_SELECT_ORGANIZATION
        = "select * from organizations where organizations_id = ?";
    
    private static final String SQL_SELECT_ALL_ORGANIZATIONS
        = "select * from organizations";
    
    private static final class OrganizationMapper implements RowMapper<Organization> {
        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization or = new Organization();
            or.setOrganizationId(rs.getInt("organizations_id"));
            or.setAddress(rs.getString("address"));
            or.setContact(rs.getString("contact"));
            or.setDescription(rs.getString("description"));
            or.setName(rs.getString("name"));
            return or;
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getAddress(),
                organization.getContact(),
                organization.getDescription(),
                organization.getName());

         
                organization.setOrganizationId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", 
                                             Integer.class));

    }
    
    @Override
    public void deleteOrganization(int organizationId) {
        jdbcTemplate.update(SQL_DELETE_SUPER_ORGANIZATION,organizationId);
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }
    
    @Override
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                    organization.getAddress(),
                    organization.getContact(),
                    organization.getDescription(),
                    organization.getName(),
                    organization.getOrganizationId());
    }
    
    @Override
    public Organization getOrganizationbyId(int organizationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION, 
                                               new OrganizationMapper(), 
                                               organizationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, 
                                  new OrganizationMapper());
    }
    
    private static final String SQL_INSERT_SIGHTING
        = "insert into sightings (locations_id,date) values (?,?)";
    
    private static final String SQL_INSERT_SIGHTINGS_SUPERS
        = "insert into supers_sightings(supers_id,sightings_id) values(?,?)";
    
    private static final String SQL_DELETE_SIGHTING
        = "delete from sightings where sightings_id = ?";
    
    private static final String SQL_DELETE_SIGHTINGS_SUPERS
        = "delete from supers_sightings where sightings_id = ?";
    
    private static final String SQL_UPDATE_SIGHTING
        = "update sightings set locations_id = ?, date = ? where sightings_id = ?";
    
    private static final String SQL_SELECT_SIGHTING
        = "select * from sightings where sightings_id = ?";
    
    private static final String SQL_SELECT_LOCATION_BY_SIGHTING_ID
        = "select * from sightings si join locations lo on si.locations_id = lo.locations_id "
            + "where si.sightings_id = ?";
    
    private static final String SQL_SELECT_ALL_SIGHTINGS
        = "select * from sightings";
    
    private static final String SQL_SELECT_ALL_SIGHTINGS_BY_DATE
        = "select * from sightings si where si.date = ?";
    
    private static final String SQL_SELECT_TEN_MOST_RECENT_SIGHTINGS
        = "select * from sightings si order by si.date DESC limit 3";
    
    
    private static final class SightingMapper implements RowMapper<Sightings> {
        @Override
        public Sightings mapRow(ResultSet rs, int i) throws SQLException {
            Sightings si = new Sightings();
            si.setSightingId(rs.getInt("sightings_id"));
            si.setDate(rs.getTimestamp("date").toLocalDateTime().toLocalDate());
            return si;
        }
    }
    
    private void insertSightingsSupers(Sightings sightings) {
        final int sightingsId = sightings.getSightingId();
        final List<Supers> supers = sightings.getSupers();
        
        for(Supers currentSuper : supers) {
            jdbcTemplate.update(SQL_INSERT_SIGHTINGS_SUPERS,currentSuper.getSupersId(),
                    sightingsId);
        }
    }
    
    private List<Supers> findSupersforSighting(Sightings sightings) {
        return jdbcTemplate.query(SQL_SELECT_SUPERS_BY_SIGHTINGS_ID,
                                  new SuperMapper(),
                                  sightings.getSightingId());
    }
    
    private Location findLocationforSighting(Sightings sightings) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING_ID,
                                           new LocationMapper(),
                                           sightings.getSightingId());
    }
    
    private List<Sightings> 
            associateSuperAndLocationWithSighting(List<Sightings> sightingsList) {
        // set the complete list of author ids for each book
        for (Sightings currentSightings : sightingsList) {
            // add Authors to current book
            currentSightings.setSupers(findSupersforSighting(currentSightings));
            // add the Publisher to current book
            currentSightings.setLocation(findLocationforSighting(currentSightings));
        }
        return sightingsList;
    }
            
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sightings sighting) {
        // first insert into books table and get newly generated book_id
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getLocation().getLocationId(),
                sighting.getDate().toString());
                
        sighting.setSightingId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", 
                                                   Integer.class));
        // now update the books_authors table
        insertSightingsSupers(sighting);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSighting(int sightingId) {
        // delete books_authors relationship for this book
        
        jdbcTemplate.update(SQL_DELETE_SIGHTINGS_SUPERS, sightingId);
        // delete book
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSighting(Sightings sighting) {
        // update books table
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sighting.getLocation().getLocationId(),
                sighting.getDate().toString(),
                sighting.getSightingId());
        // delete books_authors relationships and then reset them
        jdbcTemplate.update(SQL_DELETE_SIGHTINGS_SUPERS, sighting.getSightingId());
        insertSightingsSupers(sighting);
    }
    
    @Override
    public Sightings getSightingbyId(int id) {
        try {
            // get the properties from the books table
            Sightings sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING, 
                                                    new SightingMapper(), 
                                                    id);
            // get the Authors for this book and set list on the book
            sighting.setSupers(findSupersforSighting(sighting));
            // get the Publisher for this book
            sighting.setLocation(findLocationforSighting(sighting));
            return sighting;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Sightings> getAllSightingsbyDate(LocalDate date) {
        List<Sightings> sightingsList = 
            jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_BY_DATE, 
                               new SightingMapper(), 
                               date);
        // set the publisher and list of Authors for each book
        return associateSuperAndLocationWithSighting(sightingsList);
    }
    
    @Override
    public List<Sightings> getAllSightings() {
        // get all the books
        List<Sightings> sightingsList = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, 
                                                 new SightingMapper());
        // set the publisher and list of Authors for each book
        return associateSuperAndLocationWithSighting(sightingsList);
    }
    
    @Override
    public List<Sightings> getRecentSightingsbyDate() {
        try {
            // get the properties from the books table
            List<Sightings> sightings = jdbcTemplate.query(SQL_SELECT_TEN_MOST_RECENT_SIGHTINGS, 
                                                    new SightingMapper());
            // get the Authors for this book and set list on the book
            for(Sightings currentSighting : sightings) {
                currentSighting.setSupers(findSupersforSighting(currentSighting));
                // get the Publisher for this book
                currentSighting.setLocation(findLocationforSighting(currentSighting));
            }
            return sightings;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
