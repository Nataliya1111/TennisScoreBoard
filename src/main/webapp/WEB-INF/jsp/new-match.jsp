    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Tennis Scoreboard | New Match</title>
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
                <div>
                    <h1>Start new match</h1>
                    <div class="new-match-image"></div>
                    <div class="form-container center">
                        <form method="post" action="${pageContext.request.contextPath}/new-match">
                            <c:if test="${not empty Error_message}">
                                <p style="color: red;">${Error_message}</p>
                            </c:if>
                            <label class="label-player" for="player1Name">Player one</label>
                            <input class="input-player" placeholder="Name" value="${player1Name != null ? player1Name : ''}" type="text" name="player1Name" id="player1Name" required title="Enter a name">
                            <label class="label-player" for="player2Name">Player two</label>
                            <input class="input-player" placeholder="Name" value="${player2Name != null ? player2Name : ''}" type="text" name="player2Name" id="player2Name" required title="Enter a name">
                            <input class="form-button" type="submit" value="Start">
                        </form>
                    </div>
                </div>
            </div>
        </main>
        <footer>
            <div class="footer">
                <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
            </div>
        </footer>
        </body>
        </html>

