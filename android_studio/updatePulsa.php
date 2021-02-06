<?php 

	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable 
		$id_pulsa = $_POST['id_pulsa'];
		$nominal_pulsa = $_POST['nominal_pulsa'];
		$harga_pulsa = $_POST['harga_pulsa'];
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE pulsa SET nominal_pulsa = '$nominal_pulsa', harga_pulsa = '$harga_pulsa' WHERE id_pulsa = $id_pulsa;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data ';
		}else{
			echo 'Gagal Update Data>>'.$id_pulsa;
		}
		
		mysqli_close($con);
	}
?>