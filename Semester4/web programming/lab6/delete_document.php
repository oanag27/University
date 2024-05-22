<?php
include 'db.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $title = $_POST['document_title'];

    $con = OpenConnection();

    $stmt = $con->prepare("DELETE FROM documents WHERE title=?");
    $stmt->bind_param("s", $title);

    if ($stmt->execute()) {
        echo "Document deleted successfully!";
    } else {
        echo "Error: " . $stmt->error;
    }

    $stmt->close();
    CloseConnection($con);
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Delete Document</title>
</head>
<body>
    <h2>Delete Document</h2>
    <!-- Add your delete confirmation form here -->
    <form action="delete_document.php" method="post">
        <label for="document_id">Enter Document title:</label>
        <input type="text" id="document_id" name="document_title" required><br>
        <input type="submit" value="Delete Document">
    </form>
</body>
</html>

