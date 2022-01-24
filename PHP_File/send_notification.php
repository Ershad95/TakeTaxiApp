<?php

require "index.php";

$json = $_POST["json"];
$old_time = $_POST["old_time"];

$tokens = array();
$respone= array();

/*
$json ='{"msg":{
    "Address":"Karaj",
    "PassengerName":"Ershad",
    "PassengerTel":"0983749879",
    "Time":"2017-01-26 10:23:17"},
    "token":
            ["cDVFonckO0M:APA91bEdR5FkR0fNmI64SyAMJdLCbOpwc8NecnAmPsusainTJXV-njCsZKtJ3_MeG_hlbjP-Gd3X-WGmy1r4hescbd59lpuOtSdxLHg5h6t-kDldGP6KQsX6iXhqjl_Xr89RNvh7zAc8",
            "cBpvFGqSwCY:APA91bGy4FH-OVSqwHgAVIhSsOLV0emLeSv34s05bf-GuTY8K-KxLcaOso3OoyusHNv0C2JdnVXWwPQzG70NtgdAcZWNj-owFa60t_6bCUFdGK_66pvxF2ERpKyb1_Q9WedTTAJGs_39",
            "fQF4JN2XsCU:APA91bEcdOjWkkF4cWoYGK8T2rhf7MAqelrjlWfWqenArofIPUKMnJx84W1glPVhcklcc89yPCv7KHPZ0hkPrR-KqPerPw7VFa58FxS_nOx_UjRvSemLgKmdvOMSOkN4RQTtRyIbdrrX",
            "dNjhnUHiQbU:APA91bHpadRbxGeTxKX2WltbLcJ8Lbh3doeVd57JhzB_bdCVGxK-uEhWIJzA544kcJdkoWuJwKOpQaarrkPnNeX_RYUWfy8yxgZF7ubWY1lIILO5qea7GqPf3TnO1ihF1KalCl4-ADpr"]}';
            
            foreach ($tokens as $val)
{
    echo  $val;
    echo  "<br>";
    echo  "<br>";
}
echo  "<hr />";


foreach ($message as $val)
{
    echo  $val;
    echo  "<br>";
    echo  "<br>";
}
*/



$data = json_decode($json,true);

$message = array(
	"Name"=>$data['msg']['PassengerName'],
	"Tel"=>$data['msg']['PassengerTel'],
	"Addr"=>$data['msg']['Address'],
	"Time"=>$data['msg']['Time'],
	"Des"=>$data['msg']['Destination'],
	"Price"=>$data['msg']['Price']
	);

$name = $data['msg']['PassengerName'];
$tel =  $data['msg']['PassengerTel'];
$addr = $data['msg']['Address'];
$des  = $data['msg']['Destination'];
$time = $data['msg']['Time'];
$price =$data['msg']['Price'];

$insert_message = "insert into Messages(name,telephone,address,Destination,time) values('$name','$tel','$addr','$des','$time');";

$update_message = "update Messages set time = '$time' where time = '$old_time' ";

if($old_time!="-")
{
            $update_result = mysqli_query($connection,$update_message);
            if($update_result)
            {
                $respone['Message']="زمان پیام تغییر کرد";
		$respone['Result']="1";
            }
            else
            {
                $respone['Message']="خطا : زمان تغییر نکرد";
		$respone['Result']="0";
            }
            }else
            {
            	$insert_result = mysqli_query($connection,$insert_message);
            	if($insert_result)
            	{
               	 	$respone['Message']="ثبت با موفقیت انجام شد";
			$respone['Result']="1";
            	}
            	else
            	{
                	$respone['Message']="خطا : ثبت انجام نشد";
			$respone['Result']="0";
            	}
            }
            
            echo $respone['Result'];


foreach ($data['token'] as $value)
{
    array_push($tokens,$value);
}

send_notifi($tokens,$message);

function send_notifi($token,$message){
	//firebase server url to send the curl request
$url = 'https://fcm.googleapis.com/fcm/send';

$fields = array(
    'registration_ids' => $token,
    'data' => $message,
);


$headers = array(
    'Authorization: key='.'AAAAxCS6EPo:APA91bGp32r69plPy8vzPmVWleNyGaNZMme8cog5qesJXVlGnXqXwHtrdi0yUGaJxPdHK1ahoAvoIm7PRClbD7KIEriQTU3KJ9qdpibE0TxcDFlPrySoxsF2ZnlRcvgTUOcJ9PC9HMGFQcJdbaxM7YEoe7zcTcfWVA',
    'Content-Type: application/json'
);



//Initializing curl to open a connection
$ch = curl_init();

//Setting the curl url
curl_setopt($ch, CURLOPT_URL, $url);

//setting the method as post
curl_setopt($ch, CURLOPT_POST, true);

//adding headers
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

//disabling ssl support
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

//adding the fields in json format
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

//finally executing the curl request
$result = curl_exec($ch);
if ($result === FALSE) {
    die('Curl failed: ' . curl_error($ch));
}

//Now close the connection
curl_close($ch);

echo $result."<br><br>";

echo json_encode($fields);
}





?>