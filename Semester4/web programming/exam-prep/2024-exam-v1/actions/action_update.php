<?php
require "config.php";

$id = $_POST['id'];
$name = $_POST['name'];
$age = $_POST['age'];
$powers = $_POST['powers'];
$rank = $_POST['rank'];

$sql = "UPDATE Avatar SET name = ?, age = ?, powers = ?, rank = ? WHERE id = ?";
$stmt = mysqli_stmt_init($conn);

if (!mysqli_stmt_prepare($stmt, $sql)) {
    echo "There was an error! Please try again later.";
    exit();
}

mysqli_stmt_bind_param($stmt, "ssssi", $name, $age, $powers, $rank, $id);
mysqli_stmt_execute($stmt);

header("Location: ../home.php");
?>
