<html>
<head>
    <title>Auditorium</title>
    <#include "resources/scripts.ftl">
</head>
<body>
    <#include "resources/navbar.ftl">
    <div class="page-header mt-4"><h1 class="text-center">Auditorium details
    </h1></div>

    <#list model["auditoriums"] as auditorium>
    <div class="container">
        <h4>Name: ${auditorium.name}</h4>
        <div class="mt-4">Number of seats: ${auditorium.seatsNumber}</div>
        <div class="pt-4">Vip seats: ${auditorium.vipSeats}</div>
        <a class="btn pt-4 btn-primary"
        href="/event?auditoriumName=${auditorium.name}">See events in ${auditorium.name}</a>
    </div>
    </#list>

</body>
</html>