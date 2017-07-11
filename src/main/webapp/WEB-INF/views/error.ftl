<html>
<head>
    <title>Application error</title>
    <#include "resources/scripts.ftl">
</head>
<body>
    <#include "resources/navbar.ftl">
    <h1>Error Page</h1>
    <p>Application has encountered an error. Please contact support.</p>
    <br>
    <h3>Exception: ${ex.message}</h3>
    <br><br>
    <h3>Stack Trace</h3>
    <#list ex.stackTrace as ste>
    <div>${ste}</div>
    </#list>
</body>
</html>