<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard</title>
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
            <span class="logo-text">TennisScoreboard</span>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>HTTP Status ${status}</h1>
        <p>${message}</p>
        <div class="error-page">
            <img src="${pageContext.request.contextPath}/images/error_cat.png" alt="Error Cat" class="error-image">
        </div>
    </div>
</main>
</body>
</html>