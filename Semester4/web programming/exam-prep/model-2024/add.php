<?php
include 'dbCon.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (isset($_POST['username']) && isset($_POST['project'])) {
        $username = $_POST['username'];
        $project = $_POST['project'];

        // Prevent SQL injection
        $username = mysqli_real_escape_string($con, $username);
        $project = mysqli_real_escape_string($con, $project);

        $check_query = "SELECT * FROM SoftwareDeveloper WHERE name = '$username'";
        $check_result = mysqli_query($con, $check_query);

        if (mysqli_num_rows($check_result) == 1) {
            $project_query = "SELECT * FROM Project WHERE name = '$project'";
            $project_result = mysqli_query($con, $project_query);

            if (mysqli_num_rows($project_result) == 0) {
                $add_project_query = "INSERT INTO Project (name) VALUES ('$project')";
                mysqli_query($con, $add_project_query);
            }
            $assign_query = "UPDATE Project SET members = CONCAT_WS(',', members, '$username') WHERE name = '$project'";
            mysqli_query($con, $assign_query);

            echo "Developer '$username' assigned to project '$project' successfully.";
        } else {
            echo "Developer '$username' doesn't exist.";
        }
    } else {
        echo "Username and project name are required.";
    }
}

mysqli_close($con);
?>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add SD Page</title>
  </head>
  <body>
    <div class="login-container">
      <h2>Add sd to project</h2>
      <form action="add.php" method="post">
        <input type="text" name="username" placeholder="Username" required />
        <input type="text" name="project" placeholder="Project" required />
        <button type="submit">Add</button>
      </form>
    </div>
  </body>
</html>


