function buildBoardString() {
  let result = "";
  document.querySelectorAll(".cell").forEach((cell) => {
    const number = cell.value;
    result += number === "" ? "0" : number;
  });
  document.getElementById("boardString").value = result;
}
