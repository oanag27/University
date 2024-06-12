<?php
    require "config.php";

    $username = $_POST["username"];
    $sql = "SELECT * FROM auction WHERE user = ?";
    $select_stmt = mysqli_stmt_init($conn);
    
    if (!mysqli_stmt_prepare($select_stmt, $sql)) {
        echo("There was an error! Please try again later.");
        exit();
    }
    mysqli_stmt_bind_param($select_stmt, "s", $username);
    mysqli_stmt_execute($select_stmt);
    $result = mysqli_stmt_get_result($select_stmt);
    $row = mysqli_fetch_assoc($result);
    if ($row) {
    // set the session and redirect to the home page
        $_SESSION["username"] = $row["user"];
        header("Location: ../home.php");
        exit();
    } else {
        echo("User not found.");
        exit();
    }
?>