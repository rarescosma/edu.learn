<?php

// Business 1
var_dump( vc_optimize( array(
	'probability_of_success' => 0.05,
	'market_size' => 5000000000,
	'percent_of_market' => 0.2 / 100,
	'average_salary_per_person' => 100000,
	'headcount' => 3,
	'time_to_market' => 0.5
) ) );

// Business 2
var_dump( vc_optimize( array(
	'probability_of_success' => 0.05,
	'market_size' => 10000000000,
	'percent_of_market' => 15 / 100,
	'average_salary_per_person' => 50000,
	'headcount' => 30,
	'time_to_market' => 3
) ) );


function vc_optimize( $args = array() ) {
	$defaults = array(
		'probability_of_success' => 0,
		'market_size' => 0,
		'percent_of_market' => 5,
		'average_salary_per_person' => 50000,
		'headcount' => 5,
		'time_to_market' => 1
	);

	extract( array_replace( $defaults, $args ) );

	$up = ( $probability_of_success * $market_size * $percent_of_market );
	$down = ( $average_salary_per_person * $headcount * $time_to_market );

	if( 0 === $down )
		return false;

	return pow( $up / $down, 1 / $time_to_market );
}
