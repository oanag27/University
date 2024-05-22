<?php
include 'db.php';

$con = OpenConnection();

$result = $con->query("SELECT * FROM documents");

$documents = array();
while ($row = $result->fetch_assoc()) {
    $documents[] = $row;
}

CloseConnection($con);

//echo json_encode($documents);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document Management System - Browse Documents</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Document Management System - Browse Documents</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Author</th>
                <th>Title</th>
                <th>Number of Pages</th>
                <th>Type</th>
                <th>Format</th>
            </tr>
        </thead>
        <tbody id="documentTable">
        </tbody>
    </table>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var documents = <?php echo json_encode($documents); ?>;
            var tableContent = "";
            documents.forEach(function(doc) {
                tableContent += `<tr>
                                    <td>${doc.id}</td>
                                    <td>${doc.author}</td>
                                    <td>${doc.title}</td>
                                    <td>${doc.num_pages}</td>
                                    <td>${doc.type}</td>
                                    <td>${doc.format}</td>
                                </tr>`;
            });
            document.getElementById("documentTable").innerHTML = tableContent;
        });
    </script>
</body>
</html>