<%@page import="_libs.kurmix.Views"%>
<% Views v = new Views(request); %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page='<%= v.partial("head") %>' />
    </head>
    <body>
  	<!-- navbar -->
	<jsp:include page='<%= v.partial("navbar") %>' />
	<!-- fin navbar -->
        
	<div class="container">            
            <div class="panel panel-body text-center">
                <h1> 404 - Página no Encontrada</h1>
            </div>			
	</div>

	<!-- footer -->
	<jsp:include page='<%= v.partial("footer") %>' />
        <!-- fin footer -->
    </body>
</html>

