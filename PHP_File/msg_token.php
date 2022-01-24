<?php

require "index.php";

	$respone= array();
	$json= $_POST["json"];
	/*
	$json = '{  
   "Time":"2017-01-28 21:26:20",
   "token_accepted":"cDVFonckO0M:APA91bEdR5FkR0fNmI64SyAMJdLCbOpwc8NecnAmPsusainTJXV-njCsZKtJ3_MeG_hlbjP-Gd3X-WGmy1r4hescbd59lpuOtSdxLHg5h6t-kDldGP6KQsX6iXhqjl_Xr89RNvh7zAc8"
}';
*/
	$data = json_decode($json,true);
	$time = $data['Time'];
	$token = $data['Token_accepted'];
	

  	$check= "Select * from Messages where token = 'Empty' and time = '$time'";


      $result_check = mysqli_query($connection,$check);
      $row = mysqli_fetch_array($result_check ,MYSQLI_ASSOC);
      $count = mysqli_num_rows($result_check );
      
      if($count>0)
      {
      		$MessageUpdate= "UPDATE Messages SET token='$token' WHERE time='$time'";
      		$r = mysqli_query($connection,$MessageUpdate);
      		if($r){
      			$respone['Message']="انجام شد";
      			$respone['Result']="1";
      			
      		}
      		 else
     		 {
      			$respone['Message']="خطا";
      			$respone['Result']="0";
      		}
      }else
      {
      	$respone['Message']="خطا : هیچ رکوردی یافت نشد ";
      	$respone['Result']="0";
      }
     
      
      
       echo json_encode($respone);
	
	mysqli_close($connection);
/*

?>