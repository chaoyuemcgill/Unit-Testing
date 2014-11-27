/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplayModel;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jonti
 */
public class ArtificialIntelligence 
{
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Brick> bricks;
	private ArrayList<Bomb> bombs;
        private CollisionDetector detector;
        private Bomberman bomberman;
        boolean collision;
 
	    public ArtificialIntelligence(Bomberman b,ArrayList<Enemy> e, ArrayList<Brick> br, ArrayList<Bomb> bo, CollisionDetector cD) {
	    	
	    	enemies = e;
	    	bricks = br;
	    	bombs = bo;
	    	detector = cD;
                bomberman =b;
                collision = false;
                
	       
	    }
	    
	    public void updatePosition() 
            {
	    	for(Enemy temp : enemies)
                {
	    		
	    		if (!temp.isDead()) {
                    boolean isAlignedWithColumn = ((temp.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH) % (GridObject.EFFECTIVE_PIXEL_WIDTH * 2)==0);
                    boolean isAlignedWithRow = ((temp.getYPosition() - GridObject.EFFECTIVE_PIXEL_HEIGHT) % (GridObject.EFFECTIVE_PIXEL_HEIGHT * 2)) == 0;
                    
                    if(isAlignedWithColumn &&(!(isAlignedWithRow)))
                    {
                        if(temp.getDirection()>1)
                        {
                            temp.setDirection(0);
                        }
                    }
                    if(isAlignedWithRow &&(!(isAlignedWithColumn)))
                    {
                        if(temp.getDirection()<2)
                        {
                            temp.setDirection(2);
                        }
                    }
                   
                    if(temp.getSmartness()>1)
                    {
                      if(isAlignedWithColumn && isAlignedWithRow)
                      {
                        int collisionCount = 0;
                        if(!(temp.wallpass))
                        {
                            if(temp.getDirection()<2)
                            {
                        
                                for(Brick brick:bricks)
                                {
                                    if ((brick.getXPosition() == temp.getXPosition()-GridObject.EFFECTIVE_PIXEL_WIDTH)&&(brick.getYPosition()==temp.getYPosition()))
                                    {
                                        collisionCount++;
                                        break;
                                    }
                                    if ((brick.getXPosition() == temp.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH)&&(brick.getYPosition()==temp.getYPosition()))
                                    {
                                        collisionCount++;
                                        break;
                                    } 
                                }
                                for(Bomb bomb:bombs)
                                {
                                    if (bomb.getXPosition() == temp.getXPosition()-GridObject.EFFECTIVE_PIXEL_WIDTH)
                                    {
                                        collisionCount++;
                                    }
                                    if (bomb.getXPosition() == temp.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH)
                                    {
                                        collisionCount++;
                                    }   
                                }
                            }
                            else if (temp.getDirection()>1)
                            {                   
                                for(Brick brick:bricks)
                                {
                                    if ((brick.getYPosition() == temp.getYPosition()-GridObject.EFFECTIVE_PIXEL_HEIGHT)&&(brick.getXPosition()==temp.getXPosition()))
                                    {
                                        collisionCount++;
                                        // System.out.println("hiya");
                                        break;
                                    }
                                    if ((brick.getYPosition() == temp.getYPosition()+GridObject.EFFECTIVE_PIXEL_HEIGHT)&&(brick.getXPosition()==temp.getXPosition()))
                                    {
                                        collisionCount++;
                                        break;
                                    }   
                                }
                                for(Bomb bomb:bombs)
                                {
                                    if (bomb.getYPosition() == temp.getYPosition()-GridObject.EFFECTIVE_PIXEL_HEIGHT)
                                    {
                                        collisionCount++;
                                    }
                                    if (bomb.getYPosition() == temp.getYPosition()+GridObject.EFFECTIVE_PIXEL_HEIGHT)
                                    {
                                        collisionCount++;
                                    }   
                                }
                            }
                        }
                      //System.out.println(collisionCount);
                        if(collisionCount<2)
                        {
                           int chance = 0;
                           if(temp.getSmartness()==2)
                               chance = 8;
                           if(temp.getSmartness()==3)
                           {
                               chance = 4;
                           }
                           Random rn = new Random();
                           int j = rn.nextInt(10);
                           // System.out.println("j = "+j);
                           if(j>chance)
                           {
                                int q = rn.nextInt(3);
                                //System.out.println("JACKPOT! Q = "+q);
                                if(temp.getDirection()>1)
                                {
                                    temp.setDirection(q);
                                }
                                else
                                    temp.setDirection(q+2); 
                           }          
                        }
                      }
                    }
                  //System.out.println("DIRECTION -> "+temp.getDirection());

                    if(temp.getSmartness()==2)
                    {
                        if(bomberman.getXPosition() == temp.getXPosition())
                        {
                            if((bomberman.getYPosition()-GridObject.EFFECTIVE_PIXEL_HEIGHT*(2) <= temp.getYPosition())&&(temp.getYPosition()<bomberman.getYPosition()))
                                 temp.setDirection(1);
                            if((bomberman.getYPosition()+GridObject.EFFECTIVE_PIXEL_HEIGHT*(2) >= temp.getYPosition())&&(temp.getYPosition()>bomberman.getYPosition()))
                                 temp.setDirection(0);
                        }
                         if(bomberman.getYPosition() == temp.getYPosition())
                        {
                            if((bomberman.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH*(2) >= temp.getXPosition())&&(temp.getXPosition()>bomberman.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH/2))
                            {   
                               // System.out.println(temp.getXPosition()+", "+temp.getYPosition()+" ----- bomberman : "+bomberman.getXPosition()+", "+bomberman.getYPosition());
                                temp.setDirection(2);
                            }
                            if((bomberman.getXPosition()-GridObject.EFFECTIVE_PIXEL_WIDTH*(2) <= temp.getXPosition())&&(temp.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH/2<bomberman.getXPosition()))
                                 temp.setDirection(3);
                        }
                     }      

                  
                    if(temp.getDirection()==0)
                    {
                        temp.setYPosition(temp.getYPosition()-temp.getSpeed());
                    }
                    else if(temp.getDirection()==1)
                    {
                        temp.setYPosition(temp.getYPosition()+temp.getSpeed());
                    }
                    else if(temp.getDirection()==2)
                    {
                        temp.setXPosition(temp.getXPosition()-temp.getSpeed());
                    }
                    else if(temp.getDirection()==3)
                    {
                        temp.setXPosition(temp.getXPosition()+temp.getSpeed());
                    }
	    		}
                }
                for(Enemy temp : enemies)
                {
                    collision = false;


                   // System.out.println("--->"+temp.direction);

                    //System.out.println("--->"+temp.direction);


                    if (temp.isConcreteCollision())
                        collision = true;
                  if(!(temp.wallpass))
                  {
                    for(Brick brick: bricks)
                    {
                        
                       if(temp.getDirection()==0)
                       {
                        if(detector.checkUpCollision(temp,brick))
                        {
                            collision = true;
                        }
                       }
                       else if(temp.getDirection()==1)
                       {
                        if(detector.checkDownCollision(temp,brick))
                        {
                             collision = true;
                        }
                       }
                       else if(temp.getDirection()==2)
                       {
                        if(detector.checkLeftCollision(temp,brick))
                        {
                             collision = true;
                        }
                       }
                       else if(temp.getDirection()==3)
                       {
                        if(detector.checkRightCollision(temp,brick))
                        {
                             collision = true;
                        }
                       }
                    }
                    for(Bomb bomb : bombs)
                    {
                        if(temp.getDirection()==0)
                       {
                        if(detector.checkUpCollision(temp,bomb))
                        {
                            collision = true;
                        }
                       }
                       else if(temp.getDirection()==1)
                       {
                        if(detector.checkDownCollision(temp,bomb))
                        {
                               collision = true;
                        }
                       }
                       else if(temp.getDirection()==2)
                       {
                        if(detector.checkLeftCollision(temp,bomb))
                        {
                               collision = true;
                        }
                       }
                       else if(temp.getDirection()==3)
                       {
                        if(detector.checkRightCollision(temp,bomb))
                        {
                               collision = true;
                        }
                       }
                           
                        

                    }

                  }


                  
                    /*

                    for(Enemy enemy: enemies)
                    {
                      if(!(enemy.equals(temp)))
                      {
                        if(detector.checkCollision(temp,enemy))
                        {
                            //System.out.println("!!!!!!!!!!!!!!!!");
                            collision = true;
                        }
                      }
                    }

  */                  

                    if(collision)
                    {
                        /*if(temp.isConcreteCollision())
                        {
                            if (temp.getDirection()==0)
                            {
                                temp.setYPosition(temp.getYPosition()+temp.getSpeed());
                                if(temp.isConcreteCollision())
                                {
                                    temp.setDirection(3);
                                    
                                }
                                else
                                    temp.setYPosition(temp.getYPosition()-temp.getSpeed());
                            }
                        }*/
                        
                    
                        if(temp.getDirection()==0)
                        {
                            //System.out.println("lol");
                            temp.setYPosition(temp.getYPosition()+temp.getSpeed());
                            temp.setDirection(1);
                        }
                        else if(temp.getDirection()==1)
                        {
                            //System.out.println("kek");
                            temp.setYPosition(temp.getYPosition()-temp.getSpeed());
                            temp.setDirection(0);
                        }
                        else if(temp.getDirection()==2)
                        {
                            temp.setXPosition(temp.getXPosition()+temp.getSpeed());
                            temp.setDirection(3);
                        }
                        else if(temp.getDirection()==3)
                        {
                            temp.setXPosition(temp.getXPosition()-temp.getSpeed());
                            temp.setDirection(2);
                        }
                    }
                    
                    
                    
                }
                        
                }
}
 
	    

   

