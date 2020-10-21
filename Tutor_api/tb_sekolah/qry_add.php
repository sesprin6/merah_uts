<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_ADD);
	$p_statement->bind_param('ss', $_POST[NAME], $_POST[LOCATION]);
	if ($p_statement->execute())
		echo 'Berhasil menambahkan sekolah';
	else
		echo 'Gagal menambahkan sekolah';

	$connection->close();
