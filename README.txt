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
   


Bassel, 8 Jan
    - Moved character render & update logic from PlayScreen to Character 
    - Moved powerups render & update logic from PlayScreen to PowerupsHandler 
    - Removed old useless commented code 
    - Fixed a bug where character position and fire key were determined based on texture number instead of character number

Ayman, 17, Jan
    - Added:
        • Basic AI can be enabled/disabled from character constructor
        • New Powerup -> UpgradeWeapon
    - Modified:
        • Character:
            - Added attributes shape_width, shape_height that control the physics body shape
                (Used by AI to check whether the character in shooting range or not)
            - Added Ai related code
            - Moved the Allowed_Jumps check to the Jump() function
            - Changed Access Modifiers of movement functions to Public (Used by AI)
            - Speedboost now doesn't stack more than twice
        • Powerups:
            - General Cleanup: Moved redundant code from child classes to parent class
            - Armor now decreases shot force by a certain factor instead of disabling it completely
            - Fixed a bug where RefillAmmo had no effect
        • Weapon: added MAX_TYPE attribute to determine the number of current weapons
            (used by UpgradeWeapon Powerup)




Ayman, 20 Jan
    - Added:
        • New AI functionality (can navigate maps and chase player properly now)
        • **Experimental Bomb class, use F for player1 and G for player2
    - Modified:
        • Character:
            - Modified constructor to get spawn location
            - Character now spawns in a location from the map (can be edited in Tiled) instead
                of hard coded values
            - Added code for grenades
        • Map:
            - Added new data to the Tiled map (navigation points for ai - player spawn locations -
                whether platforms can be dropped from or not)
            - Added code to retrieve added data from map to the Map class
            - Modified user data for the platforms (now is a pair of physics body and map object)
        • Weapons: adjusted weapons forces



Bassel, 24 Jan
- Modified Bullet and bomb to support bullets spreading in all directions on explostion 
- Prevented bomb from memory leaking 


Yahia, 24 Jan
-Modified weapons texture size
-Added Texture to Bullet's constructor (To be able to specify a bullet texture for each weapon)
-Fixed a bug where the player could shoot while the weapon was in air


Bassel, 27 Jan
- New map, map 3, still in progress, AI is not configured with it yet, take a quick look at the map class
- Press and hold Q for the tile flipping thing, adding it permenatnly will be discussed
- Modified weapon and bullet to support adding light to bullets in map 3
- moved removing bullets logic in weapon to a separate method
- Reduced the opacity and density of moving background in map 2
/* check out box2dlights, very simple and can do really cool stuff */

Youssef, 30 Jan
- modified bullet bounds to match init position, this should make initial bullet position dynamic
- replaced sprites/TextureRegions in Hud, they're now implemented using Textures and drawn using game Batch
- fixed bug where game crashed due to index out of bounds in lives array in Hud
- some variables renamed
- *physics bodies, weapons and characters textures' positions to be discussed*
- added endGame() to be used to implement game ending

Shehab, 1 Feb
- menu is completely redone (even though it looks the same)
- map choice menu is ready
- menu is built on scene2D, some playlists to it:
        • https://www.youtube.com/watch?v=EJwXzmUQChg&list=PLXY8okVWvwZ0JOwHiH1TntAdq-UDPnC2L  (main menu videos)
        • https://www.youtube.com/watch?v=Huifd-C2KrI&list=PLS9MbmO_ssyCZ9Tjfay2tOQoaOVoG59Iy (scene2D videos)
- all old menu classes and assets still exist till menu is completely finalized
- the new settings menu screen is yet to be implemented
- some minor features and effects will be added
- in the character choice menu the characters are a bit pixelated, this will be solved later

Yahia, 2 Feb
-Added bullets counter to hud
-Adjusted bullets/weapon position to fit the new size

youssef, 2 Feb
- modified hud bullets' positions
- added end game state, using gameOver boolean in PlayScreen,
dispose methods should be worked on to be able to transition back to main menu screen,
and a back button should be added on game ending.
- check TODOS as some things need to be looked into.

Shehab, 3 Feb
- Bug Fixes:
    • menu sounds no longer appear in PlayScreen.
    • characters are no longer pixelated in character choice menu screen.
- The SettingsMenuScreen is completely redone with scene2D.
- General bug fixed and menu stuff modified.