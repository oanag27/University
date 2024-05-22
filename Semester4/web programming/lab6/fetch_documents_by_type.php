<?php
include 'db.php';

if ($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['type'])) {
    $type = $_GET['type'];

    $con = OpenConnection();

    $stmt = $con->prepare("SELECT * FROM documents WHERE type = ?");
    $stmt->bind_param("s", $type);
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
