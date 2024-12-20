<?php
    require "config.php";
    $id = $_POST["id"];
    $description = $_POST["description"];
    $user = $_POST["user"];
    $date = $_POST["date"];

    // Update the post in the database
    $sql = "UPDATE auction SET description = ?, user = ?, date = ? WHERE id = ?";
    $update_stmt = mysqli_stmt_init($conn);
    
    if (!mysqli_stmt_prepare($update_stmt, $sql)) {
        echo("There was an error! Please try again later.");
        exit();
    }
    
    mysqli_stmt_bind_param($update_stmt, "sssi", $description, $user, $date, $id);
    mysqli_stmt_execute($update_stmt);

    header("Location: ../home.php");  
?>