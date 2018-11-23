{% extends "templateBase2.tpl" %}

{% block contenu %}
	<p>
        <table>
	  {% for item in items %}
	     <tr>
	       <td> {{ item.getNp() }} </td>
	       <td> {{ item.getLib() }} </td>
	       <td> {{ item.getCoul() }} </td>
	       <td> {{ item.getQs() }} </td>
	     </tr>
	  {% endfor %}
	</table>
        </p>
{% endblock %}