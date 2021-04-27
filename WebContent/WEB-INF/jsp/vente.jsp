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
		
		<c:set var="pageName" value="${action}Vente"/>
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
			
			<form class="form-horizontal" action="Vente" method="POST">
			
		 	<input type="hidden" name="action" value="${action}"/>
			<input type="hidden" name="no_article" value="${no_article}"/>
			<input type="hidden" name="title" value="${title}"/>
			
			<div class="row">
			
				<!-- Image -->
				<div class="col-md-4" align="center" >
					<img src="${in_picture!='' ? in_picture : 'img/article_default.png'}" class="img-fluid"/>
				</div>
				
				<div class="col-md-8">

					<!-- Nom article -->
				  <div class="form-group row">
				    <label for="inputItem" class="col-md-4 col-form-label"><fmt:message key="label.vente.item"/> :</label>
				    <div class="col-md-6">
				      <input type="text" class="form-control" id="inputItem" name="in_item" value="${in_item}" ${ro?"readonly":""} required />
				    </div>
				  </div>

					<!-- Description -->
				  <div class="form-group row">
				    <label for="textareaDescription" class="col-md-4 col-form-label"><fmt:message key="label.vente.description"/> :</label>
				    <div class="col-md-6">
							<textarea class="form-control rounded-0" id="textareaDescription"  name="ta_description" ${ro?"readonly":""} required rows="3" >${ta_description}</textarea>
				    </div>
				  </div>

					<!-- Catégorie -->
				  <div class="form-group row">
				    <label for="selectCategories" class="col-md-4 col-form-label"><fmt:message key="label.vente.category"/> :</label>
				    <div class="col-md-6">

							<c:if test="${ro}"><input type="hidden" name="sl_category" value="${sl_category}"/></c:if>

							<select class="browser-default custom-select" id="selectCategories" name="sl_category" ${ro?"disabled":""} >
								<option value="-1" ${sl_category=='-1' ? "selected" : ""} disabled="disabled" ><fmt:message key="label.categorie.choose"/></option>
								<c:if test="${categories!=null}">
									<c:forEach var="category" items="${categories}">
										<option value="${category.key}" ${category.key==sl_category ? "selected" : ""}><fmt:message key="${category.value}"/></option>
									</c:forEach>
								</c:if>
							</select>

						</div>
					</div>
					
					<!-- Upload image -->
				  <div class="form-group row">
				    <label for="inputPicture" class="col-md-4 col-form-label"><fmt:message key="label.vente.picture"/> :</label>
						<div class="col-md-6">
							<input type="text" class="form-control" id="inputPicture" name="in_picture" value="${in_picture}" placeholder="https://" ${ro?"readonly":""} />
						</div>
				  </div>
				  
					<!-- Mise à prix -->
					<div class="form-group row">
						<label for="inputPrice" class="col-md-4 col-form-label"><fmt:message key="label.vente.price"/> :</label>
						<div class="col-md-6">
							<input type="number" class="form-control" id="inputPrice" name="in_price" value="${in_price}" min="0" ${ro?"readonly":""} required/>
						</div>
					</div>
					
					<!-- Date début -->
					<div class="form-group row">
						<label for="inputBegin" class="col-md-4 col-form-label"><fmt:message key="label.vente.begin"/> :</label>
						<div class="col-md-6">
							<input type="datetime-local" class="form-control" id="inputBegin" name="dt_begin" value="${dt_begin}" ${ro?"readonly":""} required />
						</div>
					</div>
					
					<!-- Date fin -->
					<div class="form-group row">
						<label for="inputEnd" class="col-md-4 col-form-label"><fmt:message key="label.vente.end"/> :</label>
						<div class="col-md-6">
							<input type="datetime-local" class="form-control" id="inputEnd" name="dt_end" value="${dt_end}" ${ro?"readonly":""} required />
						</div>
					</div>
					
					<!-- Retrait -->
					<div class="row">
						<div class="col-md-10">
							<div class="card">
								<div class="card-header"><fmt:message key="label.vente.withdrawal"/></div>
								<div class="card-body">
									<!-- Rue -->
									<div class="form-group row">
										<label for="inputStreet" class="col-md-3 col-form-label"><fmt:message key="label.vente.street"/> :</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="inputStreet" name="in_street" value="${in_street}" ${ro?"readonly":""} required />
										</div>
									</div>
									<!-- Code Postal -->
									<div class="form-group row">
										<label for="inputZip" class="col-md-3 col-form-label"><fmt:message key="label.vente.zip"/> :</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="inputZip" name="in_zip" value="${in_zip}" ${ro?"readonly":""} required />
										</div>
									</div>
									<!-- Ville -->
									<div class="form-group row">
										<label for="inputCity" class="col-md-3 col-form-label"><fmt:message key="label.vente.city"/> :</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="inputCity" name="in_city" value="${in_city}" ${ro?"readonly":""} required />
										</div>
									</div>
								</div>

							</div>
							<div class="col-md-2"></div>
						</div>
					</div> <!-- Retrait -->

				</div>
			</div>
			
			<!-- /.row -->
		  <div class="row">
		  	<br><br>
		  </div>
		  
		  <c:if test="${action=='create'}">
		  
			<!-- /.row -->
		  <div class="row">
		    <div class="col-md-2"></div>
		    <div class="col-md-3">
		  		<button type="submit" class="btn btn-primary btn-block"><fmt:message key="label.vente.save"/></button>
		    </div>
		    <div class="col-md-2"></div>
		   	<div class="col-md-3">
		   		<a class="btn btn-secondary btn-block" role="button" href="${root}/ListeEncheres"><fmt:message key="label.vente.cancel"/></a>
		  	</div>
		  	<div class="col-md-2"></div>
		  </div>
		  
		  </c:if> <!-- test="${action=='create'}" -->

		  <c:if test="${action=='update'}">
		  
			<!-- /.row -->
		  <div class="row">
		  	<div class="col-md-1"></div>
		    <div class="col-md-3">
		  		<button type="submit" class="btn btn-primary btn-block"><fmt:message key="label.vente.save"/></button>
		    </div>
		   	<div class="col-md-3">
		   		<a class="btn btn-secondary btn-block" role="button" href="${root}/ListeEncheres"><fmt:message key="label.vente.cancel"/></a>
		  	</div>
		  	<div class="col-md-1"></div>
		   	<div class="col-md-4">
					<button type="button" class="btn btn-danger btn-block" data-toggle="modal" data-target="#suppressConfirmModal">
						<fmt:message key="label.vente.suppress"/>
					</button>
		  	</div>
		  	<div class="col-md-1"></div>
		  	
		  	<!-- Modal -->
				<div class="modal fade" id="suppressConfirmModal" tabindex="-1" role="dialog">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle"><fmt:message key="label.vente.suppress"/></h5>
								<button type="button" class="close" data-dismiss="modal"></button>
							</div>
							<div class="modal-body">
								<fmt:message key="label.vente.suppress.message"/>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="label.vente.cancel"/></button>
					   		<a class="btn btn-danger" role="button" href="${root}/VenteDelete?no_article=${no_article}"><fmt:message key="label.vente.suppress.confirm"/></a>
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
		   		<a class="btn btn-secondary btn-block" role="button" href="${root}/ListeEncheres"><fmt:message key="label.vente.return"/></a>
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