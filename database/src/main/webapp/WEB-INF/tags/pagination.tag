<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ attribute name="search" required="true"%>
<%@ attribute name="offset" required="true"%>
<%@ attribute name="currentIndex" required="true"%>
<%@ attribute name="notBeginIndex" required="true"%>
<%@ attribute name="notEndIndex" required="true"%>
<%@ attribute name="beginIndex" required="true"%>
<%@ attribute name="endIndex" required="true"%>

<div class="container text-center">
	<ul class="pagination">
		<c:if test="${notBeginIndex}">
			<li><a
				<tags:href target="/database/dashboard" pageIndex="${currentIndex - 1}" pageSize="${offset}" search="${search}"/>
				aria-label="Previous" id="previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>
		</c:if>
		<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
			<c:choose>
				<c:when test="${i == currentIndex}">
					<li class="active"><a
						<tags:href target="/database/dashboard" pageIndex="${i}" pageSize="${offset}" search="${search}"/>>${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a <tags:href target="/database/dashboard" pageIndex="${i}" pageSize="${offset}" search="${search}"/>>${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${notEndIndex}">
			<li><a <tags:href target="/database/dashboard" pageIndex="${currentIndex + 1}" pageSize="${offset}" search="${search}"/>
				aria-label="Next" id="next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</c:if>
	</ul>

	<div class="btn-group btn-group-sm pull-right" role="group">
		<a <tags:href target="/database/dashboard" pageIndex="1" pageSize="10" search="${search}"/>>
			<button type="button"
				class="btn btn-default">10</button></a>
				<a <tags:href target="/database/dashboard" pageIndex="1" pageSize="50" search="${search}"/>>
				<button type="button"
				class="btn btn-default">50</button></a>
				<a <tags:href target="/database/dashboard" pageIndex="1" pageSize="100" search="${search}"/>>
				<button type="button"
				class="btn btn-default">100</button></a>
	</div>
</div>