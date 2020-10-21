<?php
	include $_SERVER['DOCUMENT_ROOT'] . '/connection.php';

	//Set header
	function SetJsonHeader()
	{
		header('Content-Type: application/json');
	}

	//MySQL Queries
	const QUERY_ADD			= 'INSERT INTO sekolah VALUES (DEFAULT, ?, ?)';
	const QUERY_DELETE		= 'DELETE FROM sekolah WHERE id = ?';
	const QUERY_GET			= 'SELECT * FROM sekolah WHERE id = ?';
	const QUERY_GETALL		= 'SELECT * FROM sekolah';
	const QUERY_UPDATE		= 'UPDATE sekolah SET nama = ?, tempat = ? WHERE id = ?';

	//General Keys
	const ID				= 'id';
	const NAME				= 'name';
	const LOCATION			= 'location';

	//Database Keys
	const TBL_ID			= 'id';
	const TBL_NAME			= 'nama';
	const TBL_LOCATION		= 'tempat';
