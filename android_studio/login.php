<?php 

	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Import File Koneksi Database
		require_once('koneksi.php');

		$username='';
		$password='';		

		$username = $_POST['username'];
		$password = $_POST['password'];
		
				
		$data_customer = mysqli_query($con,"SELECT * FROM customer where username='$username' and password='$password'");

		$data_admin = mysqli_query($con,"SELECT * FROM user_admin where username='$username' and password='$password'");

		$result = array();
		
		if(mysqli_num_rows($data_customer) > 0){
			while($row = mysqli_fetch_array($data_customer)) {
				array_push($result,array(
					"id_user"=>$row['id_customer'],
					"username"=>$row['username'],
					"password"=>$row['password'],
					"noTelp"=>$row['noTelp'],
					"pulsa"=>$row['pulsa'],
					"akses"=>"Customer"
				));
			}
			echo json_encode(array('result'=>$result));

		} else if(mysqli_num_rows($data_admin) > 0) {
			while($row = mysqli_fetch_array($data_admin)) {
				array_push($result,array(
					"id_user"=>$row['id_admin'],
					"username"=>$row['username'],
					"password"=>$row['password'],
					"akses"=>"Admin"
				));
			}
			echo json_encode(array('result'=>$result));

		} else {
			array_push($result,array(
				"akses"=>"Maaf username atau Password Salah !!"
			));

			echo json_encode(array('result'=>$result));
		}
	
		
		mysqli_close($con);
	}
?>