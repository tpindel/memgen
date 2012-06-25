<#import "common.ftl" as c/>
<@c.page title="upload meme">
    <form class="well form-inline" action="/upload" method="post">
      <label>Image's URL:</label>
      <input name="url" type="text" class="span4" placeholder="http://">
      <button type="submit" class="btn">Submit</button>
    </form>
</@c.page>
