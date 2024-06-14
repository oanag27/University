
<?php
session_start();
require 'config.php';

$person = isset($_SESSION['person']) ? $_SESSION['person'] : null;
$date = isset($_SESSION['date']) ? $_SESSION['date'] : null;
$city = isset($_SESSION['destinationCity']) ? $_SESSION['destinationCity'] : null;

function get_all($date, $city) {
    require 'config.php';
    $sql = "SELECT * FROM Flights WHERE date=? AND destinationCity=? AND availableSeats>0";
    $stmt = mysqli_stmt_init($conn);
    
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        echo "Error";
        exit();
    }

    mysqli_stmt_bind_param($stmt, "ss", $date, $city);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    $flights = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $flights[] = $row;
    }

    mysqli_free_result($result);
    mysqli_stmt_close($stmt);
    mysqli_close($conn);

    return $flights;
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Flights</title>
</head>
<body>
    <h1>Available Flights</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Id</th>
                <th>Date</th>
                <th>Destination City</th>
                <th>Available Seats</th>
            </tr>
        </thead>
        <tbody>
            <?php
            $flights = get_all($date, $city);
            if (empty($flights)) {
                echo "<tr><td>There aren't any flights</td></tr>";
            } else {
                foreach ($flights as $flight) {
                    echo "<tr>";
                    echo "<td>" . $flight['flightId'] . "</td>";
                    echo "<td>" . $flight['date'] . "</td>";
                    echo "<td>" . $flight['destinationCity'] . "</td>";
                    echo "<td>" . $flight['availableSeats'] . "</td>";
                    echo '<td>
                        <form action="action_add_flight.php" method="POST">
                            <input type="hidden" name="flightId" value="' . $flight['flightId'] . '">
                            <input type="hidden" name="person" value="' . $person . '">
                            <button type="submit">Add Reservation</button>
                        </form>
                    </td>';
                    echo "</tr>";
                }
            }
            ?>
        </tbody>
    </table>
    <form action="../home.php">
        <button type="submit">Back to Home</button>
    </form>
</body>
</html>
