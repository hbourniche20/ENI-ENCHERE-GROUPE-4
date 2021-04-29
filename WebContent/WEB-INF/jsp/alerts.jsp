<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${ not empty error }">
	<div class="alert alert-danger text-center my-5" role="alert">
			${ error } 
	</div>
</c:if>
<c:if test="${ not empty success }">
	<div class="alert alert-success text-center my-5" role="alert">
			${ success } 
	</div>
</c:if>