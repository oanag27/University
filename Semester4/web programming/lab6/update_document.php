<?php
include 'db.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    //$id = $_POST['document_id'];
    $author = $_POST['author'];
    $title = $_POST['title'];
    $num_pages = $_POST['num_pages'];
    $type = $_POST['type'];
    $format = $_POST['format'];

    $con = OpenConnection();

    //$stmt = $con->prepare("UPDATE documents SET author=?, title=?, num_pages=?, type=?, format=? WHERE id=?");
    //$stmt->bind_param("ssissi", $author, $title, $num_pages, $type, $format, $id);
    $stmt = $con->prepare("UPDATE documents SET author=?, num_pages=?, type=?, format=? WHERE title=?");
    $stmt->bind_param("sisss", $author,$num_pages, $type, $format,$title);

    if ($stmt->execute()) {
        echo "Document updated successfully!";
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
    <title>Update Document</title>
</head>
<body>
<h2>Update Document</h2>
    <form action="update_document.php" method="post">
        <!-- <label for="document_id">Enter Document ID:</label>
        <input type="text" id="document_id" name="document_id" required><br> -->
        <label for="author">Author:</label>
        <input type="text" id="author" name="author" required><br>
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br>
        <label for="num_pages">Number of Pages:</label>
        <input type="number" id="num_pages" name="num_pages" required><br>
        <label for="type">Type:</label>
        <input type="text" id="type" name="type" required><br>
        <label for="format">Format:</label>
        <input type="text" id="format" name="format" required><br>
        <input type="submit" value="Update Document">
    </form>
</body>
</html>