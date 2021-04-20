<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${ not empty sessionScope.user }">
    	Bienvenue <c:out value="${ sessionScope.user.pseudo }"></c:out>
    	<a href="DeconnexionServlet">Se Deconnecter</a>
    </c:when>
    <c:otherwise>
		<a href="ConnexionServlet">Se connecter</a>
		<a href="CreationCompteServlet">S'inscrire</a>
    </c:otherwise>
</c:choose>