package game.core.models.impl;

import game.core.models.IMoveAlgorithm;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.IMouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Mouse extends Character implements IMouse {


    private IMoveAlgorithm moveAlgorithm;

    public Mouse(String id, Position position, String imagePath) {
        super(id, position, imagePath);
    }




    public void move(){
        //TODO fix thix
        this.moveAlgorithm.move();

        super.move();
    }


    @Override
    public void setMoveAlgorithm(IMoveAlgorithm algorithm) {
        this.moveAlgorithm = algorithm;
    }
}
