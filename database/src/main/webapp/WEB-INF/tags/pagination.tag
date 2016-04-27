<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="currentIndex" required="true"%>
<%@ attribute name="notBeginIndex" required="true"%>
<%@ attribute name="notEndIndex" required="true"%>
<%@ attribute name="beginIndex" required="true"%>
<%@ attribute name="endIndex" required="true"%>

<div class="container text-center">
	<ul class="pagination">
		<c:if test="${notBeginIndex}">
			<li><a href="database/dashboard?pageIndex=Previous"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>
		</c:if>
		<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
   			<li><a href="/database/dashboard?pageIndex=${i}">${i}</a></li>
		</c:forEach>
		<c:if test="${notEndIndex}">
			<li><a href="/database/dashboard?pageIndex=Next"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</c:if>
	</ul>

	<div class="btn-group btn-group-sm pull-right" role="group">
		<a href="/database/dashboard?pageSize=10"><button type="button"
				class="btn btn-default">10</button></a> <a
			href="/database/dashboard?pageSize=50"><button type="button"
				class="btn btn-default">50</button></a> <a
			href="/database/dashboard?pageSize=100"><button type="button"
				class="btn btn-default">100</button></a>
	</div>
</div>