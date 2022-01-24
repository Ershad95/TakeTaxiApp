<?php

	require "index.php";
	$respone= array();
	
	if(isset($_POST["token"]) && isset($_POST["status"])){
	
	$status= $_POST["status"];
	$token= $_POST["token"];

	
      $respone = array();
      $UsersUpdate= "UPDATE Users SET Status='$status' WHERE token='$token'";
    
      

      $result = mysqli_query($connection,$UsersUpdate);
      
      	    if($result)
            {
                $respone['Message']="تغییر انجام شد";
		$respone['Result']="1";
            }
            else
            {
                $respone['Message']="خطا : تغییر امکان پذیر نیست";
		$respone['Result']="0";
            }
      }
        else 
        {
            $respone['Message']="خطا:انجام شدنی نیست";
	    $respone['Result']="0";
        }
		echo json_encode($respone);
		mysqli_close($connection);

?>