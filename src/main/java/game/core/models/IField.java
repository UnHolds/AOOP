package game.core.models;

import java.util.List;
import java.util.Map;

public interface IField {

    int getRowCount();

    int getColumnCount();

    List<ISubway> getSubways();
}
