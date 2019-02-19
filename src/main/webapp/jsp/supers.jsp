<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
            <h1>Supers</h1>
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
                  <li role="presentation" >
                        <a href="${pageContext.request.contextPath}/displaySightingsPage">
                            Sightings
                        </a>
                  </li>
                </ul>    
            </div>
            <table id="SuperTable" class="table table-hover">
                <h2>List of Supers</h2>
                <tr>
                    <th width="40%">Name</th>
                    <th width="30%">Organizations</th>
                    <th width="15%"></th>
                    <th width="15%"></th>
                </tr>
                <c:forEach var="currentSuper" items="${supersList}">
                   <tr>
                       <td>
                           <c:out value="${currentSuper.name}"/>
                       </td>
                       <td>
                           <c:forEach var="currentOrganization" items="${currentSuper.organizations}">
                                <c:out value="${currentOrganization.name}"/>
                           </c:forEach>
                       </td>
                       <td>
                           <a href="displayEditSupersPage?supersId=${currentSuper.supersId}">
                           Edit
                           </a>
                       </td>
                       <td>
                           <a href="deleteSuper?supersId=${currentSuper.supersId}">
                           Delete
                           </a>
                       </td>
                   </tr>
               </c:forEach>
            </table>
            <div class="row">
                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <!-- 
                    Add col to hold the new contact form - have it take up the other 
                    half of the row
                -->
                <div class="col-md-6">
                    <h2>Add New Super</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createSuper">
                        <div class="form-group">
                            <label for="add-name" class="col-md-4 control-label">Name:</label>
                            <div class="col-md-8">
                              <input type="text" class="form-control" name="name" placeholder="Name"
                                     required minlength="1" maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-bio" class="col-md-4 control-label">Bio:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="bio" placeholder="Bio"
                                       required minlength="1" maxlength="30"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-power" class="col-md-4 control-label">Power:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="power" placeholder="Power"
                                       required minlength="1" maxlength="20"/>
                            </div>
                        </div>
                       <div class="form-group">
                            <label for="add-organization" class="col-md-4 control-label">Organization:</label>
                            <div class="col-md-8">
                                <select required="true" name ="Organization" multiple>
                                    <c:forEach var="currentOrganization" items="${organizationList}">
                                        <option value="${currentOrganization.organizationId}"><c:out value="${currentOrganization.name}"/></option> 
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Super"/>
                            </div>
                        </div>
                    </form>

                </div> <!-- End col div -->

            </div> <!-- End row div -->
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
