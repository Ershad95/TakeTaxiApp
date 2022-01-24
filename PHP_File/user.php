<?php


	require "index.php";
	if(isset($_POST["email"]) && isset($_POST["pass"]) && isset($_POST["token"])){
	
	$email = $_POST["email"];
	$token= $_POST["token"];
	$pass = $_POST["pass"];
	$name=	$_POST['name'];
	$lname=	$_POST['lname'];
	$respone = array();


      $check = "SELECT id FROM Users WHERE token= '$token' ";
      $result = mysqli_query($connection,$check);
      $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
      
      $count = mysqli_num_rows($result);
      	
      if($count == 0) {
            $insert = "insert into Users(name,lname,email,pass,token,status) values('$name','$lname','$email','$pass','$token','waiting');";

            $f = mysqli_query($connection,$insert);
            if($f)
            {
                $respone['Message']="ثبت نام با موفقیت انجام شد";
		$respone['Result']="1";
            }
            else
            {
                $respone['Message']="خطا : ثبت نام انجام نشد";
		$respone['Result']="0";
            }
      }
        else 
        {
            $respone['Message']="خطا:در این دستگاه ثبت نام انجام شده";
	    $respone['Result']="0";
        }
		echo json_encode($respone);
		
		mysqli_close($connection);
		
	

	}
	
	
	
?>
