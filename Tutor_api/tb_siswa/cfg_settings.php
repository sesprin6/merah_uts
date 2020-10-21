<?php
	include $_SERVER['DOCUMENT_ROOT'] . '/connection.php';

	//Set header
	function SetJsonHeader()
	{
		header('Content-Type: application/json');
	}

	//MySQL Queries
	const QUERY_ADD				= 'INSERT INTO siswa VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)';
	const QUERY_DELETE			= 'DELETE FROM siswa WHERE id = ?';
	const QUERY_GET				= 'select siswa.id, siswa.no_induk, paket.paket as paket, siswa.nama, siswa.j_kelamin, siswa.tempat_lahir, siswa.tanggal_lahir, sekolah.nama as sekolah, siswa.alamat, siswa.nama_wali, siswa.telp from siswa inner join paket on siswa.paket = paket.id inner join sekolah on siswa.sekolah = sekolah.id where siswa.id = ?;';
	const QUERY_GETALL			= 'SELECT * FROM siswa';
	const QUERY_UPDATE			= 'UPDATE siswa SET no_induk = ?, paket = ?, nama = ?, j_kelamin = ?, tempat_lahir = ?, tanggal_lahir = ?, sekolah = ?, alamat = ?, nama_wali = ?, telp = ? WHERE id = ?';

	//General Keys
	const ID					= 'id';
	const NO					= 'no';
	const PACKAGE				= 'package';
	const NAME					= 'name';
	const SEX					= 'sex';
	const BIRTHPLACE			= 'birthplace';
	const BIRTHDAY				= 'birthday';
	const SCHOOL				= 'school';
	const ADDRESS				= 'address';
	const REPRESENTATIVE		= 'representative';
	const PHONE					= 'phone';

	//Database Keys
	const TBL_ID				= 'id';
	const TBL_NO				= 'no_induk';
	const TBL_PACKAGE			= 'paket';
	const TBL_NAME				= 'nama';
	const TBL_SEX				= 'j_kelamin';
	const TBL_BIRTHPLACE		= 'tempat_lahir';
	const TBL_BIRTHDAY			= 'tanggal_lahir';
	const TBL_SCHOOL			= 'sekolah';
	const TBL_ADDRESS			= 'alamat';
	const TBL_REPRESENTATIVE	= 'nama_wali';
	const TBL_PHONE				= 'telp';
