<html>
<head>
    <title>Users</title>
    <#include "resources/scripts.ftl">
</head>
<body>
    <#include "resources/navbar.ftl">
    <div class="page-header mt-4"><h1 class="text-center">User details</h1></div>
    <div class="container">
       <h4>Name: ${userAccount.user.name}</h4>
       <#if msg?has_content>
      <h4 class="label-danger label">${msg}</h4>
       </#if>
       <p>
           <i class="glyphicon glyphicon-envelope"></i>  ${userAccount.user.email}<br/>

           <i class="glyphicon glyphicon-gift"></i>  ${userAccount.user.birthday}</br>

            <#if currentUser??>
             <#if userAccount.user.id == currentUser.id>
                <h3>Your balance: <span id="currentBalance">${userAccount.prepaidMoney}</span>
                <a id="depositMoney" class="btn btn-primary" href="#">Add 200</a>
             </#if>
            </#if>

             <a class="btn btn-primary"
                  href="/ticket/${userAccount.user.id}">See booked tickets</a>
       </div>
 
    <#include "resources/deposit.ftl"> 
</body>
</html>