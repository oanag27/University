<?php
    session_start();
    // Get the username from the session if it's stored there
    $username = isset($_SESSION['username']) ? $_SESSION['username'] : null;

    // get the entities
    require "actions/action_get_all.php";
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
</head>

<style>
    .entity {
        border: 1px solid black;
        padding: 10px;
        margin: 10px;
    }

    .entity:hover {
        background-color: #eee;
        cursor: pointer;
    }
</style>

<body>
     <?php 
    echo "<h2>Welcome, " . $_SESSION["username"] . "!</h2>";
    ?> 
    <form action="actions/action_logout.php">
        <button type="submit"> Logout </button>
    </form>

    <hr>
    <button><a href="update.php">Update auction</a></button>
    <button><a href="add_auction.php">Add a new auction</a></button>
    <hr>

    <h3>All Auctions:</h3>
    <?php 
    $auctions = get_all_auctions($username);
    for ($i = 0; $i < count($auctions); $i++) {
        echo "<div class='entity'>";
        echo "<p>Auction id: " . $auctions[$i]["Id"] . "</p>";
        echo "<p>User: " . $auctions[$i]["user"] . "</p>";
        echo "<p>Description: " . $auctions[$i]["description"] . "</p>";
        echo "<p>Date: " . $auctions[$i]["date"] . "</p>";
        echo "</div>";
    }
   ?>

</body>

</html>