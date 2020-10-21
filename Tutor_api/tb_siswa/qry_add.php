<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_ADD);
	$p_statement->bind_param('iissssisss', $_POST[NO], $_POST[PACKAGE], $_POST[NAME], $_POST[SEX], $_POST[BIRTHPLACE], $_POST[BIRTHDAY], $_POST[SCHOOL], $_POST[ADDRESS], $_POST[REPRESENTATIVE], $_POST[PHONE]);
	if ($p_statement->execute())
		echo 'Berhasil menambahkan siswa';
	else
		echo 'Gagal menambahkan siswa';

	$connection->close();
