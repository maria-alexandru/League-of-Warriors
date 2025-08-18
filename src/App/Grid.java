package App;

import Entities.Character;
import Exceptions.ImpossibleMove;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private int rows;
    private int columns;
    private Character character;
    private Cell crtCell;

    private Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>(columns);
            for (int j = 0; j < columns; j++) {
                row.add(new Cell(i, j));
            }
            this.add(row);
        }
    }

    public static Grid generateExampleGrid() {
        Grid newGrid = new Grid(5, 5);

        for (int i = 0; i < 5; i++) {
            ArrayList<Cell> row = newGrid.get(i);
            for (int j = 0; j < 5; j++) {
                Cell cell = row.get(j);
                if (i == 0 && j == 0) {
                    cell.setType(CellEntityType.Player);
                    cell.setVisited();
                    newGrid.crtCell = cell;
                }
                else if ((i == 0 && j == 3) || (i ==1 && j == 3) || (i == 2 && j == 0) || (i == 4 && j == 3))
                    cell.setType(CellEntityType.Sanctuary);
                else if (i == 4 && j == 4)
                    cell.setType(CellEntityType.Portal);
                else if (i == 3 && j == 4)
                    cell.setType(CellEntityType.Enemy);
            }

        }


        return newGrid;
    }

    public static Grid generateGrid(int rows, int columns) {
        Grid newGrid = new Grid(rows, columns);

        Random rand = new Random();
        int maxCells = rows * columns;

        int minEnemies = 4, maxEnemies = rows * columns / 4;
        if (maxEnemies < minEnemies)
            maxEnemies = minEnemies;

        int minSanctuaries = 2, maxSanctuaries = rows * columns / 4;
        if (maxSanctuaries < minSanctuaries)
            maxSanctuaries = minSanctuaries;

        int nrEnemies = rand.nextInt(maxEnemies - minEnemies + 1) + minEnemies;
        int nrPortals = 1;
        int nrSanctuaries = rand.nextInt(maxSanctuaries - minSanctuaries + 1) + minSanctuaries;

        ArrayList<Integer> cellsIndex = new ArrayList<Integer>(maxCells);
        for (int i = 0; i < maxCells; i++)
            cellsIndex.add(i);

        int index = rand.nextInt(cellsIndex.size());
        ArrayList<Cell> row = newGrid.get(cellsIndex.get(index) / columns);
        Cell cell = row.get(cellsIndex.get(index) % columns);
        cell.setType(CellEntityType.Player);
        cell.setVisited();
        cellsIndex.remove(index);
        newGrid.crtCell = cell;

        for (int i = 0; i < nrEnemies; i++) {
            index = rand.nextInt(cellsIndex.size());
            row = newGrid.get(cellsIndex.get(index) / columns);
            cell = row.get(cellsIndex.get(index) % columns);
            cell.setType(CellEntityType.Enemy);
            cellsIndex.remove(index);
        }

        index = rand.nextInt(cellsIndex.size());
        row = newGrid.get(cellsIndex.get(index) / columns);
        cell = row.get(cellsIndex.get(index) % columns);
        cell.setType(CellEntityType.Portal);
        cellsIndex.remove(index);

        for (int i = 0; i < nrSanctuaries; i++) {
            index = rand.nextInt(cellsIndex.size());
            row = newGrid.get(cellsIndex.get(index) / columns);
            cell = row.get(cellsIndex.get(index) % columns);
            cell.setType(CellEntityType.Sanctuary);
            cellsIndex.remove(index);
        }

        return newGrid;
    }

    public void goNorth() throws ImpossibleMove {
        int row = crtCell.getRow();
        int col = crtCell.getCol();
        row -= 1;
        if (row < 0)
            throw new ImpossibleMove();

        crtCell.setType(CellEntityType.Void);

        crtCell = get(row).get(col);
        crtCell.setVisited();
    }

    public void goSouth() throws ImpossibleMove {
        int row = crtCell.getRow();
        int col = crtCell.getCol();
        row += 1;
        if (row >= rows)
            throw new ImpossibleMove();

        crtCell.setType(CellEntityType.Void);

        crtCell = get(row).get(col);
        crtCell.setVisited();
    }

    public void goWest() throws ImpossibleMove {
        int row = crtCell.getRow();
        int col = crtCell.getCol();
        col -= 1;
        if (col < 0)
            throw new ImpossibleMove();

        crtCell.setType(CellEntityType.Void);

        crtCell = get(row).get(col);
        crtCell.setVisited();
    }

    public void goEast() throws ImpossibleMove {
        int row = crtCell.getRow();
        int col = crtCell.getCol();
        col += 1;
        if (col >= columns)
            throw new ImpossibleMove();

        crtCell.setType(CellEntityType.Void);

        crtCell = get(row).get(col);
        crtCell.setVisited();
    }

    public String toString() {
        String str = new String();
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = get(i);
            for (int j = 0; j < columns; j++) {
                Cell cell = row.get(j);
                if (cell.isVisited() && cell != crtCell)
                    str += "V ";
                else {
                    if (cell.getType() == CellEntityType.Player || crtCell == cell)
                        str += "P ";
                    else
                        str += "N ";
                }

            }
            str += "\n";
        }
        return str;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getColumns() {
        return columns;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCrtCell(Cell crtCell) {
        this.crtCell = crtCell;
    }

    public Cell getCrtCell() {
        return crtCell;
    }
}
