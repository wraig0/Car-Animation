# Car-Animation
Java application, simulating a car race.

## What's happening
This is an application I am currently working on, progress so far:
- [x] implement car as array of images
- [x] implement race track
- [x] implement controls
- [ ] implement cross network play

### Current item
Working on implementing the car controls, stuff like this code once working will be changed to car class variables and methods.
```
public void changePos()
    {
         if (currentImage == 1) // if pointing a direction, go that way 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed     up
         }
         
         if (currentImage == 2) // if pointing a direction, go that way 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed
             xPos = xPos + 1 * speedf; // displacement = distance * speed

         }
         
         if (currentImage == 3) // if pointing a direction, go that way 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed
             xPos = xPos + 2 * speedf; // displacement = distance * speed

         }
```
         
