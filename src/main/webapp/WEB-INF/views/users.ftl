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
            <#list model["users"] as user>
            <tbody class="tbody">
            <tr>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.birthday}</td>
                <td><a href="/user/${user.id}">link</a>
             </tr>
             </#list>
             </tbody>
            </table>
        </body>
        </html>