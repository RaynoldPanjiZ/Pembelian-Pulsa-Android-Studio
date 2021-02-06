<?php 
	//Mendapatkan Nilai Dari Variable ID  yang ingin ditampilkan
	$id = $_GET['id'];
	//Importing database
	require_once('koneksi.php');
	//Membuat SQL Query dengan id yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM pulsa WHERE id_pulsa=$id";
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id_pulsa"=>$row['id_pulsa'],
			"nominal_pulsa"=>$row['nominal_pulsa'],
			"harga_pulsa"=>$row['harga_pulsa']
		));
	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	mysqli_close($con);
?>