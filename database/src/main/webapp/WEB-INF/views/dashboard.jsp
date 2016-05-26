<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
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


	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<spring:message code="label.computercount" text="Computer found" />
				${count}
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" value="${page.search}"
							placeholder="<spring:message code="label.defaultSearch" text="Search by name"/>" /><input
							type="submit" id="searchsubmit"
							value="<spring:message code="label.search" text="Filter By Name"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="/database/addComputer"><spring:message
							code="buttons.addComputer" text="Add Computer" /></a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message
							code="buttons.edit" text="Edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm"
			action="${pageContext.request.contextPath}/deleteComputer"
			method="POST">
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
						<th><a
							<myTags:href target="/database/dashboard" pageIndex="${page.index}" pageSize="${page.maxSize}" field="name" search="${page.search}" order="${page.field.ordinal() == 1 && page.order.ordinal() == 0 ? 'DESC' : 'ASC'}"/>><spring:message
									code="table.name" text="Computer name" /></a></th>
						<th><a
							<myTags:href target="/database/dashboard" pageIndex="${page.index}" pageSize="${page.maxSize}" field="introduced" search="${page.search}" order="${page.field.ordinal() == 2 && page.order.ordinal() == 0 ? 'DESC' : 'ASC'}"/>><spring:message
									code="table.introduced" text="Introduced" /></a></th>
						<!-- Table header for Discontinued Date -->
						<th><a
							<myTags:href target="/database/dashboard" pageIndex="${page.index}" pageSize="${page.maxSize}" field="discontinued" search="${page.search}" order="${page.field.ordinal() == 3 && page.order.ordinal() == 0 ? 'DESC' : 'ASC'}"/>><spring:message
									code="table.discontinued" text="Discontinued" /></a></th>
						<!-- Table header for Company -->
						<th><a
							<myTags:href target="/database/dashboard" pageIndex="${page.index}" pageSize="${page.maxSize}" field="company" search="${page.search}" order="${page.field.ordinal() == 4 && page.order.ordinal() == 0 ? 'DESC' : 'ASC'}"/>><spring:message
									code="table.company" text="Company" /></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.entities}" var="computer">
						<tr>
							<td class="editMode" id="computer"><input type="checkbox"
								name="cb" class="cb" id="${computer.name}_id"
								value="${computer.id}"></td>
							<td><a href="editComputer?computerId=${computer.id}"
								onclick="" id="${computer.name}_name">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</section>

	<script type="text/javascript">
		var strings = new Array();
		strings['js.deleteConfirmation'] = "<spring:message code='js.deleteConfirmation' javaScriptEscape='true' />";
	</script>

	<footer class="navbar-fixed-bottom">
		<myTags:pagination currentIndex="${page.index}"
			notBeginIndex="${notBeginIndex}" notEndIndex="${notEndIndex}"
			beginIndex="${beginIndex}" endIndex="${endIndex}"
			offset="${page.maxSize} "></myTags:pagination>
	</footer>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>