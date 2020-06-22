let json = null;
let hostName = "http://localhost:8080";
let currentPage = 0;
let activeColor;
let activeCategory;
let activeMemory;
let activeAvailable;
let activeModel;
let maxPrice;
let minPrice;
let prodNameForDto;
let prodDescForDto;
let prodPriceForDto;
let prodCountForDto;
let sort = "creationTime";

async function searchFetch(searchDto, pageNum) {
    let response = await fetch(hostName + "/api/products/search?page=" + pageNum + "&sort=" + sort, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(searchDto)
    });
    if (response.ok) {
        json = await response.json();
    } else {
        alert(response.status);
    }
}

async function pageFetch(pageNum) {
    let response = await fetch(hostName + "/api/products?page=" + pageNum, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    });
    if (response.ok) {
        json = await response.json();
    } else {
        alert(response.status);
    }
}

//init category buttons and top search panel
function initCategoryBtn() {
    //showing search bar
    let categoryButtons = document.getElementsByClassName("categoryBtn")

    //events for all category buttons
    for (let i = 0; i < categoryButtons.length; i++) {

        categoryButtons.item(i).onclick = function () {
            activeCategory = categoryButtons.item(i).value
            currentPage = 0;
            $("#searchPanel").removeClass("invisible");
            let searchDto = {
                "model": {
                    "category": {
                        "name": activeCategory
                    }
                },
                "color": {
                    "name": activeColor
                },
                "memory": {
                    "volume": activeMemory
                },
                "maxPrice": maxPrice,
                "minPrice": minPrice,
                "available": activeAvailable
            }
            //pagination for category pages
            $.ajax({
                url: hostName + "/api/products/search?page=0" + "&sort=" + sort,
                method: "POST",
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(searchDto),
                success: function (data) {

                    let numberOfPages = data.totalPages;
                    let pagesBtns = document.getElementById("pagesButtons");
                    //update page buttons
                    pagesBtns.innerHTML = "";
                    let newPageBtns = "<li class=\"page-item\"><button id=\"prevPageBtn\" class=\"page-link\">Previous</button></li>";
                    for (let i = 0; i < numberOfPages; i++) {
                        newPageBtns = newPageBtns + "<li id=\"page"+i+"\" class=\"page-item num-page-item\"><button id=\"pageNumberBtn\" class=\"page-link num-link\" value=\"" + i + "\">" + i + "</button></li>";
                    }
                    newPageBtns = newPageBtns + "<li class=\"page-item\"><button id=\"nextPageBtn\" class=\"page-link\">Next</button></li>";
                    pagesBtns.innerHTML = newPageBtns;

                    //onclick on page buttons
                    let pageNumBtn = document.getElementsByClassName("page-link");
                    for (let i = 0; i < pageNumBtn.length; i++) {
                        if (pageNumBtn[i] !== null) {
                            $(pageNumBtn[i]).bindFirst('click', function () {
                                if (pageNumBtn[i].id === "prevPageBtn" || pageNumBtn[i].id === "nextPageBtn") {
                                    if (!(currentPage <= 0 && pageNumBtn[i].id === "prevPageBtn")) {
                                        if (!(currentPage >= pageNumBtn.length - 3 && pageNumBtn[i].id === "nextPageBtn")) {

                                            if (pageNumBtn[i].id === "prevPageBtn") currentPage--;
                                            if (pageNumBtn[i].id === "nextPageBtn") currentPage++;
                                        }
                                    }
                                } else {
                                    currentPage = pageNumBtn[i].value;
                                }
                                searchFetch(searchDto, currentPage).then(() => {
                                    renderProducts(json);
                                }, () => alert("try again"))
                            });
                        }
                    }

                    searchFetch(searchDto, currentPage).then(() => {
                        renderProducts(json);
                        initCurrentPage();
                    }, () => alert("try again"))
                }
            })

        };
    }
}

//create a div with data
function createDivWithProduct(product) {
    let div = document.createElement("div");
    div.className = "col";
    let price = product.price;
    if (product.count < 1) {
        price = "<b>Not available</b>"
    }
    div.insertAdjacentHTML("afterbegin", "<div class=\"image\">\n" +
        "                        <img class=\"img-fluid product-img\" id=\""+product.id+"\" src=\"http://localhost:8080/api/images/" + product.image.id + "\"></div>\n" +
        "                        <div class=\"info\">\n" +
        "                        <div class=\"name\">" + product.name + "</div>\n" +
        "                        <div class=\"price\">" + price + "</div>\n" +
        "                        <div class=\"desc\">\n" +
        "                            " + product.description + "\n" +
        "                            Color: " + product.color.name + "\n" +
        "                            Storage: " + product.memory.volume + "\n" +
        "                        </div>\n" +
        "                        <div class=\"buy\">\n" +
        "                            <button type=\"button\" value=" + product.id + " class=\"btn btn-primary btn-sm buybtn\">Buy</button>\n" +
        "                        </div>\n" +
        "                    </div>")
    return div;
}

//insert products into container
function renderProducts(resp) {
    let products = resp.content;
    document.getElementById("products").innerHTML = "";
    for (let i = 0; i < products.length; i++) {
        let product = products[i];
        document.getElementById("products").append(createDivWithProduct(product))
    }
    initByBtn();
    initProductDesc();
    //if user == admin
    initAdminBtn();
}

function initByBtn()    {
    let getCartsURL = hostName + "/api/orders";

    $.ajax({
        url: getCartsURL,
        success: function (data) {
            if (!((data.length === 0) || (data[0] === "<"))) {
                data.reverse();
                initOnClickBuy(data);
            }
        }
    });
}

//action for buy buttons
function initOnClickBuy(carts) {
    let buyButtons = document.getElementsByClassName("buybtn");

    let cartId = carts[0].id;

    for (let i = 0; i < buyButtons.length; i++) {
        buyButtons.item(i).onclick = function () {
            let addProductURL = hostName + "/api/orders/add?cartId=" + cartId + "&count=1&productId=" + this.value;
            $.ajax({
                url: addProductURL,
                method: "POST",
                success: function (data) {
                    if (data[0] !== "<") {
                        let count = $("#badge")[0].textContent;
                        $("#badge")[0].textContent = parseInt(count) + 1;
                    }
                }
            });
        }
    }
}

//init cart to order
function confirmCart(cart) {
    let cartContainer = document.getElementById("cartContainer");
    let delOrderCont = document.getElementById("orderContainer");
    if (delOrderCont !== null) delOrderCont.innerHTML = "";
    let products = cart.products;
    cartContainer.innerHTML = "";
    //render preorder cart
    //todo should be function (DRY)
    //render products if status = preorder
    for (let i = 0; i < products.length; i++) {
        cartContainer.insertAdjacentHTML("beforeend",
            "   <div class=\"col my-auto\" >\n" +
            "        <img class=\"img-thumbnail cart_prod_img\" src=\"http://localhost:8080/api/images/" + products[i].product.image.id + "\"></div>\n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "            " + products[i].product.name + "\n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "            " + products[i].product.price + "\n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "        <p><input id=\"inputCount" + products[i].product.id + "\" type=\"number\"  size=\"3\" min=\"1\" max=" + products[i].product.count + " value=" + products[i].count + "></p> \n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "            <button id=\"deleteBtn" + products[i].product.id + "\" type=\"button\" class=\"btn btn-light\">Delete</button>\n" +
            "        </div>\n" +
            "\n");
        //change count
        document.getElementById("inputCount" + products[i].product.id).addEventListener('input', function (e) {
            let totalPrice = document.getElementById("totalPrice");
            let badge = document.getElementById("badge");
            let changeCount = e.target.value - products[i].count;
            if (changeCount > 0) {
                let addProductURL = hostName + "/api/orders/add?cartId=" + cart.id + "&count=" + changeCount + "&productId=" + products[i].product.id;
                $.ajax({
                    url: addProductURL,
                    method: "POST",
                    success: function () {
                        cart.products[i].count = cart.products[i].count + changeCount;
                        badge.textContent = parseInt(badge.textContent) + changeCount;
                        confirmCart(cart);
                    }
                })
            }
            if (changeCount <= 0) {
                let reduceProductUrl = hostName + "/api/orders/reduce?cartId=" + cart.id + "&count=" + Math.abs(changeCount) + "&productId=" + products[i].product.id;
                $.ajax({
                    url: reduceProductUrl,
                    method: "PUT",
                    success: function () {
                        cart.products[i].count = cart.products[i].count + changeCount;
                        badge.textContent = parseInt(badge.textContent) + changeCount;
                        confirmCart(cart);
                    }
                })
            }
        })
        //delete products
        document.getElementById("deleteBtn" + products[i].product.id).onclick = function () {
            let deleteProduct = hostName + "/api/orders/delete?cartId=" + cart.id + "&productId=" + products[i].product.id;
            $.ajax({
                url: deleteProduct,
                method: "DELETE",
                success: function () {
                    let badge = document.getElementById("badge");
                    badge.textContent = parseInt(badge.textContent) - cart.products[i].count;
                    cart.products.splice(i, 1);
                    confirmCart(cart);
                }
            })
        }
    }

    //setup order info
    let orderContainer = document.createElement("div");
    orderContainer.id = "orderContainer";
    orderContainer.classList.add("container");
    cartContainer.insertAdjacentElement("afterend", orderContainer);

    let bill = cart.products.reduce(function (sum, current) {
        return sum + current.product.price * current.count;
    }, 0);

    cart.bill = bill;

    //form for order info
    let infoRowDiv = document.createElement("div");
    infoRowDiv.id = "infoRowDiv";
    infoRowDiv.classList.add("row");
    infoRowDiv.classList.add("row-cols-4");

    let address = "";
    if (cart.deliveryAddress !== null) {
        address = "value=\"" + cart.deliveryAddress + "\"";
    }

    orderContainer.insertAdjacentElement("afterbegin", infoRowDiv);
    infoRowDiv.insertAdjacentHTML("afterbegin",
        "        <div class=\"col text-center\" >\n" +
        "        </div>\n" +
        "        <div class=\"col text-center\" >\n" +
        "        <input id=\"infoAddress\" required type=\"text\" placeholder=\"address\" " + address + " > \n" +
        "        </div>\n" +
        "        <div id=\"totalPrice\" class=\"col text-center\" >\n" +
        "        Total price: " + bill + " \n" +
        "        </div>\n" +
        "        <div class=\"col text-center\" >\n" +
        "        <button id=\"confirmOrderBtn\" type=\"button\" class=\"btn btn-dark\">Confirm</button>\n" +
        "        </div>\n");

    //onclick confirmOrderBtn
    let confirmOrderBtn = document.getElementById("confirmOrderBtn");
    confirmOrderBtn.onclick = function () {
        let confirmOrderURL = hostName + "/api/orders/confirm"
        cart.deliveryAddress = document.getElementById("infoAddress").value;
        //let cartDto = JSON.stringify(cart);
        $.ajax({
            url: confirmOrderURL,
            method: "PUT",
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(cart),
            success: function () {
                initCartPage();
            }
        });
    }

}

function renderCart(cart) {
    let cartContainer = document.getElementById("cartContainer");
    //let globalCart = document.getElementById("globalCart");
    let products = cart.products;
    //render delivery status
    if (cart.products.length > 0) {
        cartContainer.insertAdjacentHTML("beforeend",
            "    <div class=\"deliveryStatus\"> " + cart.deliveryStatus + " </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "        </div>\n"
        );
    }
    //render preorder cart
    if (cart.deliveryStatus === "PREORDER") {
        //render products if status = preorder
        for (let i = 0; i < products.length; i++) {
            cartContainer.insertAdjacentHTML("beforeend",
                "   <div class=\"col my-auto\" >\n" +
                "        <img class=\"img-thumbnail product-img cart_prod_img\" id=\"" + products[i].product.id + "\" src=\"http://localhost:8080/api/images/" + products[i].product.image.id + "\"></div>\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "            " + products[i].product.name + "\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "            " + products[i].product.price + "\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "        <p><input id=\"inputCount" + products[i].product.id + "\" type=\"number\"  size=\"3\" min=\"1\" max=" + products[i].product.count + " value=" + products[i].count + "></p> \n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "            <button id=\"deleteBtn" + products[i].product.id + "\" type=\"button\" class=\"btn btn-light\">Delete</button>\n" +
                "        </div>\n" +
                "\n");
            //change count
            document.getElementById("inputCount" + products[i].product.id).addEventListener('input', function (e) {
                let badge = document.getElementById("badge");
                let changeCount = e.target.value - products[i].count;
                if (changeCount > 0) {
                    let addProductURL = hostName + "/api/orders/add?cartId=" + cart.id + "&count=" + changeCount + "&productId=" + products[i].product.id;
                    $.ajax({
                        url: addProductURL,
                        method: "POST",
                        success: function () {
                            products[i].count = products[i].count + changeCount;
                            badge.textContent = parseInt(badge.textContent) + changeCount;
                        }
                    })
                }
                if (changeCount <= 0) {
                    let reduceProductUrl = hostName + "/api/orders/reduce?cartId=" + cart.id + "&count=" + Math.abs(changeCount) + "&productId=" + products[i].product.id;
                    $.ajax({
                        url: reduceProductUrl,
                        method: "PUT",
                        success: function () {
                            products[i].count = products[i].count + changeCount;
                            badge.textContent = parseInt(badge.textContent) + changeCount;
                        }
                    });
                }
            })
            //delete products
            document.getElementById("deleteBtn" + products[i].product.id).onclick = function () {
                let deleteProduct = hostName + "/api/orders/delete?cartId=" + cart.id + "&productId=" + products[i].product.id;
                $.ajax({
                    url: deleteProduct,
                    method: "DELETE",
                    success: function () {
                        let badge = document.getElementById("badge");
                        badge.textContent = parseInt(badge.textContent) - products[i].count;
                        initCartPage();
                    }
                })
            }
        }
        //confirm button
        if (cart.products.length > 0) {
            cartContainer.insertAdjacentHTML("beforeend",
                "<div class=\"col my-auto\" >\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "        </div>\n" +
                "        <div class=\"text-center\" >\n" +
                "        <button id=\"confirmBtn\" type=\"button\" class=\"btn btn-dark\">Confirm</button>\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "        </div>\n"
            );

            let confirmBtn = document.getElementById("confirmBtn");
            confirmBtn.onclick = function () {
                //render order page (confirm the cart)
                confirmCart(cart);
            }
        }

    } else {
        for (let i = 0; i < products.length; i++) {
            cartContainer.insertAdjacentHTML("beforeend",
                "   <div class=\"col my-auto\" >\n" +
                "        <img class=\"img-thumbnail product-img cart_prod_img\"  id=\"" + products[i].product.id + "\" src=\"http://localhost:8080/api/images/" + products[i].product.image.id + "\"></div>\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "            " + products[i].product.name + "\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "            " + products[i].product.price + "\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "            " + products[i].count + "\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "        </div>\n" +
                "\n");
        }
    }
    initProductDesc();
}

function initCartPage() {
    document.getElementById("cartContainer").innerHTML = "";
    let cartsURL = hostName + "/api/orders";
    let delStatDivs = document.getElementsByClassName("deliveryStatus");
    let confirmBtn = document.getElementById("confirmBtn");
    let orderInfoContainer = document.getElementById("orderContainer");
    //remove confirm buttons and del status labels
    if (confirmBtn != null) confirmBtn.remove();
    for (let i = 0; i < delStatDivs.length; i++) {
        delStatDivs.item(i).remove();
    }
    //remove order info
    if (orderInfoContainer != null) orderInfoContainer.remove();
    $.ajax({
        url: cartsURL,
        success: function (data) {
            if (data[0] === "<") {
                window.location.href = hostName + "/login";
            } else {
                data.reverse();
                for (let i = 0; i < data.length; i++) {
                    renderCart(data[i]);
                }
            }
        }
    })
}

//init cart btn
function initCartBtn() {
    let cartBtn = document.getElementById("cart_button");
    let cartsURL = hostName + "/api/orders";
    let badgeCart = document.getElementById("badge");
    let count = 0;
    $.ajax({
        url: cartsURL,
        success: function (data) {
            if (data[0] === "<") {

            } else {
                //init count on cart button
                for (let i = 0; i < data.length; i++) {
                    if (data[i].deliveryStatus === "PREORDER") {
                        for (let j = 0; j < data[i].products.length; j++) {
                            count = count + data[i].products[j].count;
                        }
                    }
                }
            }
            badgeCart.textContent = count;
        }
    })

    cartBtn.onclick = function () {
        document.getElementById("products").innerHTML = "";
        document.getElementById("globalCart").classList.remove("invisible");
        $("#searchPanel").addClass("invisible");
        $("#categoryBtnPanel").addClass("invisible");
        let pages = document.getElementById("pages");
        if (pages !== null) pages.remove();
        initCartPage();
    }
}

//render product page
function renderProductPage(product) {
    let productsDiv = document.getElementById("products");
    let pages = document.getElementById("pages");
    let cartCintainer = document.getElementById("cartContainer");
    if (cartCintainer !== null) cartCintainer.innerHTML = "";
    if (pages !== null) pages.remove();
    if (productsDiv !== null) {
        productsDiv.innerHTML = "";
        productsDiv.insertAdjacentHTML("afterbegin", "<div class=\"col\">\n" +
            "                <div class=\"image\">\n" +
            "                    <img class=\"img-fluid product-img\" src=\"http://localhost:8080/api/images/" + product.image.id + "\"/>\n" +
            "                </div>\n" +
            "                <div class=\"info\">\n" +
            "                    <div class=\"name\"> name: " + product.name + "</div>\n" +
            "                        <div class=\"price\"> price: " + product.price + "</div>\n" +
            "                    <div class=\"desc\">\n" +
            "                        desc: " + product.description + "\n" +
            "                    </div>\n" +
            "                    <div class=\"color\">\n" +
            "                        Color: " + product.color.name + "\n" +
            "                    </div>\n" +
            "                    <div class=\"storage\">\n" +
            "                        Storage: " + product.memory.volume + "\n" +
            "                    </div>\n" +
            "                    </div>\n" +
            "                    <div class=\"buy\">\n" +
            "                        <button type=\"button\" value=" + product.id + " class=\"btn btn-primary btn-sm buybtn\">Buy</button>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>")
        initByBtn();
    }
}

//init click on product img
function initProductDesc() {
    let getProdUrl = hostName + "/api/products/";
    let productImages = document.getElementsByClassName("product-img");
    for (let i = 0; i < productImages.length; i++) {
        productImages[i].onclick = function () {
            $.ajax({
                url: getProdUrl + productImages[i].id,
                method: "GET",
                success: function (data) {
                    renderProductPage(data);
                    //if user == admin
                    initAdminBtn();
                    $("#categoryBtnPanel").remove();
                }
            })
        }
    }
}

//pagination for main page
function initPageBtn() {
    let pageNumBtn = document.getElementsByClassName("page-link");

    for (let i = 0; i < pageNumBtn.length; i++) {
        $(pageNumBtn[i]).bind('click',function () {
            if (pageNumBtn[i].id === "prevPageBtn" || pageNumBtn[i].id === "nextPageBtn") {

                if (!(currentPage <= 0 && pageNumBtn[i].id === "prevPageBtn")) {
                    if (!(currentPage >= pageNumBtn.length - 3 && pageNumBtn[i].id === "nextPageBtn")) {

                        if (pageNumBtn[i].id === "prevPageBtn") currentPage--;
                        if (pageNumBtn[i].id === "nextPageBtn") currentPage++;
                    }
                }
            } else {
                currentPage = pageNumBtn[i].value;
            }
            pageFetch(currentPage).then(() => {
                renderProducts(json);
            }, () => alert("try again"))
        });
    }
}

//init Color
function initColor(searchFiltersDiv) {

    let colorButtonDiv = document.getElementById("colorButtons");
    if (colorButtonDiv !== null) colorButtonDiv.innerHTML = "";
    let getColorsUrl = hostName + "/api/dictionary/colors";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "<div class=\"dropdown show\">\n" +
        //"  <label for=\"colorButton\">Color </label>" +
        "  <button class=\"btn bg-light dropdown-toggle\" type=\"button\" id=\"colorButton\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">\n" +
        "    Color\n" +
        "  </button>\n" +
        "  <div class=\"dropdown-menu\" id=\"colorButtons\" aria-labelledby=\"dropdownMenuButton\">\n" +
        "  </div>\n" +
        "</div>");

    $.ajax({
        url: getColorsUrl,
        method: "GET",
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                colorButtonDiv = document.getElementById("colorButtons");
                colorButtonDiv.insertAdjacentHTML("beforeend",
                    "<button value=\"" + data[i].name + "\" id=\"" + data[i].name + "Color\" class=\"dropdown-item color-button\">" + data[i].name[0].toUpperCase() + data[i].name.slice(1) + "</button>\n"
                );
                let actionColorBtn = document.getElementById(data[i].name + "Color");
                actionColorBtn.onclick = function () {
                    document.getElementById("colorButton").textContent =  data[i].name[0].toUpperCase() + data[i].name.slice(1) ;
                    activeColor = data[i].name;
                };
            }
        }
    });
}

//init memory
function initMemory(searchFiltersDiv) {
    let memoryButtonDiv = document.getElementById("memoryButtons");
    if (memoryButtonDiv !== null) memoryButtonDiv.innerHTML = "";
    let getMemoryUrl = hostName + "/api/dictionary/storage";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "<div class=\"dropdown show\">\n" +
        //"  <label for=\"memoryButton\">Memory </label>" +
        "  <button class=\"btn bg-light dropdown-toggle\" type=\"button\" id=\"memoryButton\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">\n" +
        "    Memory\n" +
        "  </button>\n" +
        "  <div class=\"dropdown-menu\" id=\"memoryButtons\" aria-labelledby=\"dropdownMenuButton\">\n" +
        "  </div>\n" +
        "</div>");

    $.ajax({
        url: getMemoryUrl,
        method: "GET",
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                memoryButtonDiv = document.getElementById("memoryButtons");
                memoryButtonDiv.insertAdjacentHTML("beforeend",
                    "<button value=\"" + data[i].volume + "\" id=\"" + data[i].volume + "Memory\" class=\"dropdown-item memory-button\">" + data[i].volume + "</button>\n"
                );
                let actionMemoryBtn = document.getElementById(data[i].volume + "Memory");
                actionMemoryBtn.onclick = function () {
                    document.getElementById("memoryButton").textContent = data[i].volume;
                    activeMemory = data[i].volume;
                };
            }
        }
    });
}

//init Model
function initModel(searchFiltersDiv) {
    let modelButtonDiv = document.getElementById("modelButtons");
    if (modelButtonDiv !== null) modelButtonDiv.innerHTML = "";
    let getModelUrl = hostName + "/api/dictionary/models";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "<div class=\"dropdown show\">\n" +
        //"  <label for=\"memoryButton\">Memory </label>" +
        "  <button class=\"btn bg-light dropdown-toggle\" type=\"button\" id=\"modelButton\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">\n" +
        "    Model\n" +
        "  </button>\n" +
        "  <div class=\"dropdown-menu\" id=\"modelButtons\" aria-labelledby=\"dropdownMenuButton\">\n" +
        "  </div>\n" +
        "</div>");

    $.ajax({
        url: getModelUrl,
        method: "GET",
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                modelButtonDiv = document.getElementById("modelButtons");
                modelButtonDiv.insertAdjacentHTML("beforeend",
                    "<button value=\"" + data[i].id + "\" id=\"" + data[i].name + "Model\" class=\"dropdown-item model-button\">" + data[i].name + "</button>\n"
                );
                let actionModelBtn = document.getElementById(data[i].name + "Model");
                actionModelBtn.onclick = function () {
                    document.getElementById("modelButton").textContent = data[i].name;
                    activeModel = data[i].name;
                };
            }
        }
    });
}

function initSort(searchFiltersDiv) {
    let sortButtonDiv = document.getElementById("sortButtons");
    if (sortButtonDiv !== null) sortButtonDiv.innerHTML = "";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "<div class=\"dropdown show\">\n" +
        "  <button class=\"btn bg-light dropdown-toggle\" type=\"button\" id=\"sortButton\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">\n" +
        "    Order By\n" +
        "  </button>\n" +
        "  <div class=\"dropdown-menu\" id=\"sortButtons\" aria-labelledby=\"dropdownMenuButton\">\n" +
        "  <button value=\"creationTime\" id=\"creationTimeOrder\" class=\"dropdown-item order-button\">Date</button>\n" +
        "  <button value=\"price\" id=\"priceOrder\" class=\"dropdown-item order-button\">Price</button>\n" +
        "  </div>\n" +
        "</div>");
        let actionOrderBtn = document.getElementById("creationTimeOrder");
        actionOrderBtn.onclick = function () {
            document.getElementById("sortButton").textContent = "Date";
            sort = "creationTime";
        };

        actionOrderBtn = document.getElementById("priceOrder");
        actionOrderBtn.onclick = function () {
            document.getElementById("sortButton").textContent = "Price";
            sort = "Price";
        };
}

//init max Price
function initMaxPrice(searchFiltersDiv) {
    let maxPriceInput = document.getElementById("maxPrice");
    if (maxPriceInput !== null) maxPriceInput.innerHTML = "";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "  <label class=\"my-auto\" for=\"maxPrice\">Max price </label>" +
        "<p class=\"my-auto\"><input id=\"maxPrice\" type=\"number\" min=\"0\" step=\"100\"></p> \n"
    );
    document.getElementById("maxPrice").addEventListener('input', function (e) {
        maxPrice = e.target.value;
    })
}

//init max Price
function initMinPrice(searchFiltersDiv) {
    let minPriceInput = document.getElementById("minPrice");
    if (minPriceInput !== null) minPriceInput.innerHTML = "";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "  <label class=\"my-auto\" for=\"minPrice\">Min price </label>" +
        "<p class=\"my-auto\"><input id=\"minPrice\" type=\"number\" min=\"0\" step=\"100\"></p> \n"
    );
    document.getElementById("minPrice").addEventListener('input', function (e) {
        minPrice = e.target.value;
    })
}

//init checkbox
function initCheckBoxAv(searchFiltersDiv) {
    let availableCheckBox = document.getElementById("availableCheckBox");
    if (availableCheckBox !== null) availableCheckBox.innerHTML = "";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "<div class=\"my-auto\">" +
        "<label class=\"my-auto\" for=\"availableCheckBox\">Available</label>" +
        "<input type=\"checkbox\" class=\"my-auto\" id=\"availableCheckBox\">" +
        "</div>"
    );
    document.getElementById("availableCheckBox").onclick = function () {
        activeAvailable = this.checked;
    }
}

//init Apply btn
function initApply(searchFiltersDiv) {
    let applyBtn = document.getElementById("applyBtn");
    if (applyBtn !== null) applyBtn.innerHTML = "";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "<button class=\"btn btn-secondary\" type=\"button\" id=\"applyBtn\">\n" +
        "    Apply\n" +
        "  </button>\n"
    );

    document.getElementById("applyBtn").onclick = function () {
        document.getElementById(activeCategory+"Button").click();
    }
}

//init Clear
function initClearBtn(searchFiltersDiv) {
    let clearBtn = document.getElementById("clearBtn");
    if (clearBtn !== null) clearBtn.innerHTML = "";
    searchFiltersDiv.insertAdjacentHTML("beforeend", "<button class=\"btn btn-secondary\" type=\"button\" id=\"clearBtn\">\n" +
        "    Clear\n" +
        "  </button>\n"
    );

    document.getElementById("clearBtn").onclick = function () {

        let searchFiltersDiv = document.getElementById("searchFilters");
        if (searchFiltersDiv !== null) searchFiltersDiv.innerHTML = "";

        activeColor = undefined;
        activeMemory = undefined;
        activeAvailable = undefined;
        maxPrice = undefined;
        minPrice = undefined;
        sort = "creationTime";

        initColor(searchFiltersDiv);
        initMemory(searchFiltersDiv);
        initMinPrice(searchFiltersDiv);
        initMaxPrice(searchFiltersDiv);
        initCheckBoxAv(searchFiltersDiv);
        initSort(searchFiltersDiv);
        initApply(searchFiltersDiv);
        initClearBtn(searchFiltersDiv);

        document.getElementById(activeCategory+"Button").click();

    }
}

function initSearchFilters() {
    let searchFiltersDiv = document.getElementById("searchFilters");
    if (searchFiltersDiv !== null) searchFiltersDiv.innerHTML = "";

    //initColors
    initColor(searchFiltersDiv);

    //initMemory
    initMemory(searchFiltersDiv);

    //initPrice
    initMinPrice(searchFiltersDiv);
    initMaxPrice(searchFiltersDiv);

    //initCheckBox
    initCheckBoxAv(searchFiltersDiv);

    //initSort
    initSort(searchFiltersDiv);

    //init Apply
    initApply(searchFiltersDiv);

    //init Clear
    initClearBtn(searchFiltersDiv);
}

//init current number of page
function initCurrentPage() {
    $(".num-page-item:eq(0)").addClass("active");
    $(".num-page-item").on("click", function () {
        $(".num-page-item").each(function () {
            $(this).removeClass("active");
        })
        this.classList.add("active");
    })

    $("#prevPageBtn").on("click", function () {
        if (!$(".num-page-item:eq(0)").hasClass("active")) {
           $("#page"+(currentPage + 1)).removeClass("active");
           $("#page"+currentPage).addClass("active");
        }
    })

    $("#nextPageBtn").on("click", function () {
        if (currentPage !== ($(".page-item").length - 2)) {
            $("#page"+(currentPage - 1)).removeClass("active");
            $("#page"+currentPage).addClass("active");
        }
    })
}

$(document).ready(() => {
    //onclick page button
    initPageBtn();
    //action for buy buttons
    initByBtn();
    //action for cart btn into top panel
    initCartBtn();
    //onclick product image
    initProductDesc();
    //onclick category button
    initCategoryBtn();
    //initFilters
    initSearchFilters();
    //initCurrentPage
    initCurrentPage();
    //initAdminBtn
    initAdminBtn();
})