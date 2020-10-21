<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_ADD);
	$p_statement->bind_param('s', $_POST[PACKAGE]);
	if ($p_statement->execute())
		echo 'Berhasil menambahkan paket';
	else
		echo 'Gagal menambahkan paket';

	$connection->close();
