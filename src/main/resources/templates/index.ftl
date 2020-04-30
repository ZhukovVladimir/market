<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Apple Market</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css" />
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Дополнительные стили -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <!-- Плагин Popper -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous" ></script>
    <script src="../js/index.js"></script>
</head>
<body>

<!--top panel-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">SHOP</a>
    <a class="navbar-brand" href="/">profile</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <#list categories as category>
                <button type="button" class="btn btn-light" value=${category.name}> ${category.name}</button>
            </#list>
        </div>
    </div>
    <form class="form-inline">
        <input id="search_form" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
        <button id="search_button" class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
</nav>

<!--search panel-->
<nav id="searchPanel" class="navbar navbar-expand-lg navbar-light bg-light invisible">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse">
        <div class="navbar-nav">
            <#list categories as category>
                <a class="nav-item nav-link" href="">${category.name}</a>
            </#list>
        </div>
    </div>
</nav>

        <!--products-->
<div class="container">
        <div id="products" class="row row-cols-3 align-items-end">
            <#list products as product>
                <div class="col">
                    <div class="image">
                        <img class="img-fluid" src="http://localhost:8080/api/image/${product.imageId}"/>
                    </div>
                    <div class="info">
                        <div class="name"> ${product.name} </div>
                        <#if product.count gt 0>
                            <div class="price"> ${product.price} </div>
                        <#else>
                            <div class="not_available"> <b>Not available</b> </div>
                        </#if>
                        <div class="desc">
                            ${product.description}
                            Color: ${product.colorName}
                            Storage: ${product.memoryVolume}
                        </div>
                        <div class="buy">
                            <button type="button" id="buy_button" class="btn btn-primary btn-sm">Buy</button>
                        </div>
                    </div>
                </div>
        </#list>
        </div>
</div>

</body>
</html>