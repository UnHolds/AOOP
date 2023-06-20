package game.core.models.impl.moveAlgorithms;

import game.core.models.*;
import game.core.models.impl.Position;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DirectAlgorithm implements IMoveAlgorithm {


    private IMouse mouse;
    private List<ISubway> subways;
    private List<IPlayer> cats;

    boolean isInSubway;
    private ISubway residingSubway;

    private int ticksRemainingInSubway;

    private IExit goalHole;
    private Random random;

    public DirectAlgorithm(IMouse mouse, List<ISubway> subways, List<IPlayer> cats){
        this.mouse = mouse;
        this.subways = subways;
        this.cats = cats;
        this.isInSubway = true;
        this.residingSubway = subways.stream().filter(s -> s.getMice().stream().map(m -> m.getId()).collect(Collectors.toList()).contains(mouse.getId())).findFirst().orElse(null);
        this.random = new Random();
        if(this.residingSubway == null){
            throw new RuntimeException("Residing subway can't be found, mouse needs to be in a subway");
        }

        this.ticksRemainingInSubway = 5 + random.nextInt(5); //TODO change later

    }


    private IExit getNextGoalHole(){
        List<IExit> allExits = this.subways.stream().map(s -> s.getExits()).flatMap(List::stream).collect(Collectors.toList());
        return allExits.get(random.nextInt(allExits.size()));
    }

    private IPosition getRandomExitHolePosition(ISubway subway){
        return subway.getExits().get(random.nextInt(subway.getExits().size())).getPosition();
    }

    private IPosition calculateNextPosition(){
        IPosition direction = this.goalHole.getPosition().subtract(this.mouse.getPosition());
        IPosition nextPosition = direction.multiply((mouse.getSpeed() / direction.length()));

        if(direction.length() < nextPosition.length()){
            return this.goalHole.getPosition();
        }else{
            return nextPosition.add(this.mouse.getPosition());
        }
    }

    @Override
    public IPosition getNextPosition() {

        if(this.isInSubway && (this.ticksRemainingInSubway > 0 || this.residingSubway.isGoal())){
            this.ticksRemainingInSubway--;
            return new Position(-1, -1); // has no position
        }else if(this.isInSubway && this.ticksRemainingInSubway == 0){
            //exit subway
            this.isInSubway = false;
            this.goalHole = getNextGoalHole();
            this.residingSubway.exits(this.mouse);
            return getRandomExitHolePosition(this.residingSubway);
        }

        if(this.isInSubway == false && this.goalHole.getPosition().equals(this.mouse.getPosition()) == false){
            //move to next exit
            return calculateNextPosition();
        }else{
            //mouse may enter subway
            this.ticksRemainingInSubway = 10 + random.nextInt(10); //TODO change later;
            this.isInSubway = true;
            this.residingSubway = this.subways.stream().filter(s -> s.getExits().contains(this.goalHole)).findFirst().orElse(null);
            this.residingSubway.enter(this.mouse);
            this.residingSubway.setCatPositions(this.cats.stream().map(c -> c.getPosition()).collect(Collectors.toList()));
            if(this.residingSubway == null){
                throw new RuntimeException("Could not find subway to hole");
            }
            return new Position(-1, -1);
        }

    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.DIRECT;
    }
}
