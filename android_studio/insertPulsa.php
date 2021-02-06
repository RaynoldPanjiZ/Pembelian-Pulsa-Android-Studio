<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$nominal_pulsa = $_POST['nominal_pulsa'];
		$harga_pulsa = $_POST['harga_pulsa'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO pulsa (nominal_pulsa,harga_pulsa) VALUES ('$nominal_pulsa','$harga_pulsa')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan data';
		}else{
			echo 'Gagal Menambahkan data >> '.$harga_pulsa;
		}
		
		mysqli_close($con);
	}
?>