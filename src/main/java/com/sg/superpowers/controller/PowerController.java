package com.sg.superpowers.controller;

import com.sg.superpowers.dao.SupersDao;
import com.sg.superpowers.model.Location;
import com.sg.superpowers.model.Organization;
import com.sg.superpowers.model.Sightings;
import com.sg.superpowers.model.Supers;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PowerController {
    SupersDao dao;
        
    public PowerController(SupersDao dao) {
        this.dao = dao;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayHomePage(Model model) {
        model.addAttribute("sightings",dao.getRecentSightingsbyDate());
        return "index";
    }
    
    @RequestMapping(value = "/displaySupersPage", method = RequestMethod.GET)
    public String displaySupersPage(Model model) {
        // Get all the Contacts from the DAO
        List<Supers> supersList = dao.getAllSupers();
        List<Organization> organizationList = dao.getAllOrganizations();

        // Put the List of Contacts on the Model
        model.addAttribute("supersList", supersList);
        model.addAttribute("organizationList",organizationList);

        // Return the logical name of our View component
        return "supers";
    }
    
    @RequestMapping(value = "/createSuper", method = RequestMethod.POST)
    public String createSuper(HttpServletRequest request) {

        Supers supers = new Supers();
        supers.setName(request.getParameter("name"));
        supers.setBio(request.getParameter("bio"));
        supers.setPower(request.getParameter("power"));
        String[] organizationList = request.getParameterValues("Organization");
        List<Organization> superOrganizations = new ArrayList<>();
        for(int i = 0;i < organizationList.length;i++) {
            superOrganizations.add(dao.getOrganizationbyId(Integer.parseInt(organizationList[i])));
        }
        supers.setOrganizations(superOrganizations);
        dao.addSuper(supers);

        return "redirect:displaySupersPage";
    }
    @RequestMapping(value = "/displaySupersDetails", method = RequestMethod.GET)
    public String displaySupersDetails(HttpServletRequest request, Model model) {
        String supersIdParameter = request.getParameter("supersId");
        int supersId = Integer.parseInt(supersIdParameter);

        Supers supers = dao.getSuperbyId(supersId);

        model.addAttribute("supers", supers);

        return "SupersDetails";
    }
    
    @RequestMapping(value = "/deleteSuper", method = RequestMethod.GET)
    public String deleteSuper(HttpServletRequest request) {
        String supersIdParameter = request.getParameter("supersId");
        int supersId = Integer.parseInt(supersIdParameter);
        dao.deleteSuper(supersId);
        return "redirect:displaySupersPage";
    }
    
    @RequestMapping(value = "/displayEditSupersPage", method = RequestMethod.GET)
    public String displayEditSupersPage(HttpServletRequest request, Model model) {
        String supersIdParameter = request.getParameter("supersId");
        int supersId = Integer.parseInt(supersIdParameter);
        Supers supers = dao.getSuperbyId(supersId);
        List<Organization> organizationList = dao.getAllOrganizations();
        
        model.addAttribute("organizationList",organizationList);
        model.addAttribute("supers", supers);
        return "editSupers";
    }
    
    @RequestMapping(value = "/editSupers", method = RequestMethod.POST)
    public String editSupers(@Valid @ModelAttribute("supersList") Supers supers, HttpServletRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return "editSupers";
        }
        
        String[] organizationList = request.getParameterValues("Organization");
        List<Organization> superOrganizations = new ArrayList<>();
        if(organizationList != null) {
            for(int i = 0;i < organizationList.length;i++) {
                superOrganizations.add(dao.getOrganizationbyId(Integer.parseInt(organizationList[i])));
            }
        }
        supers.setOrganizations(superOrganizations);

        dao.updateSuper(supers);

        return "redirect:displaySupersPage";
    }
    
    @RequestMapping(value = "/displayOrganizationsPage", method = RequestMethod.GET)
    public String displayOrganizationsPage(Model model) {
        // Get all the Contacts from the DAO
        List<Organization> organizationList = dao.getAllOrganizations();

        // Put the List of Contacts on the Model
        model.addAttribute("organizationList", organizationList);

        // Return the logical name of our View component
        return "organizations";
    }
    
    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public String createOrganization(HttpServletRequest request) {
        // grab the incoming values from the form and create a new Contact
        // object
        Organization organization = new Organization();
        //supers.setName(request.getParameter("name"));
        organization.setAddress(request.getParameter("address"));
        organization.setContact(request.getParameter("contact"));
        organization.setDescription(request.getParameter("description"));
        organization.setName(request.getParameter("name"));
        
        // persist the new Contact
        dao.addOrganization(organization);

        // we don't want to forward to a View component - we want to
        // redirect to the endpoint that displays the Contacts Page
        // so it can display the new Contact in the table.
        return "redirect:displayOrganizationsPage";
    }
    @RequestMapping(value = "/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);

        Organization organization = dao.getOrganizationbyId(organizationId);

        model.addAttribute("organization",organization);

        return "OrganizationDetails";
    }
    
    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        dao.deleteOrganization(organizationId);
        return "redirect:displayOrganizationsPage";
    }
    
    @RequestMapping(value = "/displayEditOrganization", method = RequestMethod.GET)
    public String displayEditOrganization(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        Organization organization = dao.getOrganizationbyId(organizationId);
        model.addAttribute("organization", organization);
        return "editOrganizations";
    }
    
    @RequestMapping(value = "/editOrganizations", method = RequestMethod.POST)
    public String editOrganizations(@Valid @ModelAttribute("organization") Organization organization, BindingResult result) {

        if (result.hasErrors()) {
            return "editOrganizations";
        }

        dao.updateOrganization(organization);

        return "redirect:displayOrganizationsPage";
    }
    
    @RequestMapping(value = "/displayLocationsPage", method = RequestMethod.GET)
    public String displayLocationsPage(Model model) {
        // Get all the Contacts from the DAO
        List<Location> locationList = dao.getAllLocations();

        // Put the List of Contacts on the Model
        model.addAttribute("locationList", locationList);

        // Return the logical name of our View component
        return "locations";
    }
    
    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createLocation(HttpServletRequest request) {
        // grab the incoming values from the form and create a new Contact
        // object
        Location location = new Location();
        //supers.setName(request.getParameter("name"));
        location.setName(request.getParameter("name"));
        location.setAddress(request.getParameter("address"));
        location.setDescription(request.getParameter("description"));
        location.setLatitude(new BigDecimal (request.getParameter("latitude")));
        location.setLongitude(new BigDecimal (request.getParameter("longitude")));
        
        
        // persist the new Contact
        dao.addLocation(location);

        // we don't want to forward to a View component - we want to
        // redirect to the endpoint that displays the Contacts Page
        // so it can display the new Contact in the table.
        return "redirect:displayLocationsPage";
    }
    @RequestMapping(value = "/displayLocationsDetails", method = RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);

        Location location = dao.getLocationbyId(locationId);

        model.addAttribute("location",location);

        return "LocationDetails";
    }
    
    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);
        dao.deleteLocation(locationId);
        return "redirect:displayLocationsPage";
    }
    
    @RequestMapping(value = "/displayEditLocation", method = RequestMethod.GET)
    public String displayEditLocation(HttpServletRequest request, Model model) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);
        Location location = dao.getLocationbyId(locationId);
        model.addAttribute("location", location);
        return "editLocations";
    }
    
    @RequestMapping(value = "/editLocations", method = RequestMethod.POST)
    public String editLocations(@Valid @ModelAttribute("location") Location location, BindingResult result) {

        if (result.hasErrors()) {
            return "editLocations";
        }
        
        

        dao.updateLocation(location);

        return "redirect:displayLocationsPage";
    }
    
   @RequestMapping(value = "/displaySightingsPage", method = RequestMethod.GET)
    public String displaySightingsPage(Model model) {
        // Get all the Contacts from the DAO
        List<Sightings> sightingsList = dao.getAllSightings();

        // Put the List of Contacts on the Model
        model.addAttribute("sightingsList", sightingsList);
        model.addAttribute("supersList",dao.getAllSupers());
        model.addAttribute("locationList", dao.getAllLocations());

        // Return the logical name of our View component
        return "sightings";
    }
    
    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createSighting(HttpServletRequest request) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // grab the incoming values from the form and create a new Contact
        // object
        Sightings sighting = new Sightings();
        //supers.setName(request.getParameter("name"));
        sighting.setDate(LocalDate.parse(request.getParameter("date"),dtf));
        sighting.setLocation(dao.getLocationbyId(Integer.parseInt(request.getParameter("location"))));
        String[] supersList = request.getParameterValues("Supers");
        List<Supers> superSightings = new ArrayList<>();
        for(int i = 0;i < supersList.length;i++) {
            superSightings.add(dao.getSuperbyId(Integer.parseInt(supersList[i])));
        }
        sighting.setSupers(superSightings);
        //sighting.setSupers(request.getParameterValues());
        
        // persist the new Contact
        dao.addSighting(sighting);

        // we don't want to forward to a View component - we want to
        // redirect to the endpoint that displays the Contacts Page
        // so it can display the new Contact in the table.
        return "redirect:/";
    }
    @RequestMapping(value = "/displaySightingsDetails", method = RequestMethod.GET)
    public String displaySightingsDetails(HttpServletRequest request, Model model) {
        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);

        Sightings sighting = dao.getSightingbyId(sightingId);

        model.addAttribute("sighting",sighting);

        return "sightingDetails";
    }
    
    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {
        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);
        dao.deleteSighting(sightingId);
        return "redirect:/";
    }
    
    @RequestMapping(value = "/displayEditSighting", method = RequestMethod.GET)
    public String displayEditSighting(HttpServletRequest request, Model model) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);
        Sightings sighting = dao.getSightingbyId(sightingId);
        model.addAttribute("date",sighting.getDate());
        model.addAttribute("location", dao.getAllLocations());
        model.addAttribute("supers",dao.getAllSupers());
        model.addAttribute("sighting", sighting);
        return "editSightings";
    }
    
    @RequestMapping(value = "/editSightings", method = RequestMethod.POST)
    public String editSightings(@Valid @ModelAttribute("sighting") Sightings sighting,HttpServletRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return "editSightings";
        }
        
        sighting.setLocation(dao.getLocationbyId(Integer.parseInt(request.getParameter("locationId"))));
        
        String[] supersList = request.getParameterValues("supersId");
        List<Supers> superSightings = new ArrayList<>();
        for(int i = 0;i < supersList.length;i++) {
            superSightings.add(dao.getSuperbyId(Integer.parseInt(supersList[i])));
        }
        sighting.setSupers(superSightings);

        dao.updateSighting(sighting);

        return "redirect:/";
    }
}
