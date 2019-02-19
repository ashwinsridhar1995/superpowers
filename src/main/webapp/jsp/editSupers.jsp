<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Supers</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Edit Super</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                  <li role="presentation">
                        <a href="${pageContext.request.contextPath}/">
                          Home
                        </a>
                  </li>
                  <li role="presentation" class="active">
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
            <sf:form class="form-horizontal" role="form" modelAttribute="supers"
                     action="editSupers" method="POST">
                <div class="form-group">
                    <label for="add-name" class="col-md-4 control-label">Name:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-name"
                                  path="name" placeholder="Name"/>
                        <sf:errors path="name" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-bio" class="col-md-4 control-label">Bio:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-bio"
                                  path="bio" placeholder="Bio"/>
                        <sf:errors path="bio" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-power" class="col-md-4 control-label">Power:</label>                          
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-power"
                                  path="power" placeholder="Power"/>
                        <sf:errors path="power" cssclass="error"></sf:errors>
                    </div>
                </div>

                <div class="form-group">
                    <label for="add-organization" class="col-md-4 control-label">Organization:</label>
                        <div class="col-md-8">
                            <select name ="Organization" multiple>
                               <c:forEach var="currentOrganization" items="${organizationList}">
                                        <option value="${currentOrganization.organizationId}" 
                                                ${supers.organizations.contains(currentOrganization) ? 'selected' : ''}>
                                            <c:out value="${currentOrganization.name}"/>
                                        </option>
                               </c:forEach>
                               <sf:hidden path="supersId"/>
                            </select>
                        </div>
                </div>
 
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Super"/>
                    </div>
                </div>
            </sf:form>                
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
