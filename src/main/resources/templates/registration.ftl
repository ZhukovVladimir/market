<!DOCTYPE html>
<html lang="en">
<head>
    <style type="text/css">
        .wrapper {
            background-color: whitesmoke;
            list-style-type: none;
            padding: 0;
            border-radius: 3px;
        }
        .form-row {
            display: flex;
            justify-content: flex-end;
            padding: .5em;
        }
        .form-row > label {
            padding: .5em 1em .5em 0;
            flex: 1;
        }
        .form-row > input {
            flex: 2;
        }
        .form-row > input,
        .form-row > button {
            padding: .5em;
        }
        .form-row > button {
            background: gray;
            color: white;
            border: 0;
        }
    </style>

    <meta charset="UTF-8">
    <title>Apple Market</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Дополнительные стили -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css"
          integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <!-- Плагин Popper -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card card-signin my-5">
                <div class="card-body">
                    <h5 class="card-title text-center">Регистрация</h5>
                    <form method="post" action="/registration" id="registerForm" class="form-signin">
                        <ul class="wrapper">
                            <li class="form-row">
                                <label for="username">Email: </label>
                                <input id="email" type="email" required name="username"/>
                            </li>
                            <li class="form-row">
                                <label for="password">Пароль: </label>
                                <input type="password" required name="password"/><br/>
                            </li>
                            <li class="form-row">
                                <label for="firstName">Имя: </label>
                                <input type="text" required name="firstName"/><br/>
                            </li>
                            <li class="form-row">
                                <label for="lastName">Фамилия: </label>
                                <input type="text" required name="lastName"/><br/>
                            </li>
                            <li class="form-row">
                                <label for="address">Адрес: </label>
                                <input type="text" required name="address"/><br/>
                            </li>
                            <li class="form-row">
                                <label for="phoneCountryCode">Код телефона: </label>
                                <input type="number" min="1" max="999"  required name="phoneCountryCode"/><br/>
                            </li>
                            <li class="form-row">
                                <label for="phone">Номер телефона: </label>
                                <input type="number" required name="phone"/><br/>
                            </li>
                            <li class="form-row">
                                <input type="submit" value="Зарегистрироваться"/>
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    let username;
    let inputUsername = document.getElementById("email");
    inputUsername.addEventListener('change', function (e) {
        username = e.target.value;
        $.ajax({
            url: "http://localhost:8080/user/exist?username=" + username,
            success: function (data) {
                if(data === true) {
                    alert("К сожалению, пользователь с таким именем уже существует");
                    e.target.value = "";
                }
            }
        })
    })

</script>

</body>
</html>