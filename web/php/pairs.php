<?php   // affiche les nombres de 0 à 100 inclus
             for ($i=0 ; $i <= 100 ; $i++)
             {
    			if($i % 2 == 0)
             	{
                   print ( $i );

                   if ($i % 8 == 0) print ("<br>"	);
                   else 			print ("   -   ");
                }
              }
?>