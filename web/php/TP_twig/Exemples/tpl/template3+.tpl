<!DOCTYPE html>
<html>
    <head>
            <link rel="stylesheet" href="style.css" />
            <title>{% block title %} {{ titlepart }} {% endblock %} - Page Twig 	    </title>
    </head>
    <body>
        <div id="content">
		{% block content %} <p>{{contenu}}</p> {% endblock %}
	</div>
        <div id="footer">
            {% block footer %}
                &copy; Templates by Twig - {{ nom }} - {{ annee }}
            {% endblock %}
        </div>
    </body>
</html>
