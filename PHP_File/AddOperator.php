<?php


	require "index.php";
	
	if(isset($_POST["email"]) && isset($_POST["pass"])){
	
	$email = $_POST["email"];
	$pass = $_POST["pass"];
	$name=$_POST['name'];
	$lname=$_POST['lname'];
	$respone = array();


      $check = "SELECT id FROM Operator WHERE email= '$email' ";
      $result = mysqli_query($connection,$check);
      $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
      
      $count = mysqli_num_rows($result);
      	
      if($count == 0) {
            $insert = "insert into Operator(email,pass,name,lname) values('$email','$pass','$name','$lname');";

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
