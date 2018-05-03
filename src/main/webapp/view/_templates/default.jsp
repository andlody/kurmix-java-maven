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
            <div class="row">
                <div class="col-lg-3">				
                    <jsp:include page='<%= v.partial("left") %>' />
                </div>
	<!-- body --> 
        <div class="col-lg-6">
            <div class="panel panel-body text-center">
                <jsp:include page="<%= v.body %>" />
            </div>
	</div>
	<!-- fin body -->        
                <div class="col-lg-3">				
                    <jsp:include page='<%= v.partial("right") %>' />
                </div>
            </div>		
	</div>

	<!-- footer -->
	<jsp:include page='<%= v.partial("footer") %>' />
        <!-- fin footer -->
    </body>
</html>