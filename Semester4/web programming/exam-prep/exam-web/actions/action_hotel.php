
<?php
session_start();
require 'config.php';
$person = isset($_SESSION['person']) ? $_SESSION['person'] : null;
$date = isset($_SESSION['date']) ? $_SESSION['date'] : null;
$city = isset($_SESSION['destinationCity']) ? $_SESSION['destinationCity'] : null;

function get_all($date, $city) {
    require 'config.php';

    $sql = "SELECT * FROM Hotels WHERE date=? AND city=? AND availableRooms>0";
    $stmt = mysqli_stmt_init($conn);
    
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        echo "Error";
        exit();
    }

    mysqli_stmt_bind_param($stmt, "ss", $date, $city);
    mysqli_stmt_execute($stmt);

    $result = mysqli_stmt_get_result($stmt);

    $hotels = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $hotels[] = $row;
    }

    mysqli_free_result($result);
    mysqli_stmt_close($stmt);
    mysqli_close($conn);
    
    return $hotels;
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Hotels</title>
</head>
<body>
    <h1>Available Hotels</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Id</th>
                <th>HotelName</th>
                <th>City</th>
                <th>Available Rooms</th>
            </tr>
        </thead>
        <tbody>
            <?php
            $hotels = get_all($date, $city);
            if (empty($hotels)) {
                echo "<tr><td>There aren't any hotels</td></tr>";
            } else {
                foreach ($hotels as $hotel) {
                    echo "<tr>";
                    echo "<td>" . $hotel['hotelId'] . "</td>";
                    echo "<td>" . $hotel['date'] . "</td>";
                    echo "<td>" . $hotel['city'] . "</td>";
                    echo "<td>" . $hotel['availableRooms']. "</td>";
                    echo '<td>
                        <form action="action_add_hotel.php" method="POST">
                            <input type="hidden" name="hotelId" value="' . $hotel['hotelId'] . '">
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
