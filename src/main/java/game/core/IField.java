package game.core;

import java.util.List;

public interface IField {

    List<IPosition> getCharacterPositions();

    List<IPosition> getTunnelEntrancePositions();

    void setMultipleCharacterPositions(List<IPosition> positions);

    void setCharacterPosition(IPosition position);

}
