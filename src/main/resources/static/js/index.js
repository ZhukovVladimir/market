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
        "                            <button type=\"button\" id=\"buy_button\" class=\"btn btn-primary btn-sm\">Buy</button>\n" +
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

function renderCart(cart) {
    let cartContainer = document.getElementById("cartContainer");
    let globalCart = document.getElementById("globalCart");
    //let cartDiv = document.createElement('div');
    let products = cart.products;
    for (let i = 0; i < products.length; i++) {
        cartContainer.insertAdjacentHTML("afterbegin",
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
            "        <p><input id=\"inputCount" + products[i].product.id +"\" type=\"number\"  size=\"3\" min=\"1\" max=" + products[i].product.count + " value=" + products[i].count + "></p> \n" +
            "        </div>\n" +
            "        <div class=\"col my-auto\" >\n" +
            "            <button id=\"deleteBtn" + products[i].product.id +"\" type=\"button\" class=\"btn btn-light\">Delete</button>\n" +
            "        </div>\n" +
            "\n");
        //change count
        document.getElementById("inputCount"+products[i].product.id).addEventListener('input', function (e) {
            let changeCount = e.target.value - products[i].count;
            if (changeCount > 0) {
                let addProductURL = hostName + "/api/orders/add?cartId=" + cart.id + "&count=" + changeCount + "&productId=" + products[i].product.id;
                $.ajax({
                    url: addProductURL,
                    method: "POST",
                    success: function () {
                        products[i].count = products[i].count + changeCount;
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
                    }
                })
            }
        })
        //remove products
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
    globalCart.insertAdjacentHTML("afterbegin", "    <div class=\"deliveryStatus\"> " + cart.deliveryStatus + " </div>\n");
}

function initCartPage() {
    document.getElementById("cartContainer").innerHTML = "";
    let cartsURL = hostName + "/api/orders";
    let delStatDivs = document.getElementsByClassName("deliveryStatus");

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

    cartBtn.onclick = function () {
        document.getElementById("productContainer").innerHTML = "";
        document.getElementById("globalCart").classList.remove("invisible");
        $("#searchPanel").addClass("invisible");
        $("#categoryBtnPanel").addClass("invisible");
        initCartPage();
    }
}

$(document).ready(() => {
    //action for buy buttons
    initByBtn();
    initCartBtn();

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
})