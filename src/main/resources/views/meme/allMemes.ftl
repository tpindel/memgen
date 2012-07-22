<#import "../common.ftl" as c/>
<@c.page title="memgen">
    <ul class="thumbnails" id="memesList">
        <#list memes as item>
        <li class="span2">
          <a href="/meme/${item.id}" class="thumbnail">
            <img src="${item.url}" alt="">
          </a>
        </li>
        </#list>
    </ul>
</@c.page>