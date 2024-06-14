<?php
    function get_all_filtered($rank1, $rank2) {
        require "config.php";
        //global $conn;
    
        $sql = "SELECT * FROM Avatar WHERE rank >= ? AND rank <= ?";
        $select_stmt = mysqli_stmt_init($conn);
    
        if (!mysqli_stmt_prepare($select_stmt, $sql)) {
            echo("There was an error! Please try again later.");
            exit();
        }
        
        mysqli_stmt_bind_param($select_stmt, "ii", $rank1, $rank2);
        mysqli_stmt_execute($select_stmt);
        $results = mysqli_stmt_get_result($select_stmt);
    
        $entities = array();
        while ($row = mysqli_fetch_assoc($results)) {
            array_push($entities, $row);
        }
    
        return $entities;
    }
?>