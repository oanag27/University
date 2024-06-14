<?php
    session_start();
    $person = isset($_SESSION['person']) ? $_SESSION['person'] : null;
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
</head>
<body>
    <form action="actions/action_flight.php" method="POST">
        <button type="submit">Flights</button>
    </form>
    <form action="actions/action_hotel.php" method="POST">
        <button type="submit">Hotels</button>
    </form>
</body>
</html>