{% extends "templateBase2.tpl" %}

{% block contenu %}
	<p>
        <table>
	  {% for item in items %}
	     <tr>
	       <td> {{ item.getNcli() }} </td>
	       <td> {{ item.getNp() }} </td>
	       <td> {{ item.getQa() }} </td>
	     </tr>
	  {% endfor %}
	</table>
        </p>
{% endblock %}