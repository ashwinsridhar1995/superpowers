<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Organizations</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Organizations</h1>
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
                  <li role="presentation" class="active">
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
            <table id="HeroVillainTable" class="table table-hover">
                <h2>List of Organization</h2>
                <tr>
                    <th width="40%">Name</th>
                    <th width="30%">Address</th>
                    <th width="15%"></th>
                    <th width="15%"></th>
                </tr>
                <c:forEach var="currentOrganization" items="${organizationList}">
                   <tr>
                       <td>
                           <c:out value="${currentOrganization.name}"/>
                       </td>
                       <td>
                           <c:out value="${currentOrganization.address}"/>
                       </td>
                       <td>
                           <a href="displayEditOrganization?organizationId=${currentOrganization.organizationId}">
                           Edit
                           </a>
                       </td>
                       <td>
                           <a href="deleteOrganization?organizationId=${currentOrganization.organizationId}">
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
                    <h2>Add New Organization</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createOrganization">
                        <div class="form-group">
                            <label for="add-name" class="col-md-4 control-label">Name:</label>
                            <div class="col-md-8">
                              <input type="text" class="form-control" name="name" placeholder="Name"
                                     required minlength="1" maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-description" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="description" placeholder="Description"
                                       required minlength="1" maxlength="30"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-address" class="col-md-4 control-label">Address:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="address" placeholder="Address"
                                       required minlength="1" maxlength="30"/>
                            </div>
                        </div>
                       <div class="form-group">
                            <label for="add-contact" class="col-md-4 control-label">Contact:</label>
                            <div class="col-md-8">
                                <input type="contact" class="form-control" name="contact" placeholder="Contact"
                                       required minlength="1" maxlength="30"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Organization"/>
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
