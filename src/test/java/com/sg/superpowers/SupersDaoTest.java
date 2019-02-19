/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superpowers;

import com.sg.superpowers.dao.SupersDao;
import com.sg.superpowers.model.Location;
import com.sg.superpowers.model.Organization;
import com.sg.superpowers.model.Sightings;
import com.sg.superpowers.model.Supers;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ashwinsridhar
 */
public class SupersDaoTest {
    SupersDao dao;

    public SupersDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
     ApplicationContext ctx
      = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        dao = ctx.getBean("SupersDao", SupersDao.class);

        // delete all locations
        List<Location> locations = dao.getAllLocations();
        for (Location currentLocation : locations) {
            dao.deleteLocation(currentLocation.getLocationId());
        }
        //delete all organizations
        List<Organization> organizations = dao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            dao.deleteOrganization(currentOrganization.getOrganizationId());
        }
        // delete all sightings
        List<Sightings> sightings = dao.getAllSightings();
        for (Sightings currentSightings : sightings) {
            dao.deleteSighting(currentSightings.getSightingId());
        }
        // delete all supers
        List<Supers> supers = dao.getAllSupers();
        for (Supers currentSupers : supers) {
            dao.deleteSuper(currentSupers.getSupersId());
        }
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testaddLocation() {
        Location location = new Location();
        location.setAddress("Pine Ridge Drive");
        location.setDescription("Bananas");
        location.setName("Orange City");
        location.setLatitude(new BigDecimal(43.12));
        location.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location);
        
        Location fromDao = dao.getLocationbyId(location.getLocationId());
        assertEquals(fromDao,location);
    }
    
    @Test
    public void testdeleteLocation() {
        Location location = new Location();
        location.setAddress("Pine Ridge Drive");
        location.setDescription("Bananas");
        location.setName("Orange City");
        location.setLatitude(new BigDecimal(43.12));
        location.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location);
        
        Location fromDao = dao.getLocationbyId(location.getLocationId());
        assertEquals(fromDao,location);
        
        dao.deleteLocation(location.getLocationId());
        assertNull(dao.getLocationbyId(location.getLocationId()));
    }
    
    @Test
    public void testUpdateLocation() {
        Location lo = new Location();
        lo.setAddress("Pine Ridge Drive");
        lo.setDescription("Bananas");
        lo.setName("Orange City");
        lo.setLatitude(new BigDecimal(43.12));
        lo.setLongitude(new BigDecimal(153.23));
        dao.addLocation(lo);
        lo.setName("Banana City");
        dao.updateLocation(lo);
        Location fromDb = dao.getLocationbyId(lo.getLocationId());
        assertEquals(fromDb,lo);
    }
    
    @Test
    public void testGetallLocations() {
        Location l1 = new Location();
        l1.setAddress("Pine Ridge Drive");
        l1.setDescription("Bananas");
        l1.setName("Orange City");
        l1.setLatitude(new BigDecimal(43.12));
        l1.setLongitude(new BigDecimal(153.23));
        dao.addLocation(l1);
        
        Location l2 = new Location();
        l2.setAddress("Rine Pidge Drive");
        l2.setDescription("Oranges");
        l2.setName("Banana City");
        l2.setLatitude(new BigDecimal(43.12));
        l2.setLongitude(new BigDecimal(143.23));
        dao.addLocation(l2);
        
        List<Location> locationList = dao.getAllLocations();
        assertEquals(2,locationList.size());
    }
    
    @Test
    public void testGetAllLocationsbySuperId() {
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        dao.addSuper(supers);
        
        Location location1 = new Location();
        location1.setAddress("Pine Ridge Drive");
        location1.setDescription("Bananas");
        location1.setName("Orange City");
        location1.setLatitude(new BigDecimal(43.12));
        location1.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location1);
        
        Location location2 = new Location();
        location2.setAddress("Rine Pidge Drive");
        location2.setDescription("Organges");
        location2.setName("Banana City");
        location2.setLatitude(new BigDecimal(43.12));
        location2.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location2);
        
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting1.setLocation(location1);
        
        List<Supers> superList = new ArrayList<>();
        superList.add(supers);
        sighting1.setSupers(superList);
        
        dao.addSighting(sighting1);
        
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.parse("2010-02-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting2.setLocation(location2);
        sighting2.setSupers(superList);
        
        dao.addSighting(sighting2);
        
        List<Location> heroLocations = dao.getAllLocationsbySuperId(supers.getSupersId());
        assertEquals(2,heroLocations.size());
    }
    
    @Test
    public void testaddOrganization() {
        Organization organization = new Organization();
        organization.setAddress("Pine Ridge Drive");
        organization.setDescription("Bananas");
        organization.setName("Orange City");
        organization.setContact("555-1234");
        
        dao.addOrganization(organization);
        
        Organization fromDao = dao.getOrganizationbyId(organization.getOrganizationId());
        assertEquals(fromDao,organization);
    }
    
    @Test
    public void testdeleteOrganization() {
        Organization organization = new Organization();
        organization.setAddress("Pine Ridge Drive");
        organization.setDescription("Bananas");
        organization.setName("Orange City");
        organization.setContact("555-1234");
        
        dao.addOrganization(organization);
        
        Organization fromDao = dao.getOrganizationbyId(organization.getOrganizationId());
        assertEquals(fromDao,organization);
        
        dao.deleteOrganization(organization.getOrganizationId());
        assertNull(dao.getOrganizationbyId(organization.getOrganizationId()));
    }
    
    @Test
    public void testUpdateOrganization() {
        Organization or = new Organization();
        or.setAddress("Pine Ridge Drive");
        or.setDescription("Bananas");
        or.setName("Orange City");
        or.setContact("555-1234");
        
        dao.addOrganization(or);
        
        or.setName("Banana City");
        dao.updateOrganization(or);
        
        Organization fromDb = dao.getOrganizationbyId(or.getOrganizationId());
        assertEquals(fromDb,or);
    }
    
    @Test
    public void testGetallOrganizations() {
        Organization o1 = new Organization();
        o1.setAddress("Pine Ridge Drive");
        o1.setDescription("Bananas");
        o1.setName("Orange City");
        o1.setContact("555-1234");
        dao.addOrganization(o1);
        
        Organization o2 = new Organization();
        o2.setAddress("Pine Ridge Drive");
        o2.setDescription("Bananas");
        o2.setName("Orange City");
        o2.setContact("555-1234");
        dao.addOrganization(o2);
        
        List<Organization> organizationList = dao.getAllOrganizations();
        assertEquals(2,organizationList.size());
    }
    
    @Test
    public void testaddSightings() {
        Location location = new Location();
        location.setAddress("Pine Ridge Drive");
        location.setDescription("Bananas");
        location.setName("Orange City");
        location.setLatitude(new BigDecimal(43.12));
        location.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location);
        
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        dao.addSuper(supers);
        
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting.setLocation(location);
        
        List<Supers> superList = new ArrayList<>();
        superList.add(supers);
        sighting.setSupers(superList);
        
        dao.addSighting(sighting);
        
        Sightings fromDao = dao.getSightingbyId(sighting.getSightingId());
        assertEquals(fromDao,sighting);
    }
    
    @Test
    public void testdeleteSightings() {
        Location location = new Location();
        location.setAddress("Pine Ridge Drive");
        location.setDescription("Bananas");
        location.setName("Orange City");
        location.setLatitude(new BigDecimal(43.12));
        location.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location);
        
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        dao.addSuper(supers);
        
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting.setLocation(location);
        
        List<Supers> superList = new ArrayList<>();
        superList.add(supers);
        sighting.setSupers(superList);
        
        dao.addSighting(sighting);
        
        Sightings fromDao = dao.getSightingbyId(sighting.getSightingId());
        assertEquals(fromDao,sighting);
        dao.deleteSighting(sighting.getSightingId());
        assertNull(dao.getSightingbyId(sighting.getSightingId()));
    }
    
    @Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setAddress("Pine Ridge Drive");
        location.setDescription("Bananas");
        location.setName("Orange City");
        location.setLatitude(new BigDecimal(43.12));
        location.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location);
        
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        dao.addSuper(supers);
        
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting.setLocation(location);
        
        List<Supers> superList = new ArrayList<>();
        superList.add(supers);
        sighting.setSupers(superList);
        
        dao.addSighting(sighting);
        
        sighting.setDate(LocalDate.parse("2010-06-01", 
                         DateTimeFormatter.ISO_DATE));
        dao.updateSighting(sighting);
        
        Sightings fromDb = dao.getSightingbyId(sighting.getSightingId());
        assertEquals(fromDb,sighting);
    }
    
    @Test
    public void testGetallSightings() {
        Location location1 = new Location();
        location1.setAddress("Pine Ridge Drive");
        location1.setDescription("Bananas");
        location1.setName("Orange City");
        location1.setLatitude(new BigDecimal(43.12));
        location1.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location1);
        
        Supers supers1 = new Supers();
        supers1.setBio("A talking towel");
        supers1.setName("Towelie");
        supers1.setPower("Getting high");
        
        dao.addSuper(supers1);
        
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting1.setLocation(location1);
        
        List<Supers> superList1 = new ArrayList<>();
        superList1.add(supers1);
        sighting1.setSupers(superList1);
        
        dao.addSighting(sighting1);
        
        Location location2 = new Location();
        location2.setAddress("Rine Pidge Drive");
        location2.setDescription("Oranges");
        location2.setName("Banana City");
        location2.setLatitude(new BigDecimal(53.12));
        location2.setLongitude(new BigDecimal(143.23));
        
        dao.addLocation(location2);
        
        Supers supers2 = new Supers();
        supers2.setBio("A talking towel");
        supers2.setName("Towelie");
        supers2.setPower("Getting high");
        
        dao.addSuper(supers2);
        
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting2.setLocation(location2);
        
        List<Supers> superList2 = new ArrayList<>();
        superList2.add(supers2);
        sighting2.setSupers(superList2);
        
        dao.addSighting(sighting2);
        
        List<Sightings> sightingList = dao.getAllSightings();
        assertEquals(2,sightingList.size());
    }
    
    @Test
    public void testgetAllSightingsbyDate() {
        Location location1 = new Location();
        location1.setAddress("Pine Ridge Drive");
        location1.setDescription("Bananas");
        location1.setName("Orange City");
        location1.setLatitude(new BigDecimal(43.12));
        location1.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location1);
        
        Supers supers1 = new Supers();
        supers1.setBio("A talking towel");
        supers1.setName("Towelie");
        supers1.setPower("Getting high");
        
        dao.addSuper(supers1);
        
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting1.setLocation(location1);
        
        List<Supers> superList1 = new ArrayList<>();
        superList1.add(supers1);
        sighting1.setSupers(superList1);
        
        dao.addSighting(sighting1);
        
        Location location2 = new Location();
        location2.setAddress("Rine Pidge Drive");
        location2.setDescription("Oranges");
        location2.setName("Banana City");
        location2.setLatitude(new BigDecimal(53.12));
        location2.setLongitude(new BigDecimal(143.23));
        
        dao.addLocation(location2);
        
        Supers supers2 = new Supers();
        supers2.setBio("A talking towel");
        supers2.setName("Towelie");
        supers2.setPower("Getting high");
        
        dao.addSuper(supers2);
        
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting2.setLocation(location2);
        
        List<Supers> superList2 = new ArrayList<>();
        superList2.add(supers2);
        sighting2.setSupers(superList2);
        
        dao.addSighting(sighting2);
        
        List<Sightings> sightingsbyDate = dao.getAllSightingsbyDate(sighting1.getDate());
        assertEquals(2,sightingsbyDate.size());
    }
    
    @Test
    public void testaddSupers() {
        Organization organization = new Organization();
        organization.setAddress("Pine Ridge Drive");
        organization.setDescription("Bananas");
        organization.setName("Orange City");
        organization.setContact("555-1234");
        
        dao.addOrganization(organization);
        
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        supers.setOrganizations(organizations);
        
        dao.addSuper(supers);
        
        Supers fromDao = dao.getSuperbyId(supers.getSupersId());
        assertEquals(fromDao,supers);
    }
    
    public void testdeleteSupers() {
        Organization organization = new Organization();
        organization.setAddress("Pine Ridge Drive");
        organization.setDescription("Bananas");
        organization.setName("Orange City");
        organization.setContact("555-1234");
        
        dao.addOrganization(organization);
        
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        supers.setOrganizations(organizations);
        
        dao.addSuper(supers);
        
        Supers fromDao = dao.getSuperbyId(supers.getSupersId());
        assertEquals(fromDao,supers);
        dao.deleteSuper(supers.getSupersId());
        assertNull(dao.getSightingbyId(supers.getSupersId()));
    }
    
    @Test
    public void testUpdateSupers() {
        Organization organization = new Organization();
        organization.setAddress("Pine Ridge Drive");
        organization.setDescription("Bananas");
        organization.setName("Orange City");
        organization.setContact("555-1234");
        
        dao.addOrganization(organization);
        
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        supers.setOrganizations(organizations);
        
        dao.addSuper(supers);
        
        Supers fromDb = dao.getSuperbyId(supers.getSupersId());
        assertEquals(fromDb,supers);
    }
    
    public void testGetallSupers() {
        Organization organization = new Organization();
        organization.setAddress("Pine Ridge Drive");
        organization.setDescription("Bananas");
        organization.setName("Orange City");
        organization.setContact("555-1234");
        
        dao.addOrganization(organization);
        
        Supers supers = new Supers();
        supers.setBio("A talking towel");
        supers.setName("Towelie");
        supers.setPower("Getting high");
        
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        supers.setOrganizations(organizations);
        
        dao.addSuper(supers);
        supers.setName("Towel");
        dao.updateSuper(supers);
    }
    
    public void testgetAllSupersbyLocationId() {
        Location location = new Location();
        location.setAddress("Pine Ridge Drive");
        location.setDescription("Bananas");
        location.setName("Orange City");
        location.setLatitude(new BigDecimal(43.12));
        location.setLongitude(new BigDecimal(153.23));
        
        dao.addLocation(location);
        
        Supers supers1 = new Supers();
        supers1.setBio("A talking towel");
        supers1.setName("Towelie");
        supers1.setPower("Getting high");
        
        dao.addSuper(supers1);
        
        Supers supers2 = new Supers();
        supers2.setBio("A talking orange");
        supers2.setName("Orange");
        supers2.setPower("Being annoying");
        
        dao.addSuper(supers2);
        
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.parse("2010-01-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting1.setLocation(location);
        
        List<Supers> superList = new ArrayList<>();
        superList.add(supers1);
        sighting1.setSupers(superList);
        
        dao.addSighting(sighting1);
        
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.parse("2010-02-01", 
                         DateTimeFormatter.ISO_DATE));
        sighting2.setLocation(location);
        superList.add(supers2);
        sighting1.setSupers(superList);
        
        dao.addSighting(sighting2);
        
        List<Supers> allLocations = dao.getAllSupersbyLocationId(location.getLocationId());
        assertEquals(2,allLocations.size());
    }
    
    public void testgetAllSupersbyOrganizationId() {
        Organization organization = new Organization();
        organization.setAddress("Pine Ridge Drive");
        organization.setDescription("Bananas");
        organization.setName("Orange City");
        organization.setContact("555-1234");
        
        dao.addOrganization(organization);
        
        Supers supers1 = new Supers();
        supers1.setBio("A talking towel");
        supers1.setName("Towelie");
        supers1.setPower("Getting high");
        
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        supers1.setOrganizations(organizations);
        
        dao.addSuper(supers1);
        
        Supers supers2 = new Supers();
        supers2.setBio("A talking towel");
        supers2.setName("Towelette");
        supers2.setPower("Getting low");
        
        supers2.setOrganizations(organizations);
        
        dao.addSuper(supers2);
        
        List<Supers> allOrganizations = dao.getAllSupersbyOrganizationId(organization.getOrganizationId());
        assertEquals(2,allOrganizations.size());
    }
}
