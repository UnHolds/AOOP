package game.core.models.impl;

import game.core.models.IField;
import game.core.models.ISubway;

import java.util.List;

public class Field implements IField {

    private int rowCount;
    private int colCount;
    private List<ISubway> subways;


    public Field(int rowCount, int colCount, List<ISubway> subways){
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.subways = subways;
    }

    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    @Override
    public int getColumnCount() {
        return this.colCount;
    }

    @Override
    public List<ISubway> getSubways() {
        return this.subways;
    }
}
