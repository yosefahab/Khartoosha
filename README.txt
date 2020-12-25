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