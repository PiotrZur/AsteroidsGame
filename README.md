# AsteroidsGame 
Game based on classic Atari asteroid game. Player needs to shoot asteroids using space bar and rotate space ship
using arrow keys in order to avoid collision. Animations, collision and graphix is created in JavaFX.
Application have following classes:

* Main - starting point of aplication and game loop.
* Defines - static default values container.
* GameState - Game logic.
* animation package
  * AnimatedImage - class used to create animation from sprite sheet.
  * Explosion - animated explosion of ship.
* assets package
  * AssetLoader - loads graphical assets from hard drive to memory.
* gameObjects package
  * gameObject - controls movement, rotation and collision of objects in game.
  * GameObjcetFactory -facotry class, build enemies, bullets and player.
* ui package
  * UIController - controls design and behaviour of UI elements
