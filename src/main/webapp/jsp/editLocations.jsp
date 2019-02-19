<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Locations</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Edit Location</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                  <li role="presentation">
                        <a href="${pageContext.request.contextPath}/">
                          Home
                        </a>
                  </li>
                  <li role="presentation">
                      <a href="${pageContext.request.contextPath}/displaySupersPage">
                          Supers
                      </a>
                  </li>
                  <li role="presentation" class = "active">
                      <a href="${pageContext.request.contextPath}/displayLocationsPage">
                          Locations
                      </a>
                  </li>
                  <li role="presentation">
                      <a href="${pageContext.request.contextPath}/displayOrganizationsPage">
                          Organizations
                      </a>
                  </li>
                  <li role="presentation" >
                        <a href="${pageContext.request.contextPath}/displaySightingsPage">
                          Sightings
                        </a>
                  </li>
                </ul>    
            </div>
            <sf:form class="form-horizontal" role="form" modelAttribute="location"
                     action="editLocations" method="POST">
                <div class="form-group">
                    <label for="add-name" class="col-md-4 control-label">Name:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-name"
                                  path="name" placeholder="Name"/>
                        <sf:errors path="name" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-address" class="col-md-4 control-label">Address:</label>                          
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-address"
                                  path="address" placeholder="Address"/>
                        <sf:errors path="address" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-description" class="col-md-4 control-label">Description:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-description"
                                  path="description" placeholder="Description"/>
                        <sf:errors path="description" cssclass="error"></sf:errors>
                    </div>
                </div>                   
                <div class="form-group">
                    <label for="add-latitude" class="col-md-4 control-label">Latitude:</label>                          
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-latitude"
                                  path="latitude" placeholder="Latitude"/>
                        <sf:errors path="latitude" cssclass="error"></sf:errors>
                    </div>
                </div>
                    
                <div class="form-group">
                    <label for="add-longitude" class="col-md-4 control-label">Longitude:</label>                          
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-longitude"
                                  path="longitude" placeholder="Longitude"/>
                        <sf:errors path="longitude" cssclass="error"></sf:errors>
                        <sf:hidden path="locationId"/>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Location"/>
                    </div>
                </div>
            </sf:form>                
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>