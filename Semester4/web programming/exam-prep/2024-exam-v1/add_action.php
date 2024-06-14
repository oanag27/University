<?php

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add</title>
</head>
<body>
    
    <h1>Add new auction</h1>
    <hr>
    <form action="actions/action_add.php" method="post">
        <input type="text" name="name" placeholder="Name">
        <input type="text" name="age" placeholder="Age">
        <input type="text" name="power" placeholder="Power">
        <input type="text" name="rank" placeholder="Rank">
        <button>Add</button>
    </form>

</body>
</html>