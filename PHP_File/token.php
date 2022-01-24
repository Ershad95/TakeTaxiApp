<?php

require "index.php";


$fcm_token = $_POST["token"];
$sql = "insert into tokens(token) values('$fcm_token') ;";
$f = mysqli_query($connection,$sql);
if($f)
{
echo "<h1 style='color :red;'>Connect</h1>";
}
else{
    echo "<h1 style='color :red;'>oh</h1>";
}

mysqli_close($connection);

?>
