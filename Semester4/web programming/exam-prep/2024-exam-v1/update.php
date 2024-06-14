<?php
session_start();
require "actions/config.php";

// Fetch the entity details based on the provided ID
$id = $_POST['id'];
$sql = "SELECT * FROM Avatar WHERE id = ?";
$stmt = mysqli_stmt_init($conn);

if (!mysqli_stmt_prepare($stmt, $sql)) {
    echo "There was an error! Please try again later.";
    exit();
}

mysqli_stmt_bind_param($stmt, "i", $id);
mysqli_stmt_execute($stmt);
$result = mysqli_stmt_get_result($stmt);
$entity = mysqli_fetch_assoc($result);

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Entity</title>
</head>

<body>
    <h2>Update Entity</h2>
    <form action="actions/action_update.php" method="POST">
        <input type="hidden" name="id" value="<?php echo $entity['id']; ?>">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="<?php echo $entity['name']; ?>"><br>
        <label for="age">Age:</label>
        <input type="text" id="age" name="age" value="<?php echo $entity['age']; ?>"><br>
        <label for="powers">Powers:</label>
        <input type="text" id="powers" name="powers" value="<?php echo $entity['powers']; ?>"><br>
        <label for="rank">Rank:</label>
        <input type="text" id="rank" name="rank" value="<?php echo $entity['rank']; ?>"><br>
        <button type="submit">Save</button>
    </form>
</body>

</html>
