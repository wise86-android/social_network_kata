# Social Networking Kata
Credits to [Sandro Mancuso](https://twitter.com/sandromancuso) and [Samir Talwar](https://twitter.com/SamirTalwar) for the [original idea](http://monospacedmonologues.com/post/49250842364/the-social-networking-kata).

Implement a console-based social networking application (similar to Twitter) satisfying the scenarios below.

### Scenarios

**Posting**: Alice can publish messages to a personal timeline

> \> Alice -> I love the weather today  
> \> Bob -> Damn! We lost!  
> \> Bob -> Good game though.  

**Reading**: Bob can view Alice’s timeline

> \> Alice  
> \> I love the weather today (5 minutes ago)  
> \> Bob  
> \> Good game though. (1 minute ago)  
> \> Damn! We lost! (2 minutes ago)  

**Following**: Charlie can subscribe to Alice’s and Bob’s timelines, and view an aggregated list of all subscriptions

> \> Charlie -> I'm in New York today! Anyone wants to have a coffee?  
> \> Charlie follows Alice  
> \> Charlie wall  
> \> Charlie - I'm in New York today! Anyone wants to have a coffee? (2 seconds ago)  
> \> Alice - I love the weather today (5 minutes ago)  

> \> Charlie follows Bob  
> \> Charlie wall  
> \> Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)  
> \> Bob - Good game though. (1 minutes ago)  
> \> Bob - Damn! We lost! (2 minute ago)  
> \> Alice - I love the weather today (5 minutes ago)  

### General requirements

- Application must use the console for input and output;
- User submits commands to the application:
    - posting: \<user name> -> \<message>
    - reading: \<user name>
    - following: \<user name> follows \<another user>
    - wall: \<user name> wall
- Don't worry about handling any exceptions or invalid commands. Assume that the user will always type the correct commands. Just focus on the sunny day scenarios.
- "posting:", "reading:", "following:" and "wall:" are not part of the command. All commands start with the user name.
- Use whatever language and frameworks you want. Use something that you know well.
- Provide a README with instructions on how to compile and run the application.
- You must release your work with an OSI-approved open source license of your choice.

**IMPORTANT:**  Implement the requirements focusing on **writing the best code** you can produce.

**CODE SUBMISSION:** Add the code to your own Github account and send us the link.

### Run the code

to run the tests:

``` ./gradlew test```

to run the program:

``` ./gradlew run```

to exit press Ctrl+d