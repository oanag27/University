<?php
include 'db.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $author = $_POST['author'];
    $title = $_POST['title'];
    $num_pages = $_POST['num_pages'];
    $type = $_POST['type'];
    $format = $_POST['format'];

    $con = OpenConnection();

    $stmt = $con->prepare("INSERT INTO documents (author, title, num_pages, type, format) VALUES (?, ?, ?, ?, ?)");
    $stmt->bind_param("ssiss", $author, $title, $num_pages, $type, $format);

    if ($stmt->execute()) {
        echo "Document added successfully!";
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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Document</title>
</head>
<body>
    <h2>Add Document</h2>
    <form action="insert_document.php" method="POST">
        <label for="author">Author:</label><br>
        <input type="text" id="author" name="author" required><br>
        <label for="title">Title:</label><br>
        <input type="text" id="title" name="title" required><br>
        <label for="num_pages">Number of Pages:</label><br>
        <input type="number" id="num_pages" name="num_pages" required><br>
        <label for="type">Type:</label><br>
        <input type="text" id="type" name="type" required><br>
        <label for="format">Format:</label><br>
        <input type="text" id="format" name="format" required><br><br>
        <input type="submit" value="Add Document">
    </form>
</body>
</html>
