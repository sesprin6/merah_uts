<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_DELETE);
	$p_statement->bind_param('i', $_GET[ID]);

	if ($p_statement->execute())
		echo 'Berhasil menghapus sekolah';
	else
		echo 'Gagal menghapus sekolah';

	$connection->close();
