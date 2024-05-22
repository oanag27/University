<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Document</title>
</head>
<body>
    <h2>Add Document</h2>
    <form action="process_add_document.php" method="POST">
        <label for="author">Author:</label>
        <input type="text" id="author" name="author" required><br>
        <label for="title">Title:</label>
      <input type="text" id="title" name="title" required />
      <label for="num_pages">Number of Pages:</label>
      <input type="number" id="num_pages" name="num_pages" required />
      <label for="type">Type:</label>
      <input type="text" id="type" name="type" required />
      <label for="format">Format:</label>
      <input type="text" id="format" name="format" required />
        <input type="submit" value="Add Document">
    </form>
</body>
</html>
