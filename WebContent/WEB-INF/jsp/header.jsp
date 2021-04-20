<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary px-5 fixed-top">
  <a class="navbar-brand" href="${ pageContext.request.contextPath }">ENI - Enchères</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarText">
    <ul class="navbar-nav ml-auto">
      <c:choose>
	    <c:when test="${ not empty sessionScope.user }">
	    	<li class="nav-item active">
		       <a class="nav-link" href="#">Enchères</a>
		    </li>
		    <li class="nav-item active">
		       <a class="nav-link" href="#">Vendre un article</a>
		    </li>
		    <li class="nav-item active">
		       <a class="nav-link" href="#">Mon profil</a>
		    </li>
		    <li class="nav-item active dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          Bienvenue <c:out value="${ sessionScope.user.pseudo }"></c:out>
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          	<a class="dropdown-item" href="DeconnexionServlet">Se Deconnecter</a>
		        </div>
      		</li>
	    </c:when>
	    <c:otherwise>
		  <li class="nav-item active">
	        <a class="nav-link" href="ConnexionServlet">S'inscrire - Se connecter</a>
	      </li>
	    </c:otherwise>
	</c:choose>
    </ul>
  </div>
</nav>

