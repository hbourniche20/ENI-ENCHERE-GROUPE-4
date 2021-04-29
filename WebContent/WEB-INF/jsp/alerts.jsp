<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${ not empty error }">
	<div class="alert alert-danger text-center my-5" role="alert">
			${ error } 
	</div>
</c:if>