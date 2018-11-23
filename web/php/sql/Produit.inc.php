<?php

// Classe achat
// labbeh, 2018

class Produit
{
      private $np;
      private $lib;
      private $coul;
      private $qs;


      
      public function __construct($n=-1, $l="", $c="", $q=-1)
      {
           $this->np = $n;
	     $this->lib = $l;
           $this->coul = $c;
           $this->qs = $q;
      }

      public function getNp  () { return $this->np  ; }
      public function getLib () { return $this->lib ; }
      public function getCoul() { return $this->coul; }
      public function getQs  () { return $this->qs  ; }

      public function __toString() {
      	     $res = "produit np:".$this->np."\n";
	     $res = $res ."<br/>";
	     return $res;
	     
      }
}