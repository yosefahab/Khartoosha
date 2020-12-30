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


Ayman, 27 Dec

- Added
    • Armor powerup with full functionality
    • Added isArmored bool to Character
    • Added charNum attribute to Character

- Modified
   • Second player now spawns on the other side
   
   
   
Bassel, 30 Dec
- moved handling character movement input from playscreen to character class
- limited character jumps
- disabled characterXcharacter collision 
- adjusted distance between tiles to make physics bodies movement smoother
 *side note: in contact listeners, if a DynamicBody X StaticBody contact happens, Fixture A is always the static body


   
