<?php
include 'dbCon.php';

// Fetch all projects from the database
$query = "SELECT * FROM Project";
$result = mysqli_query($con, $query);

if (mysqli_num_rows($result) > 0) {
    echo "<h2>All Projects</h2>";
    echo "<table border='1'>";
    echo "<tr><th>ID</th><th>Project Manager ID</th><th>Name</th><th>Description</th><th>Members</th></tr>";
    while ($row = mysqli_fetch_assoc($result)) {
        echo "<tr>";
        echo "<td>{$row['id']}</td>";
        echo "<td>{$row['ProjectManagerID']}</td>";
        echo "<td>{$row['name']}</td>";
        echo "<td>{$row['description']}</td>";
        echo "<td>{$row['members']}</td>";
        echo "</tr>";
    }
    echo "</table>";
} else {
    echo "No projects found.";
}

if (isset($_POST['username'])) {
    $username = $_POST['username'];

    // Prevent SQL injection
    $username = mysqli_real_escape_string($con, $username);
    $query = "SELECT * FROM SoftwareDeveloper WHERE name = '$username'";
    $result = mysqli_query($con, $query);

    if ($result) {
        if (mysqli_num_rows($result) == 1) {
            $row = mysqli_fetch_assoc($result);
            $query_projects = "SELECT name FROM Project WHERE FIND_IN_SET('$username', members)";
            $result_projects = mysqli_query($con, $query_projects);


            if ($result_projects && mysqli_num_rows($result_projects) > 0) {
                echo "<h2>Projects $username is a Member Of</h2>";
                echo "<ul>";
                while ($row_project = mysqli_fetch_assoc($result_projects)) {
                    echo "<li>{$row_project['name']}</li>";
                }
                echo "</ul>";
            } else {
                echo "You're not a member of any projects.";
            }
        } else {
            echo 'Invalid username. Please try again.';
        }
    } else {
        echo 'Error: ' . mysqli_error($con);
    }
    mysqli_free_result($result);
} else {
    echo 'Username is required.';
}

echo "<form action='' method='post'>";
echo "<button type='submit' name='display_sd'>Display all Software Developers</button>";
echo "</form>";


if (isset($_POST['display_sd'])) {
    $query_sd = "SELECT * FROM SoftwareDeveloper";
    $result_sd = mysqli_query($con, $query_sd);

    if (mysqli_num_rows($result_sd) > 0) {
        echo "<h2>All Software Developers</h2>";
        echo "<ul>";
        while ($row = mysqli_fetch_assoc($result_sd)) {
            echo "<li>{$row['name']}</li>";
        }
        echo "</ul>";
    } else {
        echo "No software developers found.";
    }
}

echo "<form action='add.php' method='get'>";
echo "<button type='submit'>Go to Add Page</button>";
echo "</form>";

// Close database connection
mysqli_close($con);
?>