    <html>
    <head>
        <title>User list</title>
        <#include "resources/scripts.ftl">
    </head>
    <body>
        <#include "resources/navbar.ftl">
        <table class="table">
            <thead class="thead">
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Birthday</th>
            </tr>
            </thead>
            <#list model["userAccounts"] as userAccount>
            <tbody class="tbody">
            <tr>
                <td>${userAccount.user.name}</td>
                <td>${userAccount.user.email}</td>
                <td>${userAccount.user.birthday}</td>
                <td><a href="/user/${userAccount.user.id}">See details</a>
             </tr>
             </#list>
             </tbody>
            </table>
        </body>
        </html>