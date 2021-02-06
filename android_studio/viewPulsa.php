<?php 
	//Import File Koneksi Database
	require_once('koneksi.php');
	//Membuat SQL Query
	$sql = "SELECT * FROM pulsa";
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	//Membuat Array Kosong 
	$result = array();
	while($row = mysqli_fetch_array($r))
	{
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_pulsa"=>$row['id_pulsa'],
			"nominal_pulsa"=>$row['nominal_pulsa'],
			"harga_pulsa"=>$row['harga_pulsa']
		));
	}
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	mysqli_close($con);
?>