<?php
  require 'actions/config.php';
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
</head>
<body>
  <h1>Login</h1>
  <?php
    if (isset($_GET["error"])) {
      echo "<h4>Error: " . $_GET["error"] . "</h4>";
    }
  ?>
  <form action="home.php" method="POST">
    <input type="text" name="person" placeholder="person">
    <input type="text" name="date" placeholder="date">
    <input type="text" name="destinationCity" placeholder="destinationCity">
    <button type="submit">Begin Reservation</button>
  </form>
</body>
</html>