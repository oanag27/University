<?php
    require "config.php";

    $username = $_POST["username"];
    $password = $_POST["password"];
    $sql = "SELECT * FROM users WHERE username = ? and password=?";
    $select_stmt = mysqli_stmt_init($conn);
    
    if (!mysqli_stmt_prepare($select_stmt, $sql)) {
        echo("There was an error! Please try again later.");
        exit();
    }
    mysqli_stmt_bind_param($select_stmt, "ss", $username, $password);
    mysqli_stmt_execute($select_stmt);
    $result = mysqli_stmt_get_result($select_stmt);
    $row = mysqli_fetch_assoc($result);
    if ($row) {
        $_SESSION["username"] = $row["username"];
        header("Location: ../home.php");
        exit();
    } else {
        echo("User not found.");
        exit();
    }
?>