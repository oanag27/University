<?php
require "config.php";
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $person = isset($_POST['person']) ? $_POST['person'] : null;
    $flight_id = isset($_POST['flightId']) ? $_POST['flightId'] : null;

    $insert_sql = "INSERT INTO Reservations (person, type, idReservedResource) VALUES (?, 'flight', ?)";
    $insert_stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($insert_stmt, $insert_sql)) {
        echo "Error: ";
        exit();
    }

    mysqli_stmt_bind_param($insert_stmt, "si", $person, $flight_id);
    mysqli_stmt_execute($insert_stmt);

    $update_sql = "UPDATE Flights SET availableSeats = availableSeats - 1 WHERE flightId = ?";
    $update_stmt = mysqli_stmt_init($conn);

    if (!mysqli_stmt_prepare($update_stmt, $update_sql)) {
        echo "Error: ";
        exit();
    }
    mysqli_stmt_bind_param($update_stmt, "i", $flight_id);
    mysqli_stmt_execute($update_stmt);
    header("Location: ../home.php");
    exit();
} else {
    echo "Error.";
    exit();
}
?>