<?php
include 'db.php';

if ($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['format'])) {
    $format = $_GET['format'];

    $con = OpenConnection();

    $stmt = $con->prepare("SELECT * FROM documents WHERE format = ?");
    $stmt->bind_param("s", $format);
    $stmt->execute();
    $result = $stmt->get_result();
    
    $documents = array();
    while ($row = $result->fetch_assoc()) {
        $documents[] = $row;
    }

    CloseConnection($con);

    echo json_encode($documents);
} else {
    echo "Invalid request!";
}
?>
