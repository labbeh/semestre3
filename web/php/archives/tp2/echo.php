<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<?php

/*
	foreach($_REQUEST as $key => $value){
		echo "$key = $value <br />";
	}
*/	
	/*
	print_r($_GET);
	*/
	//echo(" avec print_r<br>\n");
	foreach($_REQUEST as $key => $value){
		echo "$key: ";
		print_r($value);
		echo " <br />";
	}

?>
</body>
</html>
