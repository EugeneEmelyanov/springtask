<html>
<head>
    <title>Events</title>
    <#include "resources/scripts.ftl">
</head>
<body>
    <#include "resources/navbar.ftl">
    <div class="page-header mt-4"><h1 class="text-center">List of events</h1></div>
    <div class="mt-5">
        <#if model.events?has_content>
        <table class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Rate</th>
                    <th>Base Price</th>
                    <th>Auditorium</th>
                    <th>Date Time</th>
                </tr>
            </thead>
            <tbody>
                <#list model["events"] as event>
                <tr>
                    <td>${event.name}
                        <div>
                        <a href="/event/?eventName=${event.name}">see other dates</a></div>
                        </td>
                        <td>${event.rate}</td>
                        <td>${event.basePrice}</td>
                        <td><a href="/auditorium/list?name=${event.auditorium.name}">${event.auditorium.name}</a></td>
                        <td>${event.dateTime}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
            </#if>
        </div>
    </body>
    </html>