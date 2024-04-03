const dragZone = document.getElementById("dragZone");
const dropZone = document.getElementById("dropZone");

dragZone.addEventListener("dragstart", (event) => {
  ///dragstart event
  event.dataTransfer.setData("text/plain", event.target.id);
});

dropZone.addEventListener("dragover", (event) => {
  event.preventDefault();
});

dropZone.addEventListener("drop", (event) => {
  event.preventDefault();
  const draggedId = event.dataTransfer.getData("text/plain");
  const draggedElement = document.getElementById(draggedId);
  dropZone.appendChild(draggedElement); //move to srop zone
});
