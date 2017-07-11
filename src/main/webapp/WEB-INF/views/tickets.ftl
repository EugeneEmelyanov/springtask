<html>
<head>
    <title>Tickets</title>
    <#include "resources/scripts.ftl">
</head>
<body>
    <#include "resources/navbar.ftl">
    <div class="page-header mt-4"><h1 class="text-center">List of tickets for
        <#if model.user??>
        <a href="/user/${model.user.id}">${model.user.name}</a>
        <#else>
        all users.
        </#if></h1></div>
        <div class="mt-5">
            <#if model.tickets?has_content>

            <table class="table">
                <thead class="thead">
                    <tr>
                        <th>Event</th>
                        <th>Date</th>
                        <th>Seats</th>
                        <th>User</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    <#list model["tickets"] as ticket>
                    <tr>
                        <td>
                            <div>${ticket.event.name}</div>
                            <div>
                                <a href="/event/?eventName=${ticket.event.name}">see other dates</a></div>
                            </td>
                            <td>${ticket.dateTime}</td>
                            <td>${ticket.seats}</td>
                            <td><a href="/user/${ticket.user.id}">${ticket.user.name}</a>
                                <td>${ticket.price}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                    <#else>
                    <div>
                        <label>No tickets were booked previously.</label>
                    </div>
                    </#if>
                </div>
            </body>
            </html>