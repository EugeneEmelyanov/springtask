<html>
<head>
    <title>Events</title>
    <#include "resources/scripts.ftl">
</head>
<body>
    <#include "resources/navbar.ftl">
    <div class="page-header mt-4"><h1 class="text-center">${model.eventTickets.event.name} tickets: </h1></div>
    <div class="row">
        <div class="col-sm-6 col-lg-6 pl-10 pr-10">
            <div class="bg-info" style="margin:0 10px">
                <div class="container-fluid">
                    <h4>Number of regular seats available: ${model.eventTickets.numRegularSeats}</h4>
                    <h4>Price for 1 regular ticket: ${model.eventTickets.regularPrice}</h4>
                    <a class="btn btn-info" href="/booking/book?eventName=${model.eventTickets.event.name}&auditoriumName=${model.eventTickets.event.auditorium.name}&eventDate=${model.eventTickets.event.dateTime}&seats=<#list model.eventTickets["regularSeats"] as seat>${seat}<#break></#list>">Buy Regular ticket</a>
                    <a class="btn btn-info" href="/booking/book?eventName=${model.eventTickets.event.name}&auditoriumName=${model.eventTickets.event.auditorium.name}&eventDate=${model.eventTickets.event.dateTime}&seats=<#list 0..model.eventTickets["regularSeats"]?size-1 as seat_ord>${model.eventTickets.regularSeats[seat_ord]}<#if seat_ord==4><#break><#else>,</#if></#list>">Buy 5 Regular tickets</a>
                    <p>List of available seats:
                    <div class="container-fluid small text-alert" style="word-wrap: break-word;">
                      <#list model.eventTickets["regularSeats"] as seat>${seat}&nbsp; </#list>
                    </div>
                </div>
            </div>   
        </div>
        <div class="col-sm-6 col-lg-6" >
            <div class="bg-info container-fluid" style="margin: 0 0 10px 0">
                <div class="container-fluid">
                    <h4>Number of VIP seats available: ${model.eventTickets.numVipSeats}</h4>
                    <h4>Price for 1 VIP ticket: ${model.eventTickets.vipPrice}</h4>
                    <a class="btn btn-info" href="/booking/book?eventName=${model.eventTickets.event.name}&auditoriumName=${model.eventTickets.event.auditorium.name}&eventDate=${model.eventTickets.event.dateTime}&seats=<#list model.eventTickets["vipSeats"] as seat>${seat}<#break></#list>">Buy VIP ticket</a>
                    <a class="btn btn-info" href="/booking/book?eventName=${model.eventTickets.event.name}&auditoriumName=${model.eventTickets.event.auditorium.name}&eventDate=${model.eventTickets.event.dateTime}&seats=<#list 0..model.eventTickets["vipSeats"]?size-1 as seat_ord>${model.eventTickets.vipSeats[seat_ord]}<#if seat_ord==4><#break><#else>,</#if></#list>">Buy 5 VIP tickets</a>
                    <p>List of available seats:
                    <div class="container-fluid small text-alert" style="word-wrap: break-word;">
                      <#list model.eventTickets["vipSeats"] as seat>
                         ${seat}&nbsp;</#list>
                    </div>
               </div>
            </div>
        </div>
    </div>
</body>
</html>