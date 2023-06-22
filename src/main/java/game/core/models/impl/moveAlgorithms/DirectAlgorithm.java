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

    private long timeUntilRemainingInSubway;

    private IExit goalHole;
    private Random random;
    private long lastMoveTime = -1;

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

        this.timeUntilRemainingInSubway = -1;

    }


    private IExit getNextGoalHole(){
        List<IExit> allExits = this.subways.stream().map(s -> s.getExits()).flatMap(List::stream).collect(Collectors.toList());
        return allExits.get(random.nextInt(allExits.size()));
    }

    private IPosition getRandomExitHolePosition(ISubway subway){
        return subway.getExits().get(random.nextInt(subway.getExits().size())).getPosition();
    }

    private IPosition calculateNextPosition(){
        float speed = mouse.getSpeed();

        float diff = (System.currentTimeMillis() - this.lastMoveTime) / 1000.0f;

        IPosition direction = this.goalHole.getPosition().subtract(this.mouse.getPosition());
        IPosition nextPosition = direction.multiply(((speed * diff) / direction.length()));

        if(direction.length() < nextPosition.length()){
            return this.goalHole.getPosition();
        }else{
            return nextPosition.add(this.mouse.getPosition());
        }
    }

    @Override
    public IPosition getNextPosition() {

        if(this.lastMoveTime == -1){
            this.lastMoveTime = System.currentTimeMillis();
        }

        if(this.timeUntilRemainingInSubway == -1){
            this.timeUntilRemainingInSubway = System.currentTimeMillis() + (5000 + random.nextInt(5000));
        }

        if(this.isInSubway && (this.timeUntilRemainingInSubway > System.currentTimeMillis() || this.residingSubway.isGoal())){
            this.lastMoveTime = System.currentTimeMillis();
            return new Position(-1, -1); // has no position
        }else if(this.isInSubway && this.timeUntilRemainingInSubway <= System.currentTimeMillis()){
            //exit subway
            this.isInSubway = false;
            this.goalHole = getNextGoalHole();
            this.residingSubway.exits(this.mouse);
            this.lastMoveTime = System.currentTimeMillis();
            return getRandomExitHolePosition(this.residingSubway);
        }

        if(this.isInSubway == false && this.goalHole.getPosition().equals(this.mouse.getPosition()) == false){
            //move to next exit
            IPosition nextPosition = calculateNextPosition();
            this.lastMoveTime = System.currentTimeMillis();
            return nextPosition;
        }else{
            //mouse may enter subway
            this.timeUntilRemainingInSubway = System.currentTimeMillis() + (5000 + random.nextInt(5000));
            this.isInSubway = true;
            this.residingSubway = this.subways.stream().filter(s -> s.getExits().contains(this.goalHole)).findFirst().orElse(null);
            this.residingSubway.enter(this.mouse);
            this.residingSubway.setCatPositions(this.cats.stream().map(c -> c.getPosition()).collect(Collectors.toList()));
            if(this.residingSubway == null){
                throw new RuntimeException("Could not find subway to hole");
            }
            this.lastMoveTime = System.currentTimeMillis();
            return new Position(-1, -1);
        }

    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.DIRECT;
    }
}
