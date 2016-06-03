<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ attribute name="offset" required="true"%>
<%@ attribute name="currentIndex" required="true"%>


<c:set var="beginIndex" value="1" />

<c:if test="${(currentIndex - 3) >= 1}">
	<c:set var="beginIndex" value="${currentIndex - 3}" />
</c:if>

<c:set var="endIndex" value="${limitPages}" />

<c:if test="${(currentIndex + 3) <= limitPages}">
	<c:set var="endIndex" value="${currentIndex + 3}" />
</c:if>

<c:set var="notBeginIndex" value="${currentIndex!=1}" />
<c:set var="notEndIndex" value="${currentIndex!=endIndex}" />

<div class="container text-center">
	<ul class="pagination">
		<c:if test="${notBeginIndex}">
			<li><a
				<tags:href target="/database/dashboard" pageIndex="${currentIndex - 1}" pageSize="${offset}" search="${page.search}" order="${page.orderString}" field="${page.field.name()}"/>
				aria-label="Previous" id="previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>
		</c:if>
		<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
			<c:choose>
				<c:when test="${i == currentIndex}">
					<li class="active"><a
						<tags:href target="/database/dashboard" pageIndex="${i}" pageSize="${page.maxSize}" search="${page.search}" order="${page.orderString}" field="${page.field.name()}"/>>${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a <tags:href target="/database/dashboard" pageIndex="${i}" pageSize="${page.maxSize}" search="${page.search}" order="${page.orderString}" field="${page.field.name()}"/>>${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${notEndIndex}">
			<li><a <tags:href target="/database/dashboard" pageIndex="${currentIndex + 1}" pageSize="${page.maxSize}" search="${page.search}" order="${page.orderString}" field="${page.field.name()}"/>
				aria-label="Next" id="next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</c:if>
	</ul>

	<div class="btn-group btn-group-sm pull-right" role="group">
		<a <tags:href target="/database/dashboard" pageIndex="1" pageSize="10" search="${page.search}" order="${page.orderString}" field="${page.field.name()}"/>>
			<button type="button"
				class="btn btn-default">10</button></a>
				<a <tags:href target="/database/dashboard" pageIndex="1" pageSize="50" search="${page.search}" order="${page.orderString}" field="${page.field.name()}"/>>
				<button type="button"
				class="btn btn-default">50</button></a>
				<a <tags:href target="/database/dashboard" pageIndex="1" pageSize="100" search="${page.search}" order="${page.orderString}" field="${page.field.name()}"/>>
				<button type="button"
				class="btn btn-default">100</button></a>
	</div>
</div>