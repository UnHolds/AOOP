package game.core.models.impl;

import game.core.models.IField;

import java.util.Set;

public class Field implements IField {
    private int rowCount;
    private int columnCount;

    private Set<Subway> subways;

    public Field(int rowCount, int columns, Set<Subway> subways) {
        this.rowCount = rowCount;
        this.columnCount = columns;
        this.subways = subways;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Set<Subway> getSubways() {
        return subways;
    }
}
