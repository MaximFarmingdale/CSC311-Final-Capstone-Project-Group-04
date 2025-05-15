## Introduction 
This is the final capstone project for group 4 of CSC311 (Advanced Programming) at Farmingdale University, which is a pure Java full-stack implementation of a typing race game. It is a desktop implementation of play.typeracer.com, which has been used by millions of users and lets users race other users online by allowing users to host or join a lobby, after which they can race to see who can type a string of text faster. Type Racer allows you to join a random lobby or make a custom one, which you can send the link to other people for them to join. Our implementation is different - instead, there is a screen that shows you lobbies that are currently hosted and their current status, since the traffic will be much lower than our inspiration. Our implementation also uses a locally hosted Eureka client discovery server, which means that it will only work when that server is running.

## Connections to CSC311 
There are 8 units in CSC311, and this project uses concepts from the first 7, including <p>
1: Version Control <p>
2: GUI and JavaFX <p>
3: Collections, Generics, and Documentations <p>
4: Functional Programming, Streams and Databases <p>
5: RegEx <p>
6: Concurrency  <p>
7: Network Programming <p>
There is a particular skew toward Networking and Concurrency as the main showcase of this project is client discovery, as well as making use of Concurrency to host a lobby with multiple Users. JavaFX is also another important element as the entire UI utilizes it.

## Technologies
Java - main language used <p>
JavaFX - for GUI elements<p>
Spring Boot - For autoconfiguration and general ease of use.<p>
Spring Cloud - Client Eureka and Server Eureka for client discovery <p>
Java-Faker - for generating race text.<p>
Azure Database for MySQL <p>

## Setup 
1. Clone this [ServerEureka repository](https://github.com/MaximFarmingdale/ServerEureka)
2. [Port forward](https://learn.g2.com/port-forwarding) the ports: 8761 and 12345 on your router (Only forward 12345 if you are planning on hosting and 8761 if you are planning on running the server on your network! People are not doing those things do not need to port foward)
3. Clone the current repository 
4. Change the defaultZone URL in application-client.yml in the main project and in ServerEureka to match the IP of where you are running the Eureka Server. (Set the one in the main project to be your public IP and the one in the server to be your private one.)
5. Run the ServerEureka project (The game will only work if this is running)
6. Run the main project, and then get a friend to race. 

# Overlook
## Splash Screen
The splash screen serves as a log in screen. The user is prompted to log in via their username and password. If it is their first time entering the app, you will need to sign up, which can be done by clicking the sign up button. This prompts you to create your account with a unique username and password. After this, it will store the user info, sign you in, and direct you to the Main Menu screen.
![Login Menu](https://github.com/MaximFarmingdale/CSC311-Final-Capstone-Project-Group-04/blob/master/src/main/resources/splash.png "Login Menu")

## Main Menu
After signing in, the user is prompted to play or quit, or configure settings, and after the user clicks the play button, they are prompted to either host a lobby, join a lobby, or enter the solo mode.
![Main Menu](https://github.com/MaximFarmingdale/CSC311-Final-Capstone-Project-Group-04/blob/master/src/main/resources/mainmenu.png "Main Menu")

## Solo (Practice) Mode
Solo mode (currently only mode working) is a practice room. It simulates how you would be moving during a real game in the multiplayer modes. There is a list of predetermined words, shuffled, so you can practice honing your typing speed and skills. The simulation will end after you win by typing 10 correct words, or by running through all 20 words on the list and getting at most only 9 words correct.
![Practice Menu](https://github.com/MaximFarmingdale/CSC311-Final-Capstone-Project-Group-04/blob/master/src/main/resources/solomode.png "Solo")

## Waiting Room
If the user clicks join, they are sent to a waiting room where Eureka Client fetches a list of registered applications, which in this case are people hosting lobbies, and it fetches lobby information from metadata. It uses this lobby data to populate GUI elements. This list is refreshed every second and updates the current lobby information as it changes. Once a user clicks on a lobby, they are sent to the lobby to race.

## ClientRaceView
Once the user clicks on a lobby, they can now race against other users once the host starts the game. They can chat to their opponents using the chat on the bottom right side of the application, and they can also see a countdown to the game starting as well as join messages. Before the game starts, they can only type in the chat; the textfield that is used to race will not let premature typing before the game starts. After the game starts, the prompt text is updated to be Java Faker-generated text, and a countdown starts in chat. Then the user can start typing in the textfield that clears itself every time a word is typed after a space, except for the final word, which is cleared on the last letter. The race prompt text is updated to reflect current progress through the changing of text color on the completion of a word. After someone wins the race, a message announcing the win is sent in the chat.

## HostRaceView
The host view to the race is very similar, the only differences are that the host was taken straight to the screen from the main menu, and that the host can control when the game starts. You can also choose a source from a dropdown from which the racetext is taken from.
![Host Menu](https://github.com/MaximFarmingdale/CSC311-Final-Capstone-Project-Group-04/blob/master/src/main/resources/hostscreen.png "Host Menu")

## DataBase
Stores new user information such as username and password, and checks if users signing in have the right password and username. Passwords are hashed before being added.
## Reflections 
This project was a lot of fun to make, although many elements were quite tough to implement, like Eureka Client Discovery since it is not supported in new versions of Spring Cloud and the networking, as it required two devices to debug and needed port forwarding to host or run the Eureka Server. If I had to do it again, I would change or include many elements - such as adding a prompt for the user to host if they are in the waiting room and no one is hosting and a drop-down to choose what the topic of the prompt text is. In general, Java Faker is not great for the specific purpose of generating text for a race, and I would recommend people who are trying to do something similar to use something else, like get prompt text from [Project Gutenberg](https://www.gutenberg.org/), or another source. I would also have used something other than Eureka for client discovery, like a [Redis database](https://redis.io/), which would work much better and in general, be much more eloquent. I would have also liked to have had an animation for race progress as that would be a great indicator, and would add a lot more tension to the game. It would have also been better to use something other than raw TCP sockets for communication, such as [Rsockets](https://rsocket.io/). All in all, I am very happy with this project, especially because we were relatively new to Java Spring, concurrency, JavaFX, and Networking, and this project is heavily dependent on those topics, using them quite effectively. It was a great learning experience and we look forward to making similar projects like this in the future.

[Mid Point Slides](https://docs.google.com/presentation/d/1LtVscQ258Jy4mNOMbNNi2wt_KSN-9shdBOoK-nbkl9o/edit?usp=sharing) <p>
[Final Slides](https://docs.google.com/presentation/d/1-MvwyREiJPGI3uQ2MFU7JcivcuNpyygnb_nAN-OpJ6o/edit?usp=sharing) <p>
[Solo Demo](https://www.youtube.com/watch?v=57nfFke-G5E) <p>
[Early Multiplayer Demo](https://www.youtube.com/watch?v=Bbk1AV47-Z4) <p>
[Later Multiplayer Demo](https://youtu.be/Hc6HTVyDgss)
