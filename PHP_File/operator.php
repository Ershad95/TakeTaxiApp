<?php

require "index.php";
$email = $_POST["email"];
$pass = $_POST["pass"];


$respone = array();

      $check = "SELECT * FROM Operator WHERE email = '$email' and  pass = '$pass' ";
      $result = mysqli_query($connection,$check);
      $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
      
      
      
      $count = mysqli_num_rows($result);
      	
      if($count == 1) {
           
          	$respone['Message']="شما هم اکنون وارد برنامه شدید";
          	$respone['lname'] = $row['lname'];
      		$respone['name'] = $row['name'];
      		$respone['email'] = $row['email'];
      		$respone['pass'] = $row['pass'];
      		$respone['id'] = $row['id'];
		$respone['Result']="1";
      }
        else 
        {
                $respone['Message']="نام کابری با رمز عبور نادرست است";
		$respone['Result']="0";
        }
    mysqli_close($connection);
    echo json_encode($respone);

?>
