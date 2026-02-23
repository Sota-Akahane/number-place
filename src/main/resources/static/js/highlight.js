const cells = document.querySelectorAll("input.cell");

cells.forEach((cell) => {
  cell.addEventListener("focus", () => {
    clearHighlight();

    cell.classList.add("selected");
    highlightRelatedCells(cell);
  });

  cell.addEventListener("blur", () => {
    clearHighlight();
  });
});

function clearHighlight() {
  cells.forEach((cell) => {
    cell.classList.remove("selected", "active");
  });
}

function highlightRelatedCells(target) {
  const td = target.closest("td");
  const tr = td.parentElement;

  const rowIndex = tr.rowIndex;
  const colIndex = td.cellIndex;

  // 同じ行
  document
    .querySelectorAll(`table.sudoku tr:nth-child(${rowIndex + 1}) input.cell`)
    .forEach((c) => c.classList.add("active"));

  // 同じ列
  document
    .querySelectorAll(
      `table.sudoku tr td:nth-child(${colIndex + 1}) input.cell`,
    )
    .forEach((c) => c.classList.add("active"));

  // 同じ 3x3 ブロック
  const blockRow = Math.floor(rowIndex / 3);
  const blockCol = Math.floor(colIndex / 3);

  document.querySelectorAll("table.sudoku tr").forEach((row, r) => {
    if (Math.floor(r / 3) !== blockRow) return;

    row.querySelectorAll("td").forEach((cellTd, c) => {
      if (Math.floor(c / 3) !== blockCol) return;
      cellTd.querySelector("input.cell").classList.add("active");
    });
  });
}
