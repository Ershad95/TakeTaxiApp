<?php

require "index.php";
$email = $_POST["email"];
$pass = $_POST["pass"];
$token = $_POST["token"];
$respone = array();

      $check = "SELECT id FROM Users WHERE email = '$email' and  pass = '$pass' and token='$token' ";
      $result = mysqli_query($connection,$check);
      $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
      
      $count = mysqli_num_rows($result);
      	
      if($count == 1) {
           
          	$respone['Message']="شما هم اکنون وارد برنامه شدید";
		$respone['Result']="1";
      }
        else 
        {
                $respone['Message']=" کاربری یا رمز عبور اشتباه است یا مطعلق به این دستگاه نیست";
		$respone['Result']="0";
        }
    mysqli_close($connection);
    echo json_encode($respone);

?>
