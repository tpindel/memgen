<#import "../common.ftl" as c/>
<@c.page title="upload a new figure">
    <form class="well form-inline" action="/figure/fromDisk" method="post" enctype="multipart/form-data">
      <label>max file size: 2MB</label>
      <input name="file" type="file" class="span4" placeholder="">
      <button type="submit" class="btn">Submit</button>
    </form>
</@c.page>
