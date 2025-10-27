# Rubik's Cube Simulator

## Overview
This program simulates a **3×3 Rubik’s Cube** with interactive visualization and custom input options.

---

## Features

1. **Default Orientation**
   - The cube’s default position is:
     - **Orange face:** forward  
     - **White face:** down  
     - **Yellow face:** up  
   - This is how the cube engine interprets the cube’s orientation.

2. **3D Interaction**
   - You can **click and drag** to rotate the cube into any orientation, allowing you to view all sides from any angle.

3. **Algorithm Input**
   - Enter an algorithm in the text area using the following moves:
     - `rc`, `lc`, `uc`, `dc`, `fc`, `bc`  
       *(Right, Left, Upper, Down, Front, Back — all clockwise turns)*
   - Each move has an **anticlockwise** version, indicated by adding an **a** (e.g. `ra` = right anticlockwise).
   - You can **repeat moves** by prefixing them with a number:
     - `3rc` = three right clockwise turns (equivalent to `ra`)
   - Moves can be written consecutively:
     - Example: `rc lc 2ua`

4. **Custom Cube Building**
   - You can manually build your own cube.  
   - Each face is entered **left to right, top to bottom**.

5. **Copy Current Cube**
   - You can copy the current cube’s state into the “Build Cube” area — this provides an example format for creating your own cube.

6. **Solving**
   - Automatic solving is **not yet implemented**, but will be available in a future version.

---

## Running the Program

To launch the simulator from Java, run the following main class:

```bash
gui.Gui
