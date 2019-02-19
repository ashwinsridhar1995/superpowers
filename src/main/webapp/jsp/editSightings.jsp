<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Edit Sighting</h1>
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
                  <li role="presentation">
                      <a href="${pageContext.request.contextPath}/displayLocationsPage">
                          Locations
                      </a>
                  </li>
                  <li role="presentation">
                      <a href="${pageContext.request.contextPath}/displayOrganizationsPage">
                          Organizations
                      </a>
                  </li>
                  <li role="presentation" class="active">
                        <a href="${pageContext.request.contextPath}/displaySightingsPage">
                          Sightings
                        </a>
                  </li>
                </ul>    
            </div>
            <sf:form class="form-horizontal" role="form" modelAttribute="sighting"
                     action="editSightings" method="POST">
                    <div class="form-group">
                        <label for="add-date" class="col-md-4 control-label">Date of Sighting:</label>
                        <div class="col-md-8">
                            <sf:input type="date" class="form-control" id="add-date"
                                  path="date" placeholder="${sighting.date}"/>
                            <sf:errors path="date" cssclass="error"></sf:errors>          
                        </div>
                    </div>
                <div class="form-group">
                    <label for="add-location" class="col-md-4 control-label">Location:</label>
                        <div class="col-md-8">
                            <select name ="locationId">
                               <c:forEach var="currentLocation" items="${location}">
                                        <option value="${currentLocation.locationId}">
                                            <c:out value="${currentLocation.name}"/>
                                        </option>
                               </c:forEach>
                            </select>
                        </div>
                </div>
                <div class="form-group">
                    <label for="add-supers" class="col-md-4 control-label">Supers:</label>
                        <div class="col-md-8">
                            <select name ="supersId" multiple>
                               <c:forEach var="currentSuper" items="${supers}">
                                        <option value="${currentSuper.supersId}">
                                            <c:out value="${currentSuper.name}"/>
                                        </option>
                               </c:forEach>
                            </select>
                        </div>
                </div>
                <sf:hidden path="sightingId"/>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Sighting"/>
                    </div>
                </div>
            </sf:form>                
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
