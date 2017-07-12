<html>
<head>
   <title>Log in</title>
   <#include "resources/scripts.ftl">
   <link rel="stylesheet" type="text/css" href="/resources/static/login.css">
</head>
<body>

   <div class="container">
      <form class="form-signin" method="POST" action="perform_login">
         <h2 class="form-signin-heading">Please log in</h2>
         <label for="inputEmail" class="sr-only">Email address</label>
         <input id="inputEmail" name="username" class="form-control" placeholder="Email address" required autofocus>
         <label for="inputPassword" class="sr-only">Password</label>
         <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
         <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me">Remember me
         </label>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
   </form>

</div> <!-- /container -->

</body>
</html>