{% extends "templateBase2.tpl" %}

{% block contenu %}
	<p>
        <table>
	  {% for item in items %}
	     <tr>
	       <td> {{ item.getIdcli() }} </td>
	       <td> {{ item.getNom() }} </td>
	       <td> {{ item.getAdresse() }} </td>
	     </tr>
	  {% endfor %}
	</table>
        </p>
{% endblock %}