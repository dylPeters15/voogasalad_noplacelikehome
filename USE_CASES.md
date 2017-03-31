**Use Cases**

**Names:** 

* Dylan Peters (dlp22) 

* Kris Elbert (ke60)

* Faith Rodriguez (far10)

* Stone Mathers (smm117)

* Tavo Loaiza (eol) 

* Alexander Zapata (az73) 

* Noah Pritt (ncp14)

* Timmy Huang (th174)

* Sam Schwaller (scs51)

* Andreas Santos (ajs118)

**Everyone should do 4**.

**Use cases**: In another file, USE_CASES.md, write at least 40 **[use case**s](http://www.cs.duke.edu/courses/compsci308/spring17/readings/cockburn_use_cases.pdf) for your project that describe specific features you expect to complete. Focus on those features you plan to complete during the first implementation sprint, the next two weeks, (i.e., the project's core functionality, what must be written first to support the project's later features). Note, they can be similar to those given in the assignment specification, but should include more details based on your project's or genre's features and goals.

1.  Dylan - User starts the editor and creates a new type of unit in editor

    1. Game starts. The controller loads the model and view and connects them.

        1. Controller’s start() method implementation.

    2. The game prompts the user to load a pre-made game type or create a new game type.

        2. The Front End’s Selection class sets up a window that prompts the user with three Buttons: "Play Existing Game," “Edit Existing Game,” and “Create New Game.”

    3. The user selects to edit a pre-made game type.

        3. The event handler attached to the "Edit Existing Game" button is invoked, and the implementation of the event handler calls the back end’s editGame() method.

    4. The editor makes copies of the pre-made xml files and loads them.

        4. The back end’s editGame() method loops through each file in the game folder and copies them into a new folder.

    5. The user clicks on the "New Unit" button.

        5. The event handler attached to the "New Unit" button is invoked, and the implementation of the event handler constructs a new Unit object. The following steps simply set the unit’s properties using setters.

    6. The front end loads a prompt screen that allows the user to specify a new unit.

        6. This occurs in the front end’s GameEditing class.

    7. The user uploads an image file to represent the unit and specifies the unit name.

        7. This data is saved to the XML files of each of the unit classes using XStream.

    8. The user specifies the number of movement points that the new unit takes to move across each type of terrain. (Optional because these should auto-populate with default values.)

        8. This data is saved to the XML files of each of the unit classes using XStream.

    9. The user specifies the number of movement points that the new unit has.

        9. This is done with the Unit’s setMaxMovementPoints method.

    10. The user specifies which attacks the unit will have.

        10. Uses unit.setAttacks(Collection<Attack> attacks)

    11. The user specifies how much HP the unit will have.

        11. This is done with the Unit’s setMaxHP method.

    12. The user clicks the "Save" button.

        12. The Save button’s event handler is invoked, which calls the backend’s newUnit method.

    13. The back end writes the unit data to an xml file.

        13. The back end uses XStream to save the unit to the folder it is working in.

2.  Dylan - User starts the editor and creates a new type of terrain in editor

    14. Game starts. The controller loads the model and view and connects them.

        14. Controller’s start() method implementation.

    15. The game prompts the user to load a pre-made game type or create a new game type.

        15. The Front End’s Selection class sets up a window that prompts the user with three Buttons: "Play Existing Game," “Edit Existing Game,” and “Create New Game.”

    16. The user selects to edit a pre-made game type.

        16. The event handler attached to the "Edit Existing Game" button is invoked, and the implementation of the event handler calls the back end’s editGame() method.

    17. The editor makes copies of the pre-made xml files and loads them.

        17. The back end’s editGame() method loops through each file in the game folder and copies them into a new folder.

    18. The user clicks on the "New Terrain" button.

        18. The event handler attached to the "New Terrain" button is invoked, and the implementation of the event handler constructs a new Terrain object. The following steps simply set the terrain’s properties using setters.

    19. The front end loads a prompt screen that allows the user to specify a new terrain type.

        19. This occurs in the front end’s GameEditing class.

    20. The user uploads one or more images that can be used to represent the terrain on screen. The user specifies a terrain name.

    21. The user specifies the number of movement points that each type of unit will take to move across the new terrain type. (Optional because these should auto-populate with default values.)

        20. This is done using the setter methods from the Terrain class.

    22. The user selects the "Save" button.

        21. The Save button’s event handler is invoked, which calls the backend’s newTerrain method.

    23. The back end writes the new terrain data to an xml file.

        22. The back end uses XStream to save the unit to the folder it is working in.

3.  Dylan - User moves an actor from one cell to an adjacent cell in a Civilization-type game

    24. During the game, the user clicks on a unit.

    25. The front end prompts to see if the user wants to move the unit or use one of its abilities.

    26. User selects to move the unit.

    27. User selects cell to move the unit to.

    28. Back end checks if the unit can move to the selected cell.

    29. If the move is valid, the back end moves the unit to the selected cell.

    30. The front end is notified of the change to the model, and updates the view so the user sees the result of moving the unit.

4.  Dylan - User drops a bomb in a Battleship-like game, hitting one of the enemy’s ships.

    31. During the user’s turn, the front end displays buttons to the user based on what the user can do.

    32. Each player has one "Bomber" unit that can attack anywhere. The user selects the bomber unit.

    33. The front end prompts to see if the user wants to move the unit or use one of its abilities.

    34. The user selects to use an ability.

    35. The front end prompts the user with available abilities.

    36. The user selects the "Bomb" ability.

    37. The user selects the cell he or she wants to bomb.

    38. The back end checks if the unit can use the ability on the selected cell.

    39. If the ability is valid, the back end executes the ability on that cell.

    40. If the cell has a ship in it, the back end marks that ship as hit.

    41. If the ship is hit the requisite number of times, the back end marks the ship as sunk.

    42. The front end is notified of the changes and updates itself so the user can see the result of his or her actions.

5.  Tavo - User saves a game to an xml file

    43. Prompt the user for the filename

    44. Iterate through all the fields in the model and call `toXML()`

    45. Pass them as parameters to the XStream XML doc

    46. Write the XML doc to a file

6.  Tavo - User loads a game from an xml file

    47. Generate a file-open dialogue to let the user select the xml file

    48. Parse through the XML file and create an instantiated Model

    49. Populate all the fields of model and the 

    50. Set the current Model to the instantiated model

7. Tavo - User attempts to load a broken XML file

    51. Attempt to parse through the XML file

    52. When the XStream throws an error, catch it and re-throw it with a custom exception class (that includes a custom error message)

    53. Throw exception upward until front-end catches the exception.

    54. Show an error dialogue that insults the user and bashes his so called intelligence for opening up a bad XML file

8.  Tavo - User wishes to make a new game from scratch

    55. User chooses "New game editor" on the GUI

    56. Wizard pops up that prompts user for cell size, cell shape, game type (with default value already filled in). 

    57. Editor passes all these values to the back-end

    58. Initializes editing environment

9.  Alex - In the editor, a user wants to manipulate the attributes of a unit:

    59. The user writes Groovy code to pass the new attributes of the unit to the parser.

    60. Once the code is parsed, the new active or passive ability is passed to the constructor of a parallel unit whose new state will be saved through XML. 

    61. After this his been done, the user can then take that unit, with newly distinguished abilities, and continue creating their implementation of the game with that new unit.

10.  Alex - In the middle of a game, the user wants to move a unit with a previously specified movement pattern:

    62. The user, on the Game’s interface, will select a unit.

    63. The front end passes information to the back-end through the controller to tell which unit is moving. This is where the front-end will ask for the possible movement locations.

    64. The back-end will then determine, by the current location of the unit, which cells the unit can move to. 

    65. The front-end receives some kind of collection with the possible cells, and the front-end will display this somehow.

11.  Alex - The user wants to manipulate multiple units at the same time:

    66. The user will select the units that they will attempt to be moved- through the Game’s interface. (take note that they must be of the same type and in the same cell)

    67. The front-end will then threat these units as a group and then treat this group of units as a unit for the back-end to manipulate the location of at the discretion of the user.

12.  Alex - The user wants to delete or loses a unit in game:

    68. The user selects the unit it wants to delete on the Game’s interface, or the unit gets removed in game.

    69. The front-end then uses the controller to remove that unit globally from any collections in Cell and Player. 

13. Alex - The user, in the process of creating a game, wants to set specific winning/losing/tieing conditions for their game:
	* The user, in the editor, uses Groovy to make a ResultQuadPredicate that will be passed into the back-end  
	through the controller. 
	* Once this Predicate is passed into the back-end, it will be added to the DieselEngine's list of  
	winningConditions which will hold the QuadPredicate.

14. Alex - The user, in the process of a creating a game, wants to add events that are triggered by turns:
	* The user interacts with the editor and writes Groovy code that will create a BiConsumer that will effect  
	  the units. 
	* Along with the BiConsumer, the user should pass an appropriate way of telling the  
	back-end when the event triggers. The projected way to do this is to send a TurnTrigger Enum to through  
	the code-editor.

15.  Timmy - Unit A attacks Unit B

    70. User picks an attack from Unit A’s list of units.

    71. Attack affects Unit B 0 or more times.

    72. Each time

        23. Get attack base damage

        24. Attack effects are applied to base damage

        25. Attacking unit effects applied to base damage

        26. Defending unit effects applied to base damage

        27. Unit B loses HP equivalent to damage dealt

16.  Timmy - User defines a new Attack, and adds it to a unit

    73. User specifies name, description, imgPath, damage, numHits, as well as any additional passive abilities

        28. An attack pattern is defined by allowing the user to pick spaces on the front end

    74. User adds attack to collection and passes attack to a unit’s constructor

17.  Timmy - User moves a custom unit over Terrain which it doesn't have defined movecosts for.
    
    * UnitInstance::moveTo is called on the target cell
    
    * Unit looks up its moveCost for that cell's Terrain
    
    * A matching value for the Terrain is not found, so it defaults to the default move cost of the Terrain
    
    * The default moveCost is deducted from the Unit's movePoints

18.  Timmy - Unit wants to save load a full set of predefined units that work together

    * User creates a set of different units using unit editor
    
    * User stores those units in a Faction
    
    * User saves the Faction to XML

19.  Noah - Unit A is told to attack Unit B which is not adjacent to it.

    75. In the frontend, a user somehow instructs a unit to attack a unit some distance away from it. This could be a queen attacking down a board in chess, for example, or an archer unit trying to shoot at a unit some distance away.

    76. A method in the getInteractInfo class collects information on the situation. 

        29. How many cells away is the cell to attack?

        30. What terrain is involved, both between the cells and in the cell that each unit is a part of?

        31. Which units are between the two units? In chess, if any units are in between the attack cannot occur. In the second example, however, this could still work; archers can shoot over other units.

    77. The list of current gamerules will be checked with this information, and will decide whether the units can fight, as well as what the result will be.

20.  Noah - User creates a new gamerule that says that when a user has no units left, they lose the game.

    78. The user enters in a new rule via Groovy Code

    79. The BoardInitializer class decides that a new gamerule is being applied, as well as what the new rule is. This is accomplished with help from the Parser and Translator classes, which is a two-staged process.

    80. The new gamerule is created via the GameRule class. Its type will be endgame.

    81. The new gamerule is added to a list of gamerules that can be easily accessed throughout the lifespan of the program

    82. Because the new gamerule is of type endgame, at the end of every turn it will be checked. If no units are left for one player, the game will end and that player will lose.

21.  Noah - User creates a new gamerule that says that, when a unit is destroyed it spawns units in each of the adjacent cells

    83. The user enters in a new rule via Groovy code

    84. The BoardInitializer class decides the a new gamerule is being applied, as well as what the new rule is. This is accomplished via the Parser and Translator classes. 

    85. The new gamerule is created via the GameRule class. Its type will be general.

    86. The new gamerule is added to a list of gamerules that can be easily accessed throughout the lifespan of the program

22.  Noah -  User saves board initialization to XML

    87. A button is pressed in the frontend called "save game"

    88. Groovy code will be passed to the frontend from the backend via the controller to represent this action.

    89. The BoarderInitializer class will work with the  Parser and Translator classes to determine that the following values should be saved to xml: the gamerules, the grid type/dimension, the current units, and the terrain.

    90. Another class will handle saving the information to xml. 

23.  Faith - User is on the home screen and presses "Create New Game"

    91. User is directed to the authoring environment screen, where a game can be created.

    92. Once completed, the game settings are saved to an XML file that can be read in to play the game.

24.  Faith - User tries to move a piece to an illegal spot

    93. The user’s movement selection will be passed to backend

    94. Backend deals with associated error and passes message to frontend

    95. GamePlayer displays an error message to the user

25. Faith -  The user still has moves left but wants to end their turn

    96. The user presses an end turn button

    97. A message pops up asking the user if they really want to end their turn.

    98. Game control switches to next player

26.  Faith - The user wants is in the game design screen and wants to add a piece to the grid.

    99. The user selects a piece from a toolbar on the left.

    100. The user drags the piece over to the desired part of the grid and drops it.

27.  Kris - User modifies the movement capabilities of an Unit

    101. The Selection Screen Loads and the user chooses to Edit an existing game.

    102. The user chooses a file to modify and loads that game into the Game Development Environment.

    103.  The user selects a unit in the Game Development Viewport and the details box pops up on the side, or they can right click the unit in the viewport.

    104. The user clicks on the Edit Movement button.

    105. A small grid is highlighted around the Unit in the Viewport that the user can select and highlight parts of that the unit will then be allowed to move to.

    106. The user can click a glowing part to deselect it if they do not want the unit to be able to move there.

    107. The user can hit the Save button or Quit to set or not set their movement changes and exit out of the small grid view and back into the general view.

    108. The new movement characteristics are saved relative to the start cell’s coordinates.

    109. The user can then re save the game and include those new characteristics for that unit.

28.  Kris - User makes a Unit able to move infinitely far in one direction (say, diagonal or straight ahead)

    110. The user goes to modify the movement capabilities of the Unit in the Development Environment

    111. Instead of selecting a single cell in the small grid view, the user holds down Shift while selecting a cell and then a ray of selected cells will extend from the center Unit.

29. Kris - The user wants to know how to edit in the Development Environment and why the bees are declining at an alarming rate

    112. At the top of the menu bar, there is a button for Help.

    113. Once clicked, an HTML document pops up with basic rules and capabilities of Terrains and Units along with what the user can customize.

    114. The user can close this window or scroll through it for information on placing and editing options. The user can also discourage the use of neonics as this pesticide is toxic to bees.

30.  Kris - The User creates a piece that spans multiple cells (think ships in battleship)

    115. In the custom/add unit window of the Game Development Environment, the user sets how many cells their piece should span graphically, with the default being 1 by 1 and only the center of a little grid highlighted. They can select more cells to be spanned by this piece by selecting more cells in the small grid display. The center cell is distinguished and, by default, selected.

    116. The user uploads an image and can rotate it so that it will display around the center cell. The center cell will also determine if the image is layered over or under other images.

    117. The user saves this and the sprite is attributed to the Unit Group.

31.  Sam - User tries to change appearance of an object- 

    118. Allows user to choose from available appearances or choose a new image file

    119. User clicks and drags image to resize

32.  Sam - User tries to select image that is not accepted for object

    120. Popup tells user to select a different appearance

    121. Return to design screen

33.  Sam - User wants to use game that is bigger than the screen

    122. Scroll using two finger touch or mouse wheel or scroll bar or arrow keys

34.  Sam - copy and paste terrain or other game features

    123. User right clicks object

    124. Select copy

    125. Right click on other position

    126. Select paste 

35.  Sam - User chooses option to animate object

    127. Select object and observe object properties

    128. Press animate button to animate

36.  Faith - User wants to save their progress and exit out of the game.

    129. Uses presses Save button in options panel 

    130. The current settings are saved within the game’s XML

    131. The next time the user selects that game, the game will appear with the progress set to the last time they saved

37.  Kris - Night falls and some Units become stronger

    132. After a certain number of turns, the backend recognizes that the play environment should be updated from day to night. The frontend displays different colors or adds a semi-transparent black filter on top of the other sprites.

    133. Now that the play environment has been modified, a multiplier or modifier is in effect for how units attack and defend. Otherwise, gameplay is the same.

38.   Sam - save game development (to XML)

    134. User clicks "save" or “save as” button

        32. Save- Pop up with "saved as… “

        33. Save as- fileBrowser pops up to allow user to name file

39.  User’s score passes the game’s high score

    135. The high score for the game will be automatically updated.

    136. Upon saving, this change will be reflected in the XML file

40. The user wants to save a modified version of an already existing game.

    137. The user presses "saveas" to create a modified version 

41.  The user wants to edit a game that they are currently playing

    138. Press the "edit" button.

    139. Current game progress will be saved

    140. They will be taken to the game development screen

    141. Player makes changes and clicks Play

    142. Play of game resumes from saved position

42. Andreas- user wants to define an ‘end case’ for their game

    143. When user creates game, they will choose the ‘type’ of end case for their game (via Game Type). 

    144. When in the authoring environment, the user could select a "set game end conditions" which (depending on the game type) could either have a groovy editor or ui editor to specify end condition.

    145. (assume game type was certain pattern ends game (e.g. 3 in a row tic tac toe))

        34. Ui version, grid comes up and user can click/drag and drop created pieces to demonstrate end case set up

        35. Groovy version, user can program that ‘three in a row’ is the end case

    146. These preferences are saved in the engine, and are checked at the end of every turn to determine if game is ‘ended’

43. Andreas- user wants to define behavior of a unit in a cell when another unit moves into a ‘specified range’

    147. User can specify that a piece has a ‘passive movement’

    148. User sets the cases for this passive movement/action

        36. The user can set the behavior of the piece when the passive condition is met, the same way they would set the active behavior of the piece

    149. At each turn, game checks all pieces to see if passive action condition is met, and if so, moves the piece

44. Andreas- unit wants to know what other units are in surrounding ‘neighbor’ locations

    150. Unit gets its ‘coordinate tuple’, which gets the ‘neighbor coordinate tuples’ which are mapped to neighbor cells

45. Andreas- unit attempts to change terrain on at its current location.

    151. Unit gets its cell to call changeTerrain method

46. Faith - User creates and drags a Game Piece into its position on the grid

    152. When user clicks on a game piece a window

**Example code**: Pick at least 5 use cases and provide example "implementations" of them, using only methods from your code interfaces. Note, these should be separate, obviously named, Java code files in a separate package, usecases, that compile and contain extensive comments explaining how you expect your different APIs to collaborate. Note, you can implement each feature either in a single class to show how the steps are connected "procedurally" or in separate classes that implement the necessary interfaces to show how the steps are "distributed" across the objects. In either case, you will likely need to create simple "**[mocked u**p](http://martinfowler.com/articles/mocksArentStubs.html)" objects that implement your interfaces so you have something concrete to create, using new, and call methods on.

1. User customizes a game piece - DevMenuBar creates "New Unit" Button that, when clicked, brings up a new screen to create the new Sprite. User inputs all settings (Image, name, HP, attack, etc.), clicks “Finish,” and a new Sprite is initialized with these attributes. This Sprite is placed in a ListItem, which is placed in the appropriate CollapsibleList. (Classes created in *frontend* package: *DevMenuBar*, *NewUnitScreen*, *UnitListItem*, *CollapsibleList*, *SpriteMenu*)

2. User drags a Game Piece into its position on the grid - GamePiece (click triggers response) addPiece (when drag has completed) -> GridDisplay - placeOnGrid -> Sprite - placeCell

3. User uses Unit A’s attack on Unit B.

    1. User clicks on Unit A.

        1. The unit’s onClick() method is invoked

    2. The onClick() method causes the user to be prompted with options of what to do. User selects Attack.

        2. The attack’s onClick() method is invoked.

    3. The onClick() method causes the user to be prompted with a list of all units that can be attacked. The user selects Unit B from the list.

        3. The unit’s onClick() method is invoked.

    4. The onClick() method invokes Unit A’s useActiveAbility(ActiveAbility<GameObject> activeAbility, GameObject target) method.

        4. The useActiveAbility method invokes the ActiveAbility class’s affect(Unit user, T target, GameState game) method.

    5. The affect method modifies each unit’s properties appropriately. (If it is a basic attack, it will simply decrease Unit B’s HP)

4.  Create new game board

5.  User’s score passes the game’s high score

    6. An action occurs, such as an attack or a movement.

    7. After the action occurs, there is a new GameState. Normally, this is sent to the frontend via CommunicationController. Before this occurs, however, all of the GameRules of type "endgame" are checked to see if the end of the game has been reached. This occurs in the isGameOver method of the class EndGame, which returns a boolean.

    8. In this case, isGameOver returns true. So, the GameState is not returned to the frontend. The method whoWon in the class EndGame determines the winner of the game, and the winning player corresponding to a Player object, is sent to the frontend.

    9. The frontend creates a message to display based on the Player method getName depicting who won.  

