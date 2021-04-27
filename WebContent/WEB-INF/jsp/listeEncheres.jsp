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
		
		<c:set var="pageName" value="listeEncheres"/>
		<%@include file="navigation.jspf" %>

		<!-- Page Content -->
		<div class="container">
			<!-- Page Heading -->
			<div class="row">
				<div class="col text-center">
					<h2 class="my-4"><fmt:message key="label.listeEncheres.title"/></h2>
				</div>
			</div>
			<div class="row">
				<br>
			</div>

			<div class="row mb-2">
				<div class="col-md-12">
					<h5><fmt:message key="label.listeEncheres.filters"/></h5>
				</div>
			</div>
			
			<form class="form" action="ListeEncheres" method="POST">

			<div class="row">
				<div class="col-md-8">
  				<div class="row mb-2">
						<div class="col-md-10">
					      <input type="text" class="form-control" id="inputNom" name="in_nom" placeholder="<fmt:message key="label.listeEncheres.nom.placeholder"/>" value="${in_nom}" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group row">
						    <label for="selectCategories" class="col-md-2 col-form-label"><fmt:message key="label.listeEncheres.categories"/> :</label>
						    <div class="col-md-10">

									<select class="browser-default custom-select" id="selectCategories" name="sl_categorie">
										<option value="-1" ${sl_categorie=='-1' ? "selected" : ""}><fmt:message key="label.listeEncheres.categories.all"/></option>
										<c:if test="${categories!=null}">
										<c:forEach var="category" items="${categories}">
										<option value="${category.key}" ${category.key==sl_categorie ? "selected" : ""}><fmt:message key="${category.value}"/></option>
										</c:forEach>
										</c:if>
									</select>
									
						    </div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
		  		<button type="submit" class="btn btn-primary btn-block"><fmt:message key="label.listeEncheres.find"/></button>
				</div>
			</div>
			<!-- /.row -->

			</form>

			<div class="row">
				<br>
			</div>

			<div class="row">

				<c:if test="${cartes!=null}">
				<c:forEach var="carte" items="${cartes}">

				<div class="col-md-6 mb-4">
					<div class="card h-100">
						<div class="card-body">
							<div class="row">
								<div class=col-md-3>
									<img src="${carte.cheminPhoto!='' ? carte.cheminPhoto : 'img/article_default.png'}" class="img-fluid"/>
								</div>
								<div class=col-md-9>
									<h4 class="card-title">
										<c:if test="${user!=null}">
										<a href="${root}/Vente?action=view&no_article=${carte.idArticle}&title=label.vente.title.detail">${carte.nomArticle}</a>
										</c:if>
										<c:if test="${user==null}">${carte.nomArticle}</c:if>
									</h4>
									<p class="card-text"><fmt:message key="label.listeEncheres.card.price"><fmt:param value="${carte.prix}"/></fmt:message></p>
									<p class="card-text"><fmt:message key="label.listeEncheres.card.end"/> : ${carte.dateFin}</p>

									<c:if test="${user!=null}">
									<p class="card-text"><fmt:message key="label.listeEncheres.card.seller"/> : 
										<a href="${root}/Profil?action=view&no_utilisateur=${carte.idVendeur}&title=label.profil.title.seller">${carte.nomVendeur}</a>
									</p>
									</c:if>

 								</div>
							</div>	
						</div>
 				</div>
				</div>

				</c:forEach>
				</c:if>
					
			</div>
			<!-- /.row -->

			<div class="row">
				<br><br>
			</div>

		</div>
		<!-- /.container -->

		<%@include file="piedDePage.jspf" %>
	
		</fmt:bundle>

	</body>
</html>