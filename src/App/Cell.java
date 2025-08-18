package App;

public class Cell {
    private int row;
    private int col;
    private CellEntityType type;
    private boolean visited;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.type = CellEntityType.Void;
        this.visited = false;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public CellEntityType getType() {
        return type;
    }

    public void setVisited() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }
}
