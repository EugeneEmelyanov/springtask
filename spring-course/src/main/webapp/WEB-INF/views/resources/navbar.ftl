<nav class="navbar navbar-default">
<div class="container-fluid">
<div class="navbar-header">
<a class="navbar-brand" href="#">Advanced Spring Lab#1</a>
</div>
<ul class="nav navbar-nav">
<li><a href="/event/list">Events</a></li>
<li><a href="/auditorium/list">Auditoriums</a></li>
<li><a href="/user/list">Users</a></li>
<li><a href="/ticket/list">Tickets</a></li>
 <#if currentUser??>
  <li><h4>Welcome <span>${currentUser.name}</span></h4></li>
</#if>
</ul>
<a type="button" class="btn btn-info btn pull-right align-middle" style="margin: 5px 0" href="/logout">Logout</a>
<button type="button" class="btn btn-info btn pull-right" style="margin: 5px 10px" data-toggle="modal" data-target="#myModal">Upload Events and Users</button>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Upload events and users</h4>
      </div>
      <div class="modal-body">
          
          <div class="row">
            <div >
               <form id="upload_form" class="well" action="/upload" method="post" enctype="multipart/form-data">
                  <div class="form-group">
                    <label for="file">Select a file to upload</label>
                    <input type="file" name="file">
        
                  </div>
                  <input type="submit" class="btn btn-lg btn-primary" value="Upload">
                </form>
            </div>
    </div> <!-- /container -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>



</div>
</nav>

<script>
  $('#upload_form').submit(function(e){
    e.preventDefault();
    var form = $('#upload_form')[0];

    var data = new FormData(form);
    $.ajax({
        url:'/upload',
        type:'POST',
        enctype: 'multipart/form-data',
        processData: false,  // Important!
        contentType: false,
        cache: false,
        data:data,
        success:function(){
            $("#myModal").modal("hide");
        }
    });
});
</script>