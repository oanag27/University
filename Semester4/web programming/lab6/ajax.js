function fetchDocuments() {
  $.ajax({
    url: "select_documents.php",
    type: "GET",
    success: function (data) {
      const documents = JSON.parse(data);
      let tableContent = "";
      documents.forEach((doc) => {
        tableContent += `<tr>
                                    <td>${doc.author}</td>
                                    <td>${doc.title}</td>
                                    <td>${doc.num_pages}</td>
                                    <td>${doc.type}</td>
                                    <td>${doc.format}</td>
                                    <td><button onclick="editDocument(${doc.id})">Edit</button></td>
                                    <td><button onclick="deleteDocument(${doc.id})">Delete</button></td>
                                 </tr>`;
      });
      document.getElementById("documentTable").innerHTML = tableContent;
    },
  });
}

function addDocument() {
  const author = $("#author").val();
  const title = $("#title").val();
  const num_pages = $("#num_pages").val();
  const type = $("#type").val();
  const format = $("#format").val();

  $.ajax({
    url: "insert_document.php",
    type: "POST",
    data: {
      author: author,
      title: title,
      num_pages: num_pages,
      type: type,
      format: format,
    },
    success: function (response) {
      alert(response);
      fetchDocuments();
    },
  });
}

function editDocument(id) {
  // Fetch document data and populate the form for editing
  $.ajax({
    url: "select_documents.php",
    type: "GET",
    success: function (data) {
      const documents = JSON.parse(data);
      const document = documents.find((doc) => doc.id === id);

      $("#author").val(document.author);
      $("#title").val(document.title);
      $("#num_pages").val(document.num_pages);
      $("#type").val(document.type);
      $("#format").val(document.format);
      $("#documentForm").append(
        `<input type="hidden" id="doc_id" value="${document.id}">`
      );
      $("#submitBtn")
        .attr("onclick", "updateDocument()")
        .val("Update Document");
    },
  });
}
function updateDocument() {
  const id = $("#doc_id").val();
  const author = $("#author").val();
  const title = $("#title").val();
  const num_pages = $("#num_pages").val();
  const type = $("#type").val();
  const format = $("#format").val();

  $.ajax({
    url: "update_document.php",
    type: "POST",
    data: {
      id: id,
      author: author,
      title: title,
      num_pages: num_pages,
      type: type,
      format: format,
    },
    success: function (response) {
      alert(response);
      fetchDocuments();
      $("#submitBtn").attr("onclick", "addDocument()").val("Add Document");
      $("#documentForm").find("input[type=hidden]").remove();
      $("#documentForm")[0].reset();
    },
  });
}

function deleteDocument(id) {
  $.ajax({
    url: "delete_document.php",
    type: "POST",
    data: { id: id },
    success: function (response) {
      alert(response);
      fetchDocuments();
    },
  });
}

$(document).ready(function () {
  fetchDocuments();
});


