<?php

// Classe achat
// labbeh, 2018

class Achat
{
      private $ncli;
      private $np;
      private $qa;


      
      public function __construct($i=-1,$n=-1,$a=-1)
      {
           $this->ncli = $i;
	     $this->np = $n;
	     $this->qa = $a;
      }

      public function getNcli() { return $this->ncli; }
      public function getNp() { return $this->np;}
      public function getQa() { return $this->qa; }

      public function __toString() {
      	     $res = "achat ncli:".$this->ncli."\n";
	     $res = $res ."<br/>";
	     return $res;
	     
      }
}