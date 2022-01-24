<?php
require "index.php";
$token = $_POST['token'];
function getMessages($t,$connection)
{
	$response=array();
	$q  =mysqli_query($connection,"select * from Messages where token= '$t'");
	if($q){
		
		while($row = mysqli_fetch_assoc($q))
		{
			$tmp['Address']=		$row['address'];
			$tmp['PassengerTel']=		$row['telephone'];
			$tmp['PassengerName'] = 	$row['name'];
			$tmp['Destination']=		$row['Destination'];
				
			array_push($response,$tmp);
	
		}
		return json_encode($response);
	}
	
}
	

if($token == "ShowAll")
{
	$response=array();
	$q  =mysqli_query($connection,"select * from Messages");
	if($q){
		
		while($row = mysqli_fetch_assoc($q))
		{
			$tmp['Address']=		$row['address'];
			$tmp['PassengerTel']=		$row['telephone'];
			$tmp['PassengerName'] = 	$row['name'];
			$tmp['Destination']=		$row['Destination'];
			$tmp['Time']=			$row['time'];
			$tmp['Token_accepted']=		$row['token'];
			$t = $row['token'];
			
			$qr  =mysqli_query($connection,"select lname from Users where token = '$t' ");
			
			if(mysqli_num_rows($qr)>0)
			{	
				$result = mysqli_fetch_array($qr);
				$tmp['Driver_accepted'] =$result['lname'];
			}else
			{
				$tmp['Driver_accepted'] = "Empty";
			}
			
			
			array_push($response,$tmp);
	
		}
		echo json_encode($response);
	}

	
}else
{
	echo getMessages($token,$connection);
}


	
	


mysqli_close($connection);
?>