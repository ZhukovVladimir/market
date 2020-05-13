let json = null;

async function searchFetch(searchDto) {
    let response = await fetch("http://localhost:8080/api/products/search", {
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
        "                        <img class=\"img-fluid\" src=\"http://localhost:8080/api/image/" + product.image.id + "\"></div>\n" +
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

$(document).ready(() => {

    //showing search bar
    let categoryButtons = document.getElementsByClassName("btn-light")

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
            }, () => alert("try again)"))
        };
    }
})