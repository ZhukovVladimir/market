function initAdminBtn() {
    let getRoleUrl = hostName + "/user";
    $.ajax({
        url: getRoleUrl,
        method: "GET",
        success: function (data) {
            if (data !== "") {
                if (data.authorities[0].authority === "ROLE_ADMIN") {
                    initAdminPage();
                }
            }
        }
    })
}

function initAdminPage() {
    $("#badge").remove();

    btnsForAdmin();
}

function btnsForAdmin() {
    let addBtn = document.getElementById("cart_button");
    if (addBtn !== null) {
        addBtn.id = "addProductBtn";
        addBtn.textContent = "Add Product";
        addBtn.onclick = function () {
            //onclick add product button
            initAddProductPage();
        }
    }

    let editBtns = document.getElementsByClassName("buybtn");
    for (let i = 0; i < editBtns.length; i++) {
        editBtns[i].textContent = "Edit";
        editBtns[i].onclick = function () {
            //onclick edit product
            initEditProductPage(this.value);
        }
    }
}

function initAddProductPage() {
    $("#categoryBtnPanel").remove();
    $("#search_form").remove();
    $("#search_button").remove();
    $("#searchPanel").remove();
    $("#globalCart").remove();
    let productContainer = document.getElementById("productContainer");
    productContainer.innerHTML = "";

    productContainer.insertAdjacentHTML("afterbegin", "<div id=\"products\" class=\"row row-cols-1 text-center\">\n" +
        "<div class=\"col\">\n" +
        "<input id=\"inputImg\" type=\"file\" name=\"image\" accept=\".jpg, .jpeg, .png\">\n" +
        "</div>\n" +
        "</div>");

    let productsDiv = document.getElementById("products");
    initColorInput(productsDiv);
    initMemoryInput(productsDiv);
    initModelInput(productsDiv);
    initNameInput(productsDiv);
    initDescInput(productsDiv);
    initPriceInput(productsDiv);
    initCountInput(productsDiv);
    initConfirmBtn(productsDiv);

}

function initModelInput(searchFiltersDiv) {
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
                    activeModel = data[i].id;
                };
            }
        }
    });
}

function initColorInput(searchFiltersDiv) {

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
                    "<button value=\"" + data[i].id + "\" id=\"" + data[i].name + "Color\" class=\"dropdown-item color-button\">" + data[i].name[0].toUpperCase() + data[i].name.slice(1) + "</button>\n"
                );
                let actionColorBtn = document.getElementById(data[i].name + "Color");
                actionColorBtn.onclick = function () {
                    document.getElementById("colorButton").textContent = data[i].name[0].toUpperCase() + data[i].name.slice(1);
                    activeColor = data[i].id;
                };
            }
        }
    });
}

function initMemoryInput(searchFiltersDiv) {
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
                    "<button value=\"" + data[i].id + "\" id=\"" + data[i].volume + "Memory\" class=\"dropdown-item memory-button\">" + data[i].volume + "</button>\n"
                );
                let actionMemoryBtn = document.getElementById(data[i].volume + "Memory");
                actionMemoryBtn.onclick = function () {
                    document.getElementById("memoryButton").textContent = data[i].volume;
                    activeMemory = data[i].id;
                };
            }
        }
    });
}

function initNameInput(productsDiv) {
    let nameButtonDiv = document.getElementById("nameInput");
    if (nameButtonDiv !== null) nameButtonDiv.innerHTML = "";
    productsDiv.insertAdjacentHTML("beforeend", "<input id=\"createProdName\" required type=\"text\" placeholder=\"Name\">");
    document.getElementById("createProdName").addEventListener('input', function (e) {
        prodNameForDto = e.target.value;
    });
}

function initDescInput(productsDiv) {
    let descButtonDiv = document.getElementById("descInput");
    if (descButtonDiv !== null) descButtonDiv.innerHTML = "";
    productsDiv.insertAdjacentHTML("beforeend", "<input id=\"createProdDesc\" required type=\"text\" placeholder=\"Description\">");
    document.getElementById("createProdDesc").addEventListener('input', function (e) {
        prodDescForDto = e.target.value;
    });
}

function initPriceInput(productsDiv) {
    let priceButtonDiv = document.getElementById("priceInput");
    if (priceButtonDiv !== null) priceButtonDiv.innerHTML = "";
    productsDiv.insertAdjacentHTML("beforeend", "<input id=\"createProdPrice\" required type=\"number\" step=\"100\" placeholder=\"Price\">");
    document.getElementById("createProdPrice").addEventListener('input', function (e) {
        prodPriceForDto = e.target.value;
    });
}

function initCountInput(productsDiv) {
    let countButtonDiv = document.getElementById("countInput");
    if (countButtonDiv !== null) countButtonDiv.innerHTML = "";
    productsDiv.insertAdjacentHTML("beforeend", "<input id=\"createProdCount\" required type=\"number\" placeholder=\"Count\">");
    document.getElementById("createProdCount").addEventListener('input', function (e) {
        prodCountForDto = e.target.value;
    });
}

function initConfirmBtn(productsDiv) {
    let createProdUrl = hostName + "/api/admin/products"
    let confirmBtn = document.getElementById("confirmBtn");
    if (confirmBtn !== null) confirmBtn.innerHTML = "";
    productsDiv.insertAdjacentHTML("beforeend", "<button class=\"btn btn-secondary\" type=\"button\" id=\"confirmBtn\">\n" +
        "    Confirm\n" +
        "  </button>\n"
    );

    document.getElementById("confirmBtn").onclick = function () {
        //send img
        let inputImg = document.getElementById("inputImg");
        let formData = new FormData();
        formData.append("image", inputImg.files[0])
        $.ajax({
            url: "http://localhost:8080/api/admin/images",
            method: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (data) {
                let createDto = {
                    "model": {
                        "id": activeModel
                    },
                    "color": {
                        "id": activeColor
                    },
                    "memory": {
                        "id": activeMemory
                    },
                    "price": prodPriceForDto,
                    "count": prodCountForDto,
                    "description": prodDescForDto,
                    "name": prodNameForDto,
                    "image": {
                        "id": data.id
                    }
                }
                $.ajax({
                    url: createProdUrl,
                    method: "POST",
                    contentType: 'application/json',
                    dataType: 'json',
                    data: JSON.stringify(createDto),
                    success: function () {
                        alert("Created");
                    },
                    error: function () {
                        alert("All fields are required");
                    }
                });
            },
            error: function () {
                alert("Ошибка при загрузке изображения")
            }
        })
    }
}

function initUpdateBtn(productsDiv, idProd) {
    let updateProdUrl = hostName + "/api/admin/products"
    let updateBtn = document.getElementById("updateBtn");
    if (updateBtn !== null) updateBtn.innerHTML = "";
    productsDiv.insertAdjacentHTML("beforeend", "<button class=\"btn btn-secondary\" type=\"button\" id=\"updateBtn\">\n" +
        "    Update\n" +
        "  </button>\n"
    );

    document.getElementById("updateBtn").onclick = function () {

        let updateDto = {
            "model": {
                "id": activeModel
            },
            "color": {
                "id": activeColor
            },
            "memory": {
                "id": activeMemory
            },
            "price": prodPriceForDto,
            "count": prodCountForDto,
            "description": prodDescForDto,
            "name": prodNameForDto,
            "image": {
                "id": parseInt(document.getElementsByClassName("product-img")[0].src.split("/")[5])
            }
        }
        $.ajax({
            url: updateProdUrl + "/" + idProd,
            method: "PUT",
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(updateDto),
            success: function () {
                alert("Updated");
            }
        })
    }
}

function initDeleteBtn(productsDiv, idProd) {
    let deleteProdUrl = hostName + "/api/admin/products"
    let deleteBtn = document.getElementById("deleteBtn");
    if (deleteBtn !== null) deleteBtn.innerHTML = "";
    productsDiv.insertAdjacentHTML("beforeend", "<button class=\"btn btn-danger\" type=\"button\" id=\"deleteBtn\">\n" +
        "    Delete\n" +
        "  </button>\n"
    );

    document.getElementById("deleteBtn").onclick = function () {

        $.ajax({
            url: deleteProdUrl + "/" + idProd,
            method: "DELETE",
            success: function () {
                alert("Deleted");
            }
        })


    }
}

function initEditProductPage(idProd) {
    let getProdUrl = hostName + "/api/products/";
    let products = document.getElementById("products");
    $("#searchPanel").remove();
    products.classList.remove("row-cols-3");
    products.classList.add("row-cols-2");
    $("#categoryBtnPanel").remove();
    $.ajax({
        url: getProdUrl + idProd,
        method: "GET",
        success: function (data) {
            renderProductPage(data);
            activeColor = data.color.id;
            activeMemory = data.memory.id;
            activeModel = data.model.id;
            prodNameForDto = data.name;
            prodDescForDto = data.description;
            prodPriceForDto = data.price;
            prodCountForDto = data.count;
            document.getElementsByClassName("info")[0].insertAdjacentHTML("beforeend",
                "<div class=\"model\"> model: " + data.model.name + "</div>");
            $(".buy").remove();
            //test
            products.insertAdjacentHTML("afterend", "<div id=\"newProd\" class=\"row row-cols-1 text-center\"></div>")
            let productsDiv = document.getElementById("newProd");
            initColorInput(productsDiv);
            initMemoryInput(productsDiv);
            initModelInput(productsDiv);
            initNameInput(productsDiv);
            initDescInput(productsDiv);
            initPriceInput(productsDiv);
            initCountInput(productsDiv);
            initUpdateBtn(productsDiv, idProd);
            initDeleteBtn(productsDiv, idProd);
        }
    })

}

$.fn.bindFirst = function (name, fn) {
    this.on(name, fn);

    this.each(function () {
        let handlers = $._data(this, 'events')[name.split('.')[0]];
        let handler = handlers.pop();
        handlers.splice(0, 0, handler);
    });
};