package game.core.models;

import game.core.models.impl.Subway;

import java.util.Set;

public interface IField {

    int getRowCount();

    int getColumnCount();

    Set<Subway> getSubways();
}
