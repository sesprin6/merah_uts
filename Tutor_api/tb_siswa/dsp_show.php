<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_GET);
	$p_statement->bind_param('i', $_GET[ID]);
	$p_statement->execute();

	$data = $p_statement->get_result()->fetch_all(MYSQLI_ASSOC)[0];

	$result = array();
	array_push($result, array
	(
		ID => $data[TBL_ID],
		NO => $data[TBL_NO],
		PACKAGE => $data[TBL_PACKAGE],
		NAME => $data[TBL_NAME],
		SEX => $data[TBL_SEX],
		BIRTHPLACE => $data[TBL_BIRTHPLACE],
		BIRTHDAY => $data[TBL_BIRTHDAY],
		SCHOOL => $data[TBL_SCHOOL],
		ADDRESS => $data[TBL_ADDRESS],
		REPRESENTATIVE => $data[TBL_REPRESENTATIVE],
		PHONE => $data[TBL_PHONE]
	));

	SetJsonHeader();
	echo json_encode(array('result' => $result));
	$connection->close();
