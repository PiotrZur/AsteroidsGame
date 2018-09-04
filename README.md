# AsteroidsGame 
Game based on classic Atari asteroid game. Player needs to shoot asteroids using space bar and rotate space ship
using arrow keys in order to avoid collision. Animations, collision and graphix is created in JavaFX.
Application have following classes:

* AsteroidsApp - starting point of aplication and all game mechanics.
* AssetLoader - Singleton taht loads graphical assets from hard drive to memory.
* AnimatedImage - class used to animate ship explosion.
* UI - controls design and behaviour of UI elements.
* GameObject - controls movement, rotation and collision of objects in game. Have sublcaesses of Enemy, Bullet and Player.
