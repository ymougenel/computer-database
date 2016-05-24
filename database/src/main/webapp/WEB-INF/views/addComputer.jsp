<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="/css/bootstrap.min.css"
	var="bootstrap.mincss" />
<spring:url value="/css/font-awesome.css"
	var="font-awesomecss" />
<spring:url value="/css/main.css" var="maincss" />

<link href="${bootstrap.mincss}" rel="stylesheet" media="screen">
<link href="${font-awesomecss}" rel="stylesheet" media="screen">
<link href="${maincss}" rel="stylesheet" media="screen">
</head>

<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="/database/dashboard"> Application -
			Computer Database </a>
	</div>
	</header>

	<c:if test="${postMessage}">
		<c:forEach items="${errors}" var="error">
			<div class="alert alert-error alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>Error encountered:</strong> ${error}
			</div>
		</c:forEach>
	</c:if>

	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form id="computerForm" action="addComputer" method="POST">
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label> <input
								type="text" class="form-control" id="computerName"
								placeholder="Computer name" name="computerName">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" id="introduced"
								placeholder="Introduced date" name="introduced">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" id="discontinued"
								placeholder="Discontinued date" name="discontinued">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> <select
								class="form-control" id="companyId" name="companyId">
								<option value="" name="company">--</option>
								<c:forEach items="${companies}" var="company">
									<option value="${company.id}" id="company">${company.name}</option>
								</c:forEach>

							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Add" class="btn btn-primary">
						or <a href="dashboard" class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	</section>
	<spring:url value="/js/jquery.min.js" var="jqueryjs" />
	<spring:url value="/js/computerFormValidation.js"
		var="valjs" />
	<script src="${jqueryjs}"></script>
	<script src="${valjs}"></script>
</body>

</html>