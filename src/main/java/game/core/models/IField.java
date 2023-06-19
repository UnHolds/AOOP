package game.core.models;

import java.util.Map;

public interface IField {

    int getRowCount();

    int getColumnCount();

    Map<Integer, ISubway> getSubways();
}
