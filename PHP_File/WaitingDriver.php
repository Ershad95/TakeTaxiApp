<?php
require "index.php";
$response=array();
$q  =mysqli_query($connection,"select * from Users where status = 'waiting'");
	if($q){
		
		while($row = mysqli_fetch_assoc($q))
		{
			$tmp['token']=		$row['token'];
			$tmp['name']=		$row['name'];
			$tmp['lname']=		$row['lname'];
			$tmp['username'] = 	$row['email'];
			$tmp['password']=	$row['pass'];
			$tmp['status']=		$row['status'];
			
			
			array_push($response,$tmp);
	
		}
		echo json_encode($response);
	}
	
	mysqi_close($connection);

?>