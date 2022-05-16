Josh
17/09/2021
- Started working on application functionality

18/09/2021

- Making sure not to start multiple bash processes at once as they would make the sound overlap
- Wait for one process to finish before starting another
- Making the interface cleaner by hiding buttons and labels when they are not needed 

20/09/2021
- Having a pause after submitting the word
  - Make work better kids
  - But not adults 

21/09/2021
- Client meeting 
  - 6 minute presentation 
  - Marked individually 
  - Demo the GUI
- Higher score for first attempt 
- Have own informal order 
  - Difficulty levels?
- Decided on one prototype to move forward with
  - Merged with main branch
- Decided with no pause after submitting a word 
- 
22/09/2021
- Created new branches off the main branch 
- Worked on refactoring code to make the code more maintainable and easier to understand 
- Asked group members to check work
- Merged into main branch

23/09/2021
- Refactored code in main branch 
- Added comments
- Talks about the next steps and what features we could add for later in the project
  - Slider for speed control 
  - Display words at the end on rewards screen 

24/09/2021
- Justification for design patterns
  - Only repeating the word once when the user gets it incorrect
    - Adds less clutter
    - Already a reply button 
  - Adding “correct/incorrect” both in text and read out loud 
  - Showing hint upon request 
    - The user may want to be shown the hint earlier 
    - If they are truly stuck or just want to make sure 

28/09/2021
- Chance to get feedback on functionality
- Could perhaps ask client to play the game 
- Focus on the functionality
- Team agreement
    - Pulling out the features
- Design questions
    - Scalability 
    - Ability to keep track of modules 
    - Practice module

03/10/2021
- Started working on settings tab
- Created FXML scene builder file 
- Found it difficult to save and set the soldier value every time the tab was closed

04/10/2021
- Redid the stage without scene builder 
- Was able to save and set the slider value 

07/10/2021
- Used a listener to input macrons
- User to input “*a” for the desired macron for that letter
.
08/10/2021
- Created extra buttons and scenes for the settings tab
- Has 3 tabs in with extra information can be viewed
- Implemented checkbox 
    - To show/hide the timer

09/10/2021
- Worked on error handling
- Disabling buttons when they should not be pressed
    - When the text field is empty
    - When a bash process is running 

11/10/2021
- Discussed new features to design moving forward 
- Delegated work on the scoreboard 

12/10/2021
- Designed base functionality of the scoreboard
    - Saving scores and names into a text file which can be displayed on the leaderboard 
    - Connected the reward screen to the leaderboard 
    - Implemented it so that the scoreboard displays the results of the most recently spelt theme 
    - Added functionality to clear the leaderboard 

13/10/2021
- Fixed an error when multiple themes being selected 

14/10/2021
- Added error handling when submitting the name to the scoreboard
    - Not allowing user to submit a name with no name
    - Only allowing the user to submit their name once 

17/10/2021
- Worked on code restructuring and commentings 
  - Added comments and javadocs to classes 
  - Classes included settingsController, rewardController, leaderboard Controller, newQuizController

19/10/2021
- Worked on code restructuring and commentings 
  - Added comments and javadocs to classes 
  - Classes included bashProcess

