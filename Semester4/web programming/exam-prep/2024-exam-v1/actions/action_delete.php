<?php
require "config.php";

if (isset($_POST['id'])) {
    $id = $_POST['id'];

    $sql = "DELETE FROM Avatar WHERE id = ?";
    $delete_stmt = mysqli_stmt_init($conn);

    if (!mysqli_stmt_prepare($delete_stmt, $sql)) {
        echo "There was an error! Please try again later.";
        exit();
    }

    mysqli_stmt_bind_param($delete_stmt, "i", $id);
    mysqli_stmt_execute($delete_stmt);

    header("Location: ../home.php");
} else {
    echo "No ID provided.";
}
?>
