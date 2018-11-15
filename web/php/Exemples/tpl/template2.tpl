<html>
    <!-- ... -->
    <body>
        <h1>{{ msg|e }}</h1>
        <p>
        <table>
	  {% for item in items %}
	     <tr>
	       <td> {{ item }} </td>
	     </tr>
	  {% endfor %}
	</table>
        </p>
    </body>
</html>