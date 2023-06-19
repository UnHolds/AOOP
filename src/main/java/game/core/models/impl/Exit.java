package game.core.models.impl;

import game.core.models.IExit;
import game.core.models.IPosition;

public class Exit implements IExit {


    private IPosition position;

    public Exit(IPosition position){
        this.position = position;
    }

    @Override
    public IPosition getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(IPosition position) {
        this.position = position;
    }
}
