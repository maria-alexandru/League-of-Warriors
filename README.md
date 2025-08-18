# League of Warriors

## Description

- This project implements an adventure game where the player navigates through a matrix-based world
filled with enemies, treasures, and mysterious events. The game features a modular, object-oriented
design, incorporating several software design patterns for maintainability and scalability

- The game world is represented as a dynamic n x m matrix. Each cell may contain different types of
events or elements, such as: enemies, sanctuaries, portals, empty terrain

- Players explore the world one cell at a time, with visibility limited only to the current cell they
step into

- The game includes a simple GUI built using Java Swing, offering an interactive interface for game
actions and event visualization

- Design Patterns used:
    - Singleton - ensures only one instance of the Game class exists at any given time, using lazy
initialization

    - Builder Pattern - used to instantiate different types of Information or character classes
with flexible configurations

    - Visitor Pattern - Spell class implements the Visitor interface, allowing it to apply
effects dynamically on different game entities.

    - Element Interface - implemented by Entity Class, enabling separation of game entities
from the operations performed on them

## Implementation
<p align="center">
<img width="500" alt="image" src="https://github.com/user-attachments/assets/153de955-61fa-4e94-9223-50a2274f9ad8" />
</p>

- The game begins with a login screen, where players must enter a valid email address and password
to access their account. This authentication system ensures that each player can manage multiple characters

<p align="center">
<img width="500" alt="image" src="https://github.com/user-attachments/assets/1b696181-6058-4b63-bea4-4b0b42faa27a" />
</p>

- After logging in, the player proceeds to the character selection screen, where they can choose an
existing character. Each character has unique attributes and special abilities, which evolve over time
through battles and experience

<p align="center">
<img width="500" alt="image" src="https://github.com/user-attachments/assets/8d6be663-bcda-4c88-a581-af56636dcee9" />
</p>
  
- Once a character is selected, the game starts on a generated map. The player controls the character
through an intuitive graphical interface developed with Java Swing or by pressing the arrow keys on
the keyboard for quicker navigation

- Each cell reveals its content only when entered, potentially triggering one of several event types (enemies, sanctuaries, portals, or empty land)

- While playing, the screen displays real-time information about the current character, including:
level, experience points, health and mana

<p align="center">
<img width="500" alt="image" src="https://github.com/user-attachments/assets/22f79b91-5ada-435d-b2f0-c35c50a300fe" />

<img width="500" alt="image" src="https://github.com/user-attachments/assets/92cc908c-105e-4a45-a5f7-300c2b57da27" />

<img width="500" alt="image" src="https://github.com/user-attachments/assets/a46000ec-c0b6-4485-a073-dae7c0ce1841" />

<img width="500" alt="image" src="https://github.com/user-attachments/assets/53350291-8660-4633-b7ea-4ac990308ac0" />

<img width="500" alt="image" src="https://github.com/user-attachments/assets/83b599c0-972d-4300-b932-0f427ecadca1" />
</p>




