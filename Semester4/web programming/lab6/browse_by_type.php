<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browse by Type and Format</title>
</head>
<body>
<h2>Browse by Type and Format</h2>

<label for="documentType">Select Document Type:</label>
<select id="documentType">
    <option value="a">a</option>
    <option value="b">b</option>
    <option value="c">c</option>
    <!-- Add more options for different document types -->
</select>
<button onclick="fetchDocumentsByType()">Fetch Documents</button>

<label for="documentFormat">Select Document Format:</label>
<select id="documentFormat">
    <option value="c">c</option>
    <option value="b">b</option>
    <option value="d">d</option>
    <!-- Add more options for different document formats -->
</select>
<button onclick="fetchDocumentsByFormat()">Fetch Documents</button>

    <div id="documentList"></div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="browse_documents.js"></script> <!-- Script to handle AJAX requests -->
    
</body>
</html>
