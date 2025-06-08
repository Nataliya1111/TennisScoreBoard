<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="filterParam" value="${not empty param.filter_by_player_name ? '&filter_by_player_name=' += param.filter_by_player_name : ''}" />

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches?page=1">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Matches</h1>
            <form class="input-container" method="get" action="${pageContext.request.contextPath}/matches">
                <input type="hidden" name="page" value="1" />

                <input
                        class="input-filter"
                        placeholder="Filter by name"
                        type="text"
                        name="filter_by_player_name"
                        value="${param.filter_by_player_name}" />
                <div>
                    <button class="btn-filter" type="submit">Filter</button>
                </div>
                <div>
                    <a href="matches?page=1">
                        <button class="btn-filter" type="button">Reset Filter</button>
                    </a>
                </div>
            </form>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="match" items="${matches_response_dto.matches}">
                <tr>
                    <td>${match.player1.name}</td>
                    <td>${match.player2.name}</td>
                    <td><span class="winner-name-td">${match.winner.name}</span></td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${not empty matches_response_dto.notFoundMessage}">
            <p style="color: blue;">${matches_response_dto.notFoundMessage}</p>
        </c:if>

        <div class="pagination">
            <c:if test="${matches_response_dto.currentPage > 1}">
                <a class="prev" href="matches?page=${matches_response_dto.currentPage - 1}${filterParam}">&lt;</a>
            </c:if>
            <c:if test="${matches_response_dto.currentPage == 1}">
                <a class="prev">&lt;</a>
            </c:if>

            <c:forEach var="page" items="${matches_response_dto.pagesToShow}">
                <c:choose>
                    <c:when test="${page == -1}">
                        <a class="num-page">...</a>
                    </c:when>
                    <c:when test="${page == matches_response_dto.currentPage}">
                        <a class="num-page current" href="#">${page}</a>
                    </c:when>
                    <c:otherwise>
                        <a class="num-page" href="matches?page=${page}${filterParam}">${page}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${matches_response_dto.currentPage < matches_response_dto.lastPage}">
                <a class="next" href="matches?page=${matches_response_dto.currentPage + 1}${filterParam}">&gt;</a>
            </c:if>
            <c:if test="${matches_response_dto.currentPage == matches_response_dto.lastPage}">
                <a class="next">&gt;</a>
            </c:if>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>