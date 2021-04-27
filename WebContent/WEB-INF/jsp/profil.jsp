<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	
	<%@include file="entete.jspf" %>
		
	<body>

		<!-- Messages internationalisés -->
		<fmt:bundle basename="fr.eni.encheres.tool.messages">
		
		<c:set var="pageName" value="profil"/>
		<%@include file="navigation.jspf" %>

		<!-- Page Content -->
		<div class="container">

			<!-- Page Heading -->
			<div class="row">
				<div class="col text-center">

					<h2 class="my-4"><fmt:message key="${title}"/></h2>

				</div>
			</div>

			<!-- Alerts -->
			<div class="row">
				<br>
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<br>
					<c:if test="${alerts!=null}">
					<c:forEach items="${alerts}" var="alert">
					<div class="alert alert-danger">
						<strong><fmt:message key="alert.type.danger"/></strong>${alert}
					</div>
					</c:forEach>
					</c:if>
					<br>
				</div>
				<div class="col-md-3"></div>
				<br>
			</div>
		
			<c:set scope="page" var="ro" value="${action=='view'}"/>
			
			<form class="form-horizontal" action="Profil" method="POST">
			
			<input type="hidden" name="action" value="${action}"/>
			<input type="hidden" name="no_utilisateur" value="${no_utilisateur}"/>
			
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputUsername" class="col-md-4 col-form-label"><fmt:message key="label.profil.user"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputUsername" name="in_username" value="${in_username}" ${ro?"readonly":""} required />
				    </div>
				  </div>
				</div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputLastname" class="col-md-4 col-form-label"><fmt:message key="label.profil.lastname"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputLastname" name="in_lastname" value="${in_lastname}"  ${ro?"readonly":""} required />
				    </div>
				  </div>
				</div>
				<div class="col-md-1"></div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputFirstname" class="col-md-4 col-form-label"><fmt:message key="label.profil.firstname"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputFirstname" name="in_firstname" value="${in_firstname}"  ${ro?"readonly":""} required />
				    </div>
				  </div>
				</div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputEmail" class="col-md-4 col-form-label"><fmt:message key="label.profil.email"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputEmail" name="in_email" value="${in_email}"  ${ro?"readonly":""} required 
				      	pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" placeholder="<fmt:message key="label.profil.email.placeholder"/>" />
				    </div>
				  </div>
				</div>
				<div class="col-md-1"></div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputPhone" class="col-md-4 col-form-label"><fmt:message key="label.profil.phone"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputPhone" name="in_phone" value="${in_phone}"  ${ro?"readonly":""} 
				      	pattern="[0-9]{10}" placeholder="<fmt:message key="label.profil.phone.placeholder"/>"/>
				    </div>
				  </div>
				</div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputStreet" class="col-md-4 col-form-label"><fmt:message key="label.profil.street"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputStreet" name="in_street" value="${in_street}"  ${ro?"readonly":""} required />
				    </div>
				  </div>
				</div>
				<div class="col-md-1"></div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputZip" class="col-md-4 col-form-label"><fmt:message key="label.profil.zip"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputZip" name="in_zip" value="${in_zip}"  ${ro?"readonly":""} required 
				      	pattern="[0-9]{5}" placeholder="<fmt:message key="label.profil.zip.placeholder"/>" />
				    </div>
				  </div>
				</div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputCity" class="col-md-4 col-form-label"><fmt:message key="label.profil.city"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control" id="inputCity" name="in_city" value="${in_city}"  ${ro?"readonly":""} required />
				    </div>
				  </div>
				</div>
				<div class="col-md-1"></div>
			</div>
			
			<c:if test="${action!='view'}">
			
			<!-- /.row -->
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputPassword" class="col-md-4 col-form-label"><fmt:message key="label.profil.password"/> :</label>
				    <div class="col-md-7">
				      <input type="password" class="form-control" id="inputPassword" name="in_password" value="${in_password}" required />
				    </div>
				  </div>
				</div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label for="inputConfirm" class="col-md-4 col-form-label"><fmt:message key="label.profil.confirm"/> :</label>
				    <div class="col-md-7">
				      <input type="password" class="form-control" id="inputConfirm" name="in_confirm" value="${in_confirm}" required />
				    </div>
				  </div>
				</div>
				<div class="col-md-1"></div>
			</div>

			<c:if test="${action!='create'}">
			
			<!-- /.row -->
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-5">
				  <div class="form-group row">
				    <label class="col-md-4 col-form-label"><fmt:message key="label.profil.credit"/> :</label>
				    <div class="col-md-7">
				      <input type="text" class="form-control-plaintext" id="inputCredit" name="in_credit" value="${in_credit}" readonly />
				    </div>
				  </div>
				</div>
				<div class="col-md-5">
				  <div class="form-group row">
						<div class="col-md-12">				  
				      <div class="form-check">
				      	<input type="hidden" name="ck_admin" value="${ck_admin}" />
				        <input class="" type="checkbox" id="checkAdmin" ${ck_admin!=null?"checked":""} disabled="disabled" />
				        <label class="form-check-label" for="checkAdmin">
				        	<fmt:message key="label.profil.admin"/>
				        </label>
				      </div>
				    </div>
				  </div>
				</div>
			</div>
			
			</c:if> <!-- test="${action!='create'}" -->
			
			</c:if> <!-- test="${action!='view'}" -->
			
			<!-- /.row -->
		  <div class="row">
		  	<br><br>
		  </div>
		  
		  <c:if test="${action=='create'}">
		  
			<!-- /.row -->
		  <div class="row">
		    <div class="col-md-2"></div>
		    <div class="col-md-3">
		  		<button type="submit" class="btn btn-primary btn-block"><fmt:message key="label.profil.create"/></button>
		    </div>
		    <div class="col-md-2"></div>
		   	<div class="col-md-3">
		   		<a class="btn btn-secondary btn-block" role="button" href="${root}/ListeEncheres"><fmt:message key="label.profil.cancel"/></a>
		  	</div>
		  	<div class="col-md-2"></div>
		  </div>
		  
		  </c:if> <!-- test="${action=='create'}" -->

		  <c:if test="${action=='update'}">
		  
			<!-- /.row -->
		  <div class="row">
		  	<div class="col-md-1"></div>
		    <div class="col-md-3">
		  		<button type="submit" class="btn btn-primary btn-block"><fmt:message key="label.profil.modify"/></button>
		    </div>
		   	<div class="col-md-3">
		   		<a class="btn btn-secondary btn-block" role="button" href="${root}/ListeEncheres"><fmt:message key="label.profil.cancel"/></a>
		  	</div>
		  	<div class="col-md-1"></div>
		   	<div class="col-md-4">
					<button type="button" class="btn btn-danger btn-block" data-toggle="modal" data-target="#suppressConfirmModal">
						<fmt:message key="label.profil.suppress"/>
					</button>
		  	</div>
		  	<div class="col-md-1"></div>
		  	
		  	<!-- Modal -->
				<div class="modal fade" id="suppressConfirmModal" tabindex="-1" role="dialog">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle"><fmt:message key="label.profil.suppress"/></h5>
								<button type="button" class="close" data-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<fmt:message key="label.profil.suppress.message"/>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="label.profil.cancel"/></button>
					   		<a class="btn btn-danger" role="button" href="${root}/ProfilDelete"><fmt:message key="label.profil.suppress.confirm"/></a>
							</div>
						</div>
					</div>
				</div>
		  	
		  </div>
		  
		  </c:if> <!-- test="${action=='modify'}" -->

		  <c:if test="${action=='view'}">
		  
			<!-- /.row -->
		  <div class="row">
		    <div class="col-md-4"></div>
		   	<div class="col-md-4">
		   		<a class="btn btn-secondary btn-block" role="button" href="${root}/ListeEncheres"><fmt:message key="label.profil.return"/></a>
		  	</div>
		  	<div class="col-md-4"></div>
		  </div>
		  
		  </c:if> <!-- test="${action=='view'}" -->

			</form>

			<div class="row">
				<br>
				<br>
			</div>

		</div>
		<!-- /.container -->

		<%@include file="piedDePage.jspf" %>
	
		</fmt:bundle>

	</body>
</html>