### DESIGN
#### Name: Caleb Sanford 

##### Development Role

Caleb Sanford designed and wrote all of the code seen in this project. 

##### Design Goals
- __What are the project's design goals, specifically what kinds of new features did you want to make easy to add__
    - The design goals of this project was to make the Balls and the Bricks easy to expand upon. The goals was to 
    do this by making the classes for these objects self contained and easy to change within the Main class.
- __Describe the high-level design of your project, focusing on the purpose and interaction of the core classes__
    - Main Class:
        - initializes the game
        - contains the main game loop that runs continuously.
        - handles all of the keyboard and mouse inputs
        - computes the ball angles on contact with walls or bricks
        - Stores the array of balls and bricks that are active on the screen
    - Ball Class:
        - Creates and store the Circle object for a Ball
        - keep track of the angle and direction of motion for a Ball
        - sets the Ball position based on the elapsed time between calls
    - Brick Class:
        - Create and store a Rectangle object to display a Brick
        - Create and store a Text object to display strength of Brick
        - Method that reduces the strength of a Brick
            - Decrement strength
            - Set updated text
            - Set visibility to false if strength is 0
        - Set visibility of Brick and text when brick is created or destroyed
    - Status Class:
        - Store the score
        - Store the lives left
        - Draw the status bar
- __What assumptions or decisions were made to simplify your project's design, 
especially those that affected adding required features__
    - I tried to keep all of the functionality (even the methods to draw the objects) within
    the specific class. I have not done this perfectly, I think that the status method 
    needs to be expanded upon to add the methods to manage the levels.
- __Describe, in detail, how to add new features to your project, especially ones you were not 
able to complete by the deadline__
    - To add new styles of Bricks, Brick subclasses need to be created. The 
    style of brick can be identified by a letter in front of a number in the level.txt 
    files. Overall, a lot of work would be needed to add new bricks. This is something 
    that needs to be improved in this code. 
    - To add different styles of paddles the same is true, a lot of work is needed. This 
    is a design element that I didn't plan out. 

