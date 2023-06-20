package game.core.models;

import java.util.List;

public interface IGame {
    IField getField();

    List<IPlayer> getPlayers();

    List<IMouse> getMice();
}
