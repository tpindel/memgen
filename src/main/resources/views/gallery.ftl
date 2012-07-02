<#import "common.ftl" as c/>
<@c.page title="memgen">
    <ul class="thumbnails" id="memeList">
        <#list memes as item>
        <li class="span2">
          <a href="/edit/${item.id}" class="thumbnail">
            <img src="${item.url}" alt="">
          </a>
        </li>
        </#list>
    </ul>
</@c.page>
