<?php
    function get_all() {
        require "config.php";

        $sql = "SELECT * FROM Avatar";
        $select_stmt = mysqli_stmt_init($conn);

        if (!mysqli_stmt_prepare($select_stmt, $sql)) {
            echo("There was an error! Please try again later.");
            exit();
        }
        mysqli_stmt_execute($select_stmt);
        $results = mysqli_stmt_get_result($select_stmt);

        $entities = array();
        while ($row = mysqli_fetch_assoc($results)) {
            array_push($entities, $row);
        }

        return $entities;
    }   
?>