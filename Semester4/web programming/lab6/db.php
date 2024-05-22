<?php
function OpenConnection(): mysqli
{
    $server = "localhost"; // Localhost since we are running it locally
    $user = "root"; // Default XAMPP user
    $password = ""; // Default XAMPP password (usually empty)
    $database = "documents_database";

    $con = new mysqli($server, $user, $password, $database);

    if ($con->connect_error) {
        die('Connection failed: ' . $con->connect_error);
    }
    
    return $con;
}

function CloseConnection(mysqli $con)
{
    $con->close();
}
?>
