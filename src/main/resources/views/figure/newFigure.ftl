<#import "../common.ftl" as c/>
<@c.page title="upload a new figure">
    <form class="well form-inline" action="/figure" method="post">
      <label>Image's URL:</label>
      <input name="url" type="text" class="span4" placeholder="http://">
      <button type="submit" class="btn">Submit</button>
    </form>
</@c.page>
