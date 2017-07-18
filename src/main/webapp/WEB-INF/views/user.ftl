<html>
<head>
    <title>Users</title>
    <#include "resources/scripts.ftl">
</head>
<body>
    <#include "resources/navbar.ftl">
    <div class="page-header mt-4"><h1 class="text-center">User details</h1></div>
    <div class="container">
       <h4>Name: ${model.userAccount.user.name}</h4>
       <p>
           <i class="glyphicon glyphicon-envelope"></i>  ${model.userAccount.user.email}<br/>

           <i class="glyphicon glyphicon-gift"></i>  ${model.userAccount.user.birthday}</br>

             <a class="btn btn-primary"
                  href="/ticket/${model.userAccount.user.id}">See booked tickets</a>
       </div>

</body>
</html>