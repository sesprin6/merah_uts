<?php
	include $_SERVER['DOCUMENT_ROOT'] . '/connection.php';

	//Set header
	function SetJsonHeader()
	{
		header('Content-Type: application/json');
	}

	//MySQL Queries
	const QUERY_ADD			= 'INSERT INTO paket VALUES (DEFAULT, ?)';
	const QUERY_DELETE		= 'DELETE FROM paket WHERE id = ?';
	const QUERY_GET			= 'SELECT * FROM paket WHERE id = ?';
	const QUERY_GETALL		= 'SELECT * FROM paket';
	const QUERY_UPDATE		= 'UPDATE paket SET paket = ? WHERE id = ?';

	//General Keys
	const ID				= 'id';
	const PACKAGE			= 'package';

	//Database Keys
	const TBL_ID			= 'id';
	const TBL_PACKAGE		= 'paket';
