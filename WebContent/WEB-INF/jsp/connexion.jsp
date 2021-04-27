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
		
		<c:set var="pageName" value="connexion"/>
		<%@include file="navigation.jspf" %>

		<!-- Page Content -->
		<div class="container">
			<!-- Page Heading -->
			<div class="row">
				<div class="col text-center">
					<h2 class="my-4"><fmt:message key="label.connexion.title"/></h2>
				</div>
			</div>
			<div class="row">
				<br>
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<br>
					<c:if test="${alert!=null}">
					<div class="alert alert-danger">
						<strong><fmt:message key="alert.type.danger"/></strong><fmt:message key="${alert}"/>
					</div>
					<br>
					</c:if>
				</div>
				<div class="col-md-3"></div>
				<br>
			</div>
		
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<form class="form-horizontal" action="Connexion" method="POST">
					  
					  <div class="form-group row">
					    <label for="inputUserid" class="col-md-4 col-form-label"><fmt:message key="label.connexion.userid"/> :</label>
					    <div class="col-md-8">
					      <input type="text" class="form-control" id="inputUserid" name="in_username" placeholder="<fmt:message key="label.connexion.userid.placeholder"/>" 
					      	value="${in_username}" />
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="inputPassword" class="col-md-4 col-form-label"><fmt:message key="label.connexion.password"/> :</label>
					    <div class="col-md-8">
					      <input type="password" class="form-control" id="inputPassword" name="in_password" placeholder="<fmt:message key="label.connexion.password"/>"
					      	value="${in_password}" />
					    </div>
					  </div>
					  <div class="form-group row">
					   	<div class="col-md-4"></div>
					    <div class="col-md-8">
					      <div class="form-check">
					        <input class="" type="checkbox" id="checkRemember" name="ck_remember" ${ck_remember!=null?"checked":""} />
					        <label class="form-check-label" for="checkRemember">
					        	<fmt:message key="label.connexion.rememberme"/>
					        </label>
					      </div>
					    </div>
					  </div>
					  <div class="row">
					  	<div class="col-md-2"></div>
					   	<div class="col-md-8">
					  		<button type="submit" class="btn btn-primary btn-block"><fmt:message key="label.connexion.connect"/></button>
					  	</div>
					  	<div class="col-md-2"></div>
					  </div>
					  <div class="row">
					    <div class="col-md-2"></div>
					    <div class="col-md-8">
					    	<a class="btn btn-link" href="#"><fmt:message key="label.connexion.forgotten"/></a>
					    </div>
					  	<div class="col-md-2"></div>
					  </div>
						<div class="row">
							<br>
							<br>
						</div>
					  <div class="row">
					  	<div class="col-md-2"></div>
					   	<div class="col-md-8">
					   		<a class="btn btn-secondary btn-block" role="button" href="${root}/Profil?action=create&title=label.profil.title.newuser"><fmt:message key="label.connexion.register"/></a>
					  	</div>
					  	<div class="col-md-2"></div>
					  </div>

					</form>
				</div>
				<div class="col-md-3"></div>
			</div>
			<!-- /.row -->

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