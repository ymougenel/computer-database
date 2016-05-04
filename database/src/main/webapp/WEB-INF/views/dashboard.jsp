<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="/database/dashboard"> Application -
			Computer Database </a>
	</div>
	</header>

	<c:if test="${postMessage}">
		<div class="alert alert-${messageLevel} alert-dismissible"
			role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${messageHeader}</strong> ${messageBody}
		</div>
	</c:if>

	<section id="main">
	<div class="container">
		<h1 id="homeTitle">${count} Computers found</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">

					<input type="search" id="searchbox" name="search"
						class="form-control" value="${page.search}" placeholder="Search name"/><input
						type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer"
					href="/database/addComputer">Add Computer</a> <a
					class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();">Edit</a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="${pageContext.request.contextPath}/deleteComputer" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th><a href="dashboard?field=name" >Computer name</a></th>
					<th><a href="dashboard?field=introduced" >Introduced date</a></th>
					<!-- Table header for Discontinued Date -->
					<th><a href="dashboard?field=discontinued" >Discontinued date</a></th>
					<!-- Table header for Company -->
					<th><a href="dashboard?field=company" >Company</a></th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${page.entities}" var="computer">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value=${ computer.id}></td>
						<td><a href="editComputer?computerId=${computer.id}" onclick="" id="computer">${computer.name}</a></td>
						<td>${computer.introduced}</td>
						<td>${computer.discontinued}</td>
						<td>${computer.companyName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</section>

	<footer class="navbar-fixed-bottom"> <myTags:pagination
		currentIndex="${page.index}" notBeginIndex="${notBeginIndex}"
		notEndIndex="${notEndIndex}" beginIndex="${beginIndex}"
		endIndex="${endIndex}" search="${page.search}" offset="${page.maxSize}"></myTags:pagination> </footer>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>