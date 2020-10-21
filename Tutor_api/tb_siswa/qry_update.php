<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_UPDATE);
	$p_statement->bind_param('iissssisssi', $_POST[NO], $_POST[PACKAGE], $_POST[NAME], $_POST[SEX], $_POST[BIRTHPLACE], $_POST[BIRTHDAY], $_POST[SCHOOL], $_POST[ADDRESS], $_POST[REPRESENTATIVE], $_POST[PHONE], $_POST[ID]);
	if ($p_statement->execute())
		echo 'Berhasil update siswa';
	else
		echo 'Gagal update siswa';

	$connection->close();
