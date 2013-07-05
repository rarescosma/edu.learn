<?php


function countsort( $a, $n ) {
    if( $n == 1 ) return $a;

    $nb = $n >> 1; $nc = $n - $nb;

    $b = array_slice( $a, 0, $nb );
    $c = array_slice( $a, -$nc );

    $b = countsort( $b, $nb );
    $c = countsort( $c, $nc );

    return countmerge( $b, $c, $nb, $nc );
}

function countmerge( $b, $c, $nb, $nc ) {
    $i = $j = 0; $max = $nb + $nc;
    $d = array();

    for( $k = 0; $k < $max; $k++ ) {
        if( $j >= $nc or ( $i < $nb and $b[$i] <= $c[$j] ) ) {
            $d[] = $b[$i];
            $i++;
        } else {
            $d[] = $c[$j];
            $j++;
            global $inv;
            $inv += ( $nb - $i );
        }
    }

    return $d;
}

$time_start = microtime(true);

$a = file('IntegerArray.txt');
function toInt($x) {
    return (int)$x;
}
$a = array_map( 'toInt', $a );
$n = count( $a );
$inv = 0;
$d = countsort( $a, $n );

$time_end = microtime(true);
$time = $time_end - $time_start;

echo 'inversions: ' . $inv . "\n";
echo 'time: ' . $time . "\n";

?>