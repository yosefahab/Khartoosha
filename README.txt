Bassel, 22 Dec 
- Replaced States with screens, deleted unused states 
- modified character class
- modified PlayScreen class 
- added a test map

Bassel, 24 Dec 
- Added Map class 
- Added WorldContactListener Class (IMPORTANT)
	the concept is perfectly explained here: 
	https://www.youtube.com/watch?v=pJ_M_fACtB8&ab_channel=TheCodingTrain
	https://www.youtube.com/watch?v=tcH6Mp03KC0&ab_channel=BrentAureli%27s-CodeSchool
- Added characer going down and jumping through platfrom

Ayman, 24 Dec

- Added extra functionality to powerups
    • Added collision detection through pupCollision in WorldContactListener
    • Added effect() implementation in SpeedBoost
- Edited Character class
    • Added variables for controlling speed operations
    • Added setspeedcap that changes maxspeed (SpeedBoostPup)
    • Added resetSpeedCap to reset to DEFAULT_SPEED

Ayman, 26 Dec

- Added
    • Collision between bullets and character

- Modified
    • Bullets:
        - added isContacted indicator
        - added force attribute to be modified according to weapon
        - added Fixture and shape for collision detection
        - modified update function to update physicsbody too,
            added remove condition when bullet contacts character

    • Weapon:
        - added force attribute to be passed to bullet constructor

    • WorldContactListener
        - added Bullet-Character collision detection


Yahia, 26 Dec
Added weapon to second character in 2 player mode
