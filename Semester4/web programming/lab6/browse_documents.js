function fetchDocumentsByType() {
  var type = $("#documentType").val();
  $.ajax({
    url: "fetch_documents_by_type.php",
    type: "GET",
    data: { type: type },
    success: function (data) {
      displayDocuments(data);
    },
  });
}

function fetchDocumentsByFormat() {
  var format = $("#documentFormat").val();
  $.ajax({
    url: "fetch_documents_by_format.php",
    type: "GET",
    data: { format: format },
    success: function (data) {
      displayDocuments(data);
    },
  });
}

function displayDocuments(data) {
  const documents = JSON.parse(data);
  let documentList = "";
  documentList += "author - title";
  documents.forEach((doc) => {
    documentList += `<div>${doc.author} - ${doc.title} </div>`;
  });
  $("#documentList").html(documentList);
}

// $(document).ready(function () {
//   $("#documentType").change(function () {
//     const selectedType = $(this).val();
//     fetchDocumentsByType(selectedType);
//   });

//   $("#documentFormat").change(function () {
//     const selectedFormat = $(this).val();
//     fetchDocumentsByFormat(selectedFormat);
//   });

//   const previousType = sessionStorage.getItem("previousType");
//   const previousFormat = sessionStorage.getItem("previousFormat");
//   if (previousType) {
//     $("#documentType").val(previousType);
//     fetchDocumentsByType(previousType);
//   }
//   if (previousFormat) {
//     $("#documentFormat").val(previousFormat);
//     fetchDocumentsByFormat(previousFormat);
//   }
// });
