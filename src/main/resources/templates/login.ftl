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
                    <h5 class="card-title text-center">Sign In</h5>
                    <p><a href="/register">Register</a></p>
                    <form method="post" enctype="application/x-www-form-urlencoded" action="/login" id="loginForm" class="form-signin">
                        <ul class="wrapper">
                            <li class="form-row">
                                <label for="username">Email: </label>
                                <input type="email" name="username" id="username"/>
                            </li>
                            <li class="form-row">
                                <label for="password">Password: </label>
                                <input type="password" name="password" id="password"/>
                            </li>
                            <li class="form-row">
                                <input type="submit" value="Login"/>
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>