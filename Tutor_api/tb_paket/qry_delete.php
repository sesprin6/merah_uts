<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_DELETE);
	$p_statement->bind_param('i', $_GET[ID]);

	if ($p_statement->execute())
		echo 'Berhasil menghapus paket';
	else
		echo 'Gagal menghapus paket';

	$connection->close();
