<?php
    require "config.php";
    
    if (!isset($_SESSION['username'])) {
        echo("You are not logged in!");
        exit();
    }
?>