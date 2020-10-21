<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_UPDATE);
	$p_statement->bind_param('ssi', $_POST[NAME], $_POST[LOCATION], $_POST[ID]);
	if ($p_statement->execute())
		echo 'Berhasil update sekolah';
	else
		echo 'Gagal update sekolah';

	$connection->close();
