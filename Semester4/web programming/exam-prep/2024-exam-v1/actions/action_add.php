
<?php
    require "config.php";

    $person = isset($_SESSION['person']) ? $_SESSION['person'] : null;
    $idReservedSeats = 
    $sql = "INSERT INTO Reservation (person,type,city) VALUES (?, ?, ?, ?)";
    $insert_stmt = mysqli_stmt_init($conn);
      
    mysqli_stmt_prepare($insert_stmt, $sql);
    mysqli_stmt_bind_param($insert_stmt, "sssi", $name, $age, $power, $rank);
    mysqli_stmt_execute($insert_stmt);
    
    header("Location: ../home.php");
    exit();
?>