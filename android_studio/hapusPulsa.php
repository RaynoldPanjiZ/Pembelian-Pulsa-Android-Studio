<?php 

 //Mendapatkan Nilai id_pulsa
 $id_pulsa = $_GET['id'];
 
 //Import File Koneksi Database
 require_once('koneksi.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM pulsa WHERE id_pulsa=$id_pulsa;";

 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus data';
 }else{
 echo 'Gagal Menghapus data';
 }
 
 mysqli_close($con);
 ?>