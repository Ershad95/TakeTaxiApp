<?php
include("index.php");



$id = $_POST["token"];


$response['messages']=array();


	
	$q  =mysqli_query($connection,"select * from Users where token = '$id'");
	if($q){
		
			$row = mysqli_fetch_assoc($q);
			
			$tmp1['name']=		$row['name'];
			$tmp1['lname']=		$row['lname'];
			$tmp1['username'] = 	$row['email'];
			$tmp1['password']=		$row['pass'];
			$tmp1['status']=	$row['status'];
			
			$response['info']=$tmp1;
			
		
			
			
	}
	
	$r  =mysqli_query($connection,"select * from Messages where token= '$id'");
	if($r){
		
		while($row = mysqli_fetch_assoc($r))
		{
			$tmp2['Address']=		$row['address'];
			$tmp2['PassengerTel']=		$row['telephone'];
			$tmp2['PassengerName'] = 	$row['name'];
			$tmp2['Destination'] = 		$row['Destination'];
				
			array_push($response['messages'],$tmp2);
	
		}
	
	}
	echo json_encode($response);


?>