<?php
    require "config.php";
    $_SESSION["username"] = $row[""];
    header("Location: ../login.php");
?>