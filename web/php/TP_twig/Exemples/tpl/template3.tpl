<html>
    <!-- ... -->
    <body>
       <?php
	//ne produit pas de résultat
         $bonjour = "bienvenue";
         echo $bonjour;
       ?>
        <h1>----TITRE------</h1>
        essai<br/>
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
    </body>
</html>
