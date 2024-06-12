<?php
function get_all_auctions($username) {
    require "config.php";
    $auctions = array();

    $sql = "SELECT * FROM auction WHERE user = ?";
    $stmt = mysqli_stmt_init($conn);

    if (!mysqli_stmt_prepare($stmt, $sql)) {
        echo("There was an error! Please try again later.");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "s", $username);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);
    
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($auctions, $row);   
    }
    return $auctions;
}
 ?>
