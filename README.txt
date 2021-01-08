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

 Ayman, 31 Dec
    - Added:
        • PowerUps:
            • 2 New power ups ( Extralife, Refill Ammo)
            • Powerups now skip random number of platforms when falling
            • powerups now reset position if they fall into oblivion
            • Increased Max powerups to 6

        • Camera Debug Mode (all following keys are numpad ones):
            - Use 0 to enable debug/free roam mood
            - (4, 8, 6, 2) to move (left, up, right, down) respectively
            - Use (7, 9) to zoom out, in respectively
            - Use 0 again to reset cam back onto characters

        • Gameplay
            - Added lives to players and life mechanics
                (falling decreases life, losing 5 lives makes u lose etc...)
            - Added Guns upgrade and degrade on kill/death
            - Check TODO to find where resetting Game will be done
            - Press X for a brief game state printed in the console
            - (left texture editing debug options that can be found in ExtraLife class,
                might be useful)

        • Character:
            • Added Lives
            • Added currentWeapon as an attribute inside the character class
            • Added friction value to physics body to prevent extra sliding
            • Added kill timer logic (opponent doesn't get weapon upgrades on self kills)
                (more info in startHitTimer docstring)
            • Added weapons handling mechanics
            • Characters now spawn in random place above the platforms  after falling

        ••• Weapons:
            • 3 New Weapons Available (Pistol, MachineGun, Sniper)
            • Added weapons definitions (constants controlling weapons attributes)
            • Weapons have FIRE RATES now (number of bullets fired/sec when holding the key)
            • minor graphic change where texture change to Rifle when weapon is MG or Sniper
            • added keyPressTimer for controlling fire rates
            • Added refill ammo function

    - Modified:
        • WorldContactListener was modified to support the new features


Ayman, 5 Jan
    - Slight modifications to weapons properties ( increased force and fire rate)
    - fixed a bug where bullets stuck in platforms

Yahia, 8 Jan
    -Removed switching weapon mechanics from Character to Weapon
    -Weapon is updated when dying/killing the opponent
    -If the Player has the MAX_LEVEL Weapon (Sniper) He gets an ammo refill when killing the opponent
    -If he has the LOWEST_LEVEL Weapon (Pistol) He gets a refill when dying
    -Weapons now are easily modified in case wanting to add more weapons
   
