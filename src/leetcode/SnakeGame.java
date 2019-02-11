package leetcode;

import java.util.Deque;
import java.util.LinkedList;


//353. Design Snake Game
public class SnakeGame {
    
    LinkedList<Position> snake;
    int[][] food;
    int width; int height;
    int score;
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width; 
        this.height = height;
        this.food = food; 
        snake = new LinkedList<Position>();
        snake.add(new Position(0,0));
        score = 0;
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(score == -1 ) return -1;  //game over
//        Position head = snake.peekFirst(); //pass by reference will change real memory
        Position head = new Position(snake.peekFirst().x, snake.peekFirst().y);
        //move head
//        if(direction == "U"){ (wrong)
//        if(direction.equals("U")){ //(right string compare!!!)
        switch(direction){
	        case "U": head.x--; break;
	        case "L": head.y--; break;
	        case "R": head.y++; break;
	        case "D": head.x++; break;
        }
        
        //check if head hits border
        if(head.x >= height || head.x<0 || head.y>=width || head.y<0) {
            score = -1;
            return -1;
        }
        //check if head hits body
//        for(Position body : snake){
//            if(body.isEqual(head)){
//                score = -1;
//                return -1;
//            }
//        }
        //for wrong case: [null,0,1,1,1,1,2,2,2,2,3,3,-1] 
        // right: [null,0,1,1,1,1,2,2,2,2,3,3,3]
        for(int i=1;i<snake.size()-1;i++){ //!!! skip tail because it potentially moves with head at same time
            Position next = snake.get(i);
            if(next.isEqual(head)) return -1;	       
        }
        //not hit ==> move body
        snake.addFirst(head);
        //check if eats a food
        if(score < food.length){
            Position fd = new Position(food[score][0], food[score][1]);
            if(fd.isEqual(head)){
                score++;
            }
        }
        while(snake.size()>score+1) snake.removeLast(); //remove tail
        return score;
    }
    
    //self-defined class
    class Position{
        int x;
        int y;
        
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public boolean isEqual(Position p){
            return p.x == this.x && p.y == this.y;
        }
    }
    
}
