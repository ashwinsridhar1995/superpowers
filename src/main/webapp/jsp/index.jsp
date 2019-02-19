<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Supers Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Supers Sightings</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                  <li role="presentation" 
                      class="active">
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
                  <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displaySightingsPage">
                            Sightings
                        </a>
                  </li>
                </ul>
            </div>
                <p>
                    Welcome to the Superhero/villain Sightings System. Here, we keep you
                    updated on all the latest superhero/villain sightings
                </p>
        </div>
                            
        <table id="SightingsTable" class="table table-hover">
                <h2>List of Sightings</h2>
                <tr>
                    <th width="40%">Date of Sighting</th>
                    <th width="20%">Supers at Sighting</th>
                    <th width="20%">Location</th>
                    <th width="10%"></th>
                    <th width="10%"></th>
                </tr>
                <c:forEach var="currentSighting" items="${sightings}">
                   <tr>
                       <td>
                           <c:out value="${currentSighting.date}"/>
                       </td>
                       <td>
                           <c:forEach var="currentSuper" items="${currentSighting.supers}">
                                <c:out value="${currentSuper.name}"/>
                           </c:forEach>
                       </td>
                       <td>
                           <c:out value="${currentSighting.location.name}"/>
                       </td>
                       <td>
                           <a href="displayEditSighting?sightingId=${currentSighting.sightingId}">
                           Edit
                           </a>
                       </td>
                       <td>
                           <a href="deleteSighting?sightingId=${currentSighting.sightingId}">
                           Delete
                           </a>
                       </td>
                   </tr>
               </c:forEach>
            </table>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

