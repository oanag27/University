<?php
    session_start();
    // Get the username from the session if it's stored there
    $username = isset($_SESSION['username']) ? $_SESSION['username'] : null;
    require "actions/action_get_all.php";
    require "actions/filter.php";
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
    <button><a href="add_action.php">Add a new avatar</a></button>
    <hr>
    <!-- <?php
            $entities = get_all();
            for ($i = 0; $i < count($entities); $i++) {
                    echo "<div class='entity'>";
                    echo "<p>Name: " . $entities[$i]["name"] . "</p>";
                    echo "<p>Age: " . $entities[$i]["age"] . "</p>";
                    echo "<p>Power: " . $entities[$i]["powers"] . "</p>";
                    echo "<p>Rank: " . $entities[$i]["rank"] . "</p>";
                    echo "</div>";
                }
    ?> -->
    <h3>All Entities:</h3>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Age</th>
                <th>Power</th>
                <th>Rank</th>
            </tr>
        </thead>
        <tbody>
            <?php
                $entities = get_all();
                if (isset($_POST['filter'])) {
                    $rank1 = $_POST['rank1'];
                    $rank2 = $_POST['rank2'];
                    $entities = get_all_filtered($rank1, $rank2);
                }
                foreach ($entities as $entity) {
                    echo "<tr>";
                    echo "<form action='update.php' method='POST'>";
                    echo "<td>" . $entity['name'] . "</td>";
                    echo "<td>" . $entity['age'] . "</td>";
                    echo "<td>" . $entity['powers'] . "</td>";
                    echo "<td>" . $entity['rank'] . "</td>";
                    echo "<td>";
                    echo "<input type='hidden' name='id' value='" . $entity['id'] . "'>";
                    echo "<button type='submit'>Update</button>";
                    echo "</td>";
                    echo "</form>";
                    echo "<td>";
                    echo "<form action='actions/action_delete.php' method='POST' style='display:inline;'>";
                    echo "<input type='hidden' name='id' value='" . $entity['id'] . "'>";
                    echo "<button type='submit'>Delete</button>";
                    echo "</form>";
                    echo "</td>";
                    echo "</tr>";
                }
            ?>
        </tbody>
    </table>
    <hr>
    <h3>Filter:</h3>
    <form method="post">
        <input type="text" name="rank1" placeholder="rank1">
        <input type="text" name="rank2" placeholder="rank2">
        <input type="submit"  name="filter" value="Filter">
    </form>
      
</body>

</html>