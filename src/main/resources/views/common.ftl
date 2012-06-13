<#macro page title>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>${title?html}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="meme generator">
    <meta name="author" content="pks">

    <link href="/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="/assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

  </head>

  <body>
	  <div class="navbar navbar-fixed-top">
	    <div class="navbar-inner">
	      <div class="container">
	        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	        </a>
	        <a class="brand" href="#">memgen</a>
	        <div class="nav-collapse">
	          <ul class="nav">
	             <li class="active">
	             	<a href="#">Home</a>
  				 </li>
				 <li><a href="#">Link</a></li>
				 <li><a href="#">Link</a></li>
	          </ul>
	        </div>
	      </div>
	    </div>
	  </div>

	  <div class="container">
	    <div class="row">
	      <div class="span9">
	      	<!-- include body here -->
	      	<#nested/>
	      </div>
	      <div class="span3">
	        <h2>About Us</h2>
	        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
	      </div>
	    </div>
	  </div>
	  
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
    <script src="/assets/bootstrap/js/bootstrap.min.js"></script>
 </body>
</html>
</#macro>
