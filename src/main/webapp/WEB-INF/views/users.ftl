    <html>
    <head>
        <title>User list</title>
        <#include "resources/scripts.ftl">
    </head>
    <body>
        <#include "resources/navbar.ftl">
        <h1>${(currentUser.name)!"Not logged in"}</h1>
        <table class="table">
            <thead class="thead">
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Birthday</th>
                <th>Balance</th>
                <th>Actions</th>
            </tr>
            </thead>
            <#list userAccounts as userAccount>
            <tbody class="tbody">
            <tr>
                <td>${userAccount.user.name}</td>
                <td>${userAccount.user.email}</td>
                <td>${userAccount.user.birthday}</td>
                <td>
                    <#if currentUser??>
                        <#if userAccount.user.id == currentUser.id>
                            Balance: ${userAccount.prepaidMoney}
                        </#if>
                    <#else>
                        Not available
                    </#if>
                </td>
                <td><a href="/user/${userAccount.user.id}">See details</a></td>
             </tr>
             </#list>
             </tbody>
            </table>
        </body>
        </html>