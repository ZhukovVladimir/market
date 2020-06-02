let json = null;
let hostName = "http://localhost:8080";

async function searchFetch(searchDto) {
    let response = await fetch(hostName + "/api/products/search", {
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

//init category buttons and top search panel
function initCategoryBtn() {
    //showing search bar
    let categoryButtons = document.getElementsByClassName("categoryBtn")

    //events for all category buttons
    for (let i = 0; i < categoryButtons.length; i++) {

        categoryButtons.item(i).onclick = function () {
            $("#searchPanel").removeClass("invisible");
            let searchDto = {
                "model": {
                    "category": {
                        "name": categoryButtons.item(i).value
                    }
                }
            }
            searchFetch(searchDto).then(() => {
                renderProducts(json);
            }, () => alert("try again"))
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
        "                        <img class=\"img-fluid\" src=\"http://localhost:8080/api/images/" + product.image.id + "\"></div>\n" +
        "                        <div class=\"info\">\n" +
        "                        <div class=\"name\">" + product.name + "</div>\n" +
        "                        <div class=\"price\">" + price + "</div>\n" +
        "                        <div class=\"desc\">\n" +
        "                            " + product.description + "\n" +
        "                            Color: " + product.color.name + "\n" +
        "                            Storage: " + product.memory.volume + "\n" +
        "                        </div>\n" +
        "                        <div class=\"buy\">\n" +
        "                            <button type=\"button\" value="+ product.id +" class=\"btn btn-primary btn-sm buybtn\">Buy</button>\n" +
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
}

function initByBtn() {
    let getCartsURL = hostName + "/api/orders";

    $.ajax({
        url: getCartsURL,
        success: function (data) {
            initOnClickBuy(data);
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
    cartContainer.innerHTML = "";
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

    if (cart.deliveryStatus === "PREORDER") {
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
                        initCartPage();
                    }
                })
            }
        }
        //confirm button
        cartContainer.insertAdjacentHTML("beforeend",
            "<div class=\"col my-auto\" >\n" +
            "        </div>\n"+
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

    } else {
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
                "            " + products[i].count + "\n" +
                "        </div>\n" +
                "        <div class=\"col my-auto\" >\n" +
                "        </div>\n" +
                "\n");
        }
    }
}

function initCartPage() {
    document.getElementById("cartContainer").innerHTML = "";
    let cartsURL = hostName + "/api/orders";
    let delStatDivs = document.getElementsByClassName("deliveryStatus");
    let confirmBtn = document.getElementById("confirmBtn");
    //remove confirm buttons and del status labels
    if (confirmBtn != null) confirmBtn.remove();
    for (let i = 0; i < delStatDivs.length; i++) {
        delStatDivs.item(i).remove();
    }
    $.ajax({
        url: cartsURL,
        success: function (data) {
            if (data[0] === "<") {
                window.location.href = hostName + "/login";
            } else {
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
        document.getElementById("productContainer").innerHTML = "";
        document.getElementById("globalCart").classList.remove("invisible");
        $("#searchPanel").addClass("invisible");
        $("#categoryBtnPanel").addClass("invisible");
        initCartPage();
    }
}

//render product page
function renderProductPage(product) {
    let productsDiv = document.getElementById("products");
    productsDiv.innerHTML="";
    productsDiv.insertAdjacentHTML("afterbegin", "<div class=\"col\">\n" +
        "                <div class=\"image\">\n" +
        "                    <img class=\"img-fluid product-img\" src=\"http://localhost:8080/api/images/"+ product.image.id +"\"/>\n" +
        "                </div>\n" +
        "                <div class=\"info\">\n" +
        "                    <div class=\"name\"> " + product.name + "</div>\n" +
        "                        <div class=\"price\">" + product.price + "</div>\n" +
        "                    <div class=\"desc\">\n" +
        "                        "+ product.description +"\n" +
        "                        Color: "+ product.color.name +"\n" +
        "                        Storage: "+ product.memory.volume +"\n" +
        "                    </div>\n" +
        "                    <div class=\"buy\">\n" +
        "                        <button type=\"button\" value="+ product.id +" class=\"btn btn-primary btn-sm buybtn\">Buy</button>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>")
    initByBtn();
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
                }
            })
        }
    }
}

$(document).ready(() => {
    //action for buy buttons
    initByBtn();
    //action for cart btn into top panel
    initCartBtn();
    //onclick product image
    initProductDesc();
    //onclick category button
    initCategoryBtn();
})