  <?php
  require "index.php";
	$respone= array();
	
	if(isset($_POST["token"])){
	
	$name= $_POST["name"];
	$lname =$_POST["lname"];
	$email = $_POST["email"];
	$pass = $_POST["pass"];
	$id= $_POST["token"];

	
      
      
      $Operator_Update= "UPDATE Operator SET name='$name', lname = '$lname' , email = '$email' , pass = '$pass'  WHERE id='$id'";
    

      $result = mysqli_query($connection,$Operator_Update);
      
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
    }else
    {
    	 	$respone['Message']="خطا : تغییر امکان پذیر نیست";
		$respone['Result']="0";
    }
    echo json_encode($respone);
    mysqli_close($connection);
    ?>