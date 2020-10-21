<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_UPDATE);
	$p_statement->bind_param('si', $_POST[PACKAGE], $_POST[ID]);
	if ($p_statement->execute())
		echo 'Berhasil update paket';
	else
		echo 'Gagal update paket';

	$connection->close();
