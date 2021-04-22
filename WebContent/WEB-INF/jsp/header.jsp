<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary px-5 fixed-top">
  <a class="navbar-brand text-light" href="index">ENI - Enchères</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarText">
    <ul class="navbar-nav ml-auto">
      <c:choose>
	    <c:when test="${ not empty sessionScope.user }">
	    	<li class="nav-item active">
		       <a class="nav-link" href="${ pageContext.request.contextPath }">Enchères</a>
		    </li>
		    <li class="nav-item active">
		       <a class="nav-link" href="${ pageContext.request.contextPath }/AjoutArticleVenduServlet">Vendre un article</a>
		    </li>
		    <li class="nav-item active">
		       <a class="nav-link" href="${ pageContext.request.contextPath }/ProfileServlet?pseudo=${ sessionScope.user.getPseudo()}">Mon profil</a>
		    </li>
		    <li class="nav-item active dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          <c:out value="${ sessionScope.user.pseudo }"></c:out>
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          	<a class="dropdown-item" href="${ pageContext.request.contextPath }/DeconnexionServlet">Se Deconnecter</a>
		        </div>
      		</li>
	    </c:when>
	    <c:otherwise>
		  <li class="nav-item active">
	        <a class="nav-link" href="${ pageContext.request.contextPath }/ConnexionServlet">S'inscrire - Se connecter</a>
	      </li>
	    </c:otherwise>
	</c:choose>
    </ul>
  </div>
</nav>

