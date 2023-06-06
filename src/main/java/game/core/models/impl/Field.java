package game.core.models.impl;

import game.core.models.IField;

import java.util.Map;

public class Field implements IField {
    private int rowCount;
    private int columnCount;

    private Map<Integer, Subway> subways;

    public Field(int rowCount, int columns, Map<Integer, Subway> subways) {
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

    public Map<Integer, Subway> getSubways() {
        return subways;
    }
}
