<?php
include 'dbCon.php'; 

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (isset($_POST['username'])) {
        $username = $_POST['username'];

        // Prevent SQL injection
        $username = mysqli_real_escape_string($con, $username);
        $query = "SELECT * FROM SoftwareDeveloper WHERE name = '$username'";
        $result = mysqli_query($con, $query);

        if ($result) {
            if (mysqli_num_rows($result) == 1) {
                // Redirect to another page if the username is valid
                header("Location: welcome.php");
                exit();
            } else {
                echo 'Invalid username. Please try again.';
            }
        } else {
            echo 'Error: ' . mysqli_error($con);
        }

        // Free result set
        mysqli_free_result($result);
    } else {
        echo 'Username is required.';
    }
}
mysqli_close($con);
?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login Page</title>
  </head>
  <body>
    <div class="login-container">
      <h2>Login</h2>
      <form action="welcome.php" method="post">
        <input type="text" name="username" placeholder="Username" required />
        <button type="submit">Login</button>
      </form>
    </div>
  </body>
</html>


