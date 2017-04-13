# Part 1
## What about your API/design is intended to be flexible?

Every kind of object is a descendant of VoogaObject and follows an hierarchy full of methods that could be needed. If someone  
wants to add any kind of Object, Instance, Ability, Template, Unit, or anything they can imagine for a game, they can just  
use our already existing hierarchy to implement their idea very easily. Also, our program uses consumers and predicates   
heavily, so someone can make their own acceptable or testable methods with lambdas very easily.

## How is your API/design encapsulating your implementation decisions?

Because we use a complex system of consumers, methods only need to be defined exactly where they are used. This means that  
there are very little fully implemented methods in peripheral code. Also, we use complex hierarchies of interfaces and  
abstract classes so that any method that might be necessary is already silently implemented within the ancestry. Lastly,  
because of the Model-View-Controller work-flow that we are implementing (along with Observable objects and listeners passed  
from model to our server system) there are very few dependencies which leads to very few implementation details going  
from one step to the next.

## How is your part linked to other parts of the project?

I am writing the GameEngine... The point of it is that it delegates specialized listeners to the Server and changes things in  
the GameState depending on what actually needs to occur from the defined rules. The gameengine is very cool because, there are  
almost no dependencies for it. All the GameEngine needs is a gamestate to get the predicates, consumers, and quadpredicates  
to be able to send in the form of a listener to the Server. My gamestate also is just linked to the server who changes it. The  
changes to the gamestate listened to so that all the clients can be separately updated.

## What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?

I am also in charge of the GameState who will, through the   
defined predicates and bipredicates decide whether or not moves are applicable and throw errors possibly if no other class  
will do so. GameEngine will throw errors in the case that it restarts, loads, or saves incorrectly.

## Why do you think your API/design is good (also define what your measure of good is)?

Our API is actually very good. The reason I say so is because the extendability is really great. Effectively, to extend any  
of our classes is easy because of their abstraction and there are already implemented methods that make sense to have. Also,  
there is no duplicated code other than the method headers for implemented methods. This level of abstraction and hierarchical  
makes the code easy to add to, and that is a good metric for me!  

#Part 2
## What feature/design problem are you most excited to work on?

Im really excited on later working on a way to make our game into an RPG engine as well. Theoretically, all users could be  
changing things at the same time in the game depending on how the gamestate delegates turns and how the server takes requests  
from the clients.

## What feature/design problem are you most worried about working on?

Im worried about how the front-end is going to handle the requests and send the requests. There needs to be listeners on  
basically every thing that the gamestate does, so it is very important how the view updates.

## What major feature do you plan to implement this weekend?

I want to realistically implement a full game really. Our code editor is extremely proficient, so we should be able to  
implement rule systems and new objects very easily. It should not be difficult making new games at all. 

## Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?

The use cases are pretty descriptive, the only thing is that they could be adapted a little bit better for our server-client  
system now. They are also relatively good sized.

## Do you have use cases for errors that might occur?

In the case that the user does not pass a syntactically correct predicate or consumer, then the ScriptEngines will pick that  
up and directly throw an error so that that code does not get passed anywhere else.