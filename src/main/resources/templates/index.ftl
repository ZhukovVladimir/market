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
    <script src="../js/admin.js"></script>
</head>
<body>

<!--top panel-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">SHOP</a>
    <button type="button" id="cart_button" class="btn btn-light" value="">Cart <span id="badge" class="badge">0</span></button>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div id="categoryBtnPanel" class="navbar-nav">
            <#list categories as category>
                <button type="button" class="btn btn-light categoryBtn" id="${category.name}Button" value=${category.name}> ${category.name}</button>
            </#list>
        </div>
    </div>
    <form class="form-inline">
        <input id="search_form" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
        <button id="search_button" class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
    <form class="form-inline" method="post" action="/logout">
        <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="Logout"/>
    </form>
</nav>

<!--search panel-->
<nav id="searchPanel" class="navbar navbar-expand-lg navbar-light bg-light invisible">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse">
        <div id="searchFilters" class="navbar-nav">

        </div>
    </div>
</nav>

        <!--products-->
<div id="productContainer" class="container">
    <div id="products" class="row row-cols-3 align-items-end">
        <#list 0..5 as j>
            <#if products[j]??>
                <div id="product${products[j].id}Div" class="col">
                    <div class="image">
                        <img id="${products[j].id}" class="img-fluid product-img" src="http://localhost:8080/api/images/${products[j].image.id}"/>
                    </div>
                    <div class="info">
                        <div class="name"> ${products[j].name} </div>
                        <#if products[j].count gt 0>
                            <div class="price"> ${products[j].price} </div>
                        <#else>
                            <div class="not_available"> <b>Not available</b> </div>
                        </#if>
                        <div class="desc">
                            ${products[j].description}
                            Color: ${products[j].color.name}
                            Storage: ${products[j].memory.volume}
                        </div>
                        <div class="buy">
                            <button type="button" value=${products[j].id} class="btn btn-primary btn-sm buybtn">Buy</button>
                        </div>
                    </div>
                </div>
            </#if>
        </#list>
    </div>
    <nav id="pages" aria-label="Page navigation example">
        <ul id="pagesButtons" class="pagination justify-content-center">
            <li class="page-item"><button id="prevPageBtn" class="page-link">Previous</button></li>
            <#list 0..(products?size-1)/6 as i>
                <li id="page${i}" class="page-item num-page-item"><button id="pageNumberBtn" class="page-link num-link" value="${i}">${i}</button></li>
            </#list>
            <li class="page-item"><button id="nextPageBtn" class="page-link">Next</button></li>
        </ul>
    </nav>
</div>


        <!-- cart -->
<div id="globalCart" class="container invisible">
    <div id="cartContainer" class="row row-cols-5 deliveryProducts">
        <div class="col" >
            <img class="img-thumbnail cart_prod_img" src="ссылка на фото"/>
        </div>
        <div class="col" >
            Название
        </div>
        <div class="col" >
            Цена
        </div>
        <div class="col" >
            Количество
        </div>
        <div class="col" >
            <button type="button" class="btn btn-light">Delete</button>
        </div>

    </div>
</div>

</body>
</html>