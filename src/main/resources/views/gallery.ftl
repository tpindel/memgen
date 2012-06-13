<#import "common.ftl" as c/>
<@c.page title="memgen">
    <ul class="thumbnails">
        <#list memes as item>
        <li class="span2">
          <a href="#" class="thumbnail">
            <!-- <img src="http://placehold.it/250x250" alt=""> -->
            <img src="${item.url}" alt="">
          </a>
        </li>
        </#list>
    </ul>
</@c.page>
