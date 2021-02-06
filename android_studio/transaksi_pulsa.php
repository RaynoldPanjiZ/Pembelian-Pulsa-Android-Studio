<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$id_pulsa = $_POST['id_pulsa'];
		$id_customer = $_POST['id_user'];
		$tgl_transaksi = $_POST['tgl_transaksi'];
		$pulsa = $_POST['nominal_pulsa'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO transaksi_pulsa (id_pulsa,id_customer,tgl_transaksi) VALUES ('$id_pulsa','$id_customer','$tgl_transaksi')";
		
		$sql2 = "UPDATE `customer` SET `pulsa`='$pulsa' WHERE `id_customer`='$id_customer';";

		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql2)) {
			mysqli_query($con,$sql);
			echo 'Berhasil Menambahkan data';
		} else {
			echo 'Gagal Menambahkan data';
		}
		
		mysqli_close($con);
	}
?>