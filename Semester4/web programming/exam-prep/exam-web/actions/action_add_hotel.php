<?php
require "config.php";
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $person = isset($_POST['person']) ? $_POST['person'] : null;
    $hotel_id = isset($_POST['hotelId']) ? $_POST['hotelId'] : null;

    $insert_sql = "INSERT INTO Reservations (person, type, idReservedResource) VALUES (?, 'hotel', ?)";
    
    $insert_stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($insert_stmt, $insert_sql)) {
        echo "Error: ";
        exit();
    }

    mysqli_stmt_bind_param($insert_stmt, "si", $person, $hotel_id);
    mysqli_stmt_execute($insert_stmt);

    $update_sql = "UPDATE Hotels SET availableRooms = availableRooms - 1 WHERE hotelId = ?";
    $update_stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($update_stmt, $update_sql)) {
        echo "Error: ";
        exit();
    }
    mysqli_stmt_bind_param($update_stmt, "i", $hotel_id);
    mysqli_stmt_execute($update_stmt);
    header("Location: ../home.php");
    exit();
} else {
    echo "Error.";
    exit();
}
?>