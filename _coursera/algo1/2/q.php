<?php

new QuickSort();

class QuickSort {
    public $arr = array();
    //public $comp = 0;

    function __construct() {
        echo '<pre>';

        $a = file('IntegerArray.txt');
        function toInt($x) { return (int)$x; }

        $this->arr = array_map( 'toInt', $a );

        $c = count( $this->arr );
        $this->quicksort( 0, $c - 1 );

        $bound = ceil( $c * log( $c, 2 ) );
        echo "Upper bound: <b>{$bound}</b><br/>";
        echo "Comparisons: <b>{$this->comp}</b><br /><br />";

        echo '</pre>';
    }

    function quicksort( $l, $r ) {
        // Accumulate comparisons
        $this->comp += ( $r - $l );

        // Chose pivot
        $p = $this->choosepivot( $l, $r );
        $pivot = $this->arr[$p];

        // Move pivot on first position
        $this->swap( $l, $p );

        // Partition
        $i = $l + 1;
        for( $j = $i; $j <= $r; $j++ ) {
            if( $this->arr[$j] < $pivot ) {
                $this->swap( $i, $j );
                $i++;
            }
        }

        // Put pivot back in its rightful position
        $this->swap( $l, $i-1 );

        // Recurse on 1st half
        if( $i - 2 > $l ) $this->quicksort( $l, $i-2 );

        // Recurse on 2nd half
        if( $r > $i ) $this->quicksort( $i, $r );
    }

    function choosepivot( $l, $r ) {
        if( $r - $l == 1 ) return $l;

        // Pivot is first element
        //return $l;

        // Pivot is last element
        //return $r;

        // Flip coins
        //return rand( $l, $r );

        // Median rule

        //$m = floor( ($l + $r) / 2 );
        $m = rand( $l+1, $r-1 );

        $left = $this->arr[$l];
        $right = $this->arr[$r];
        $center = $this->arr[$m];

        $a = $left;
        $b = $right;
        $c = $center;

        // if( $left > $center ) $this->swap( $l, $m );
        // if( $left > $right ) $this->swap( $l, $r );
        // if( $center > $right ) $this->swap( $m, $r );

        // return $m;

        if( ( $c < $a and $a < $b ) or ( $b < $a and $a < $c ) ) return $l;
        if( ( $a < $b and $b < $c ) or ( $c < $b and $b < $a ) ) return $r;
        if( ( $b < $c and $c < $a ) or ( $a < $c and $c < $b ) ) return $m;
    }

    function swap( $x, $y ) {
        $t = $this->arr[$x];
        $this->arr[$x] = $this->arr[$y];
        $this->arr[$y] = $t;
    }

}

?>