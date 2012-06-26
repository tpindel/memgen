<#import "common.ftl" as c/>
<@c.page title="memgen">
	<img src="${url}" alt="">
	<form class="well form-inline" name="memGen" method="post" action="/edit">
	    <input name="id" type="hidden" value="${id}">
		<label>Top title:</label>
		<input name="topTitle" type="text" class="span3" placeholder="Type top title …">
		<label>Bottom title:</label>
    	<input name="bottomTitle" type="text" class="span3" placeholder="Type bottom title …">
    	<button type="submit" class="btn btn-primary">Generate</button>
	</form>
	
</@c.page>