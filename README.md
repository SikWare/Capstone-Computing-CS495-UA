# Capstone-Computing-CS495-UA
Home Organizer App

SikWare presents:
FML: Fix My Life

Requirements Description
Adam Pluth
Ken Logan
Brandon Mottor
2/16/2017

Version
Comment
Author
Date
0.1
_init_
Adam Pluth
02/16/2017
0.2
Functional Requirements
Brandon Mottor
03/01/2017
0.3
Descriptions
Ken Logan
03/01/2017
0.4
Intro, Terminology,
Descriptions, System requirements
Adam Pluth
03/01/2017
0.5
Clean up, added Table of Contents, Appendices
Adam Pluth

03/02/2017
0.6
Activity Diagrams
Ken Logan
03/02/2017
0.7
Activity Diagrams
Brandon Mottor
03/02/2017






Table of Contents:
1. Preface											4
2. Intro											4
2.1. Overview									4
2.2. Terminology								4
2.2.1. Living Group							4
2.2.2. Karma								4
2.2.3. Head of Household						4
3. Project Description									5
3.1. Initialization								5
3.2. Start Screen								5
3.3. Users									5
3.4. Sharing / Synchronization						5
3.5. Rewards									5
3.6. Inter-Communication							6
3.7. Inventory / Ownership Log						6
3.8. Scheduling / Reminders						7
3.9. Notes List									7
4. Requirements									8
4.1. Functional									8
4.1.1. Calendar								8
4.1.2. Notes List								8
4.1.3. Shopping List							8
4.1.4. Media Inventory							8
4.1.5. Rewards								8
4.1.6. Inter-Communication						8
4.2. Non-Functional								8
4.2.1. Admin User							8
4.2.2. Synchronizer							8
4.2.3. Data Manager							9
4.2.4. Database								9
5. System Requirements								9

6. Appendix										10
6.1. A-Use Case Diagram							10
6.2. B-Class Diagram								10
6.3. C-Activity Diagrams							10
6.3.1. Chat									10
6.3.2. Calendar								10
6.3.3. Notes								10
6.3.4. Groceries								10
6.3.5. Media								10
6.3.6. Rewards								10





1. Preface

Requirements for an android application titled “FML” or Fix My Life. This document outlines terminology of the app, platform requirements, functional and non-functional requirements, constraints, assumption, and diagrams.

2. Intro
2.1. Overview –The intention of this app is to facilitate a happy and healthier and better organized lifestyle, either by yourself or with others.

2.2. Terminology
2.2.1. Living Group – group of users registered to the same living space or family.
2.2.2. Karma – points awarded for events inside app. These point can have a positive or negative value.
2.2.3. Head of Household(HOH) – the Admin user(s) for the living group.

3. Project Description

3.1. Initialization: When first ran, this app will ask for an existing user account or create a new one. After which the user will be asked if there are any persons living with the user and if they would like to participate in the experience. Steps will be taken to fit the user into their respective user category.
3.2. Start Screen: Upon starting this App the user will be presented with a table that is auto populated with preset options. The user will then be able to manipulate this adding or removing item as needed.
3.3. Users:  the users will be classified into three separate entities. Single users will have free use of the app with automated encouragement. Another user entity is that of a community living area, such as a dorm/apartment, these users will be able to share information back and forth to provide more adequate living arrangements. The final case will be that of a parent child relationship in which the parent will have admin rights over the child and the child will have to follow certain protocols.
3.4. Sharing/Sync:  the Data inputted by our users will be synchronized across all people in the living domain that use this -app.
3.5. Rewards System:  The rewards section is designed to give a sense of achievement when utilizing the app.  This section will have a progress bar which will balance the tasks accomplished with the ones that have not been accomplished with the ones that have. Positive “karma” points will be awarded to a person when an item is completed which will be exchanged for requests or unlocking mini games. There will be a history of tasks completed and points spend along with negative karma from missing a task. 
3.6. Inter-Communication: The chat activity allows the communication of family members in three sections, a cork board, a general text message board with filterable targets, and a video chat. The cork board will have post-it style note that pins to the board and may be tagged as a task. These tasks will be able to be imported into the calendar. With the general messenger there will be a drop down menu with groups that will change the target of the next message sent. The drop down menu will also change/select the target of the video call.
3.7. Inventory:    The Media Inventory allows the living group to browse and add items to their house-wide media inventory list. There will be a search bar at the top of the screen that will allow users to look for specific items in their inventory. There will be a two scrollable columnar lists in the middle of the screen, with one listing all currently owned media, and the other listing all media that the household group wishes to obtain. Each item will be clickable, on which a new activity would launch showing more detailed information about the entry, as well as button that would allow users to move items in the wanted list to the owned list, if the item is not already owned.  Above these lists will be three buttons, sort, filter, and add. Sort will allow users to sort the items in the lists by each items attributes. Filter will allow the users to only show items that contain certain attributes. Add will allow users to add items to either the “Owned” list or the “Wanted” list. Upon selecting add, a new activity will launch that will contain editable fields in which the user will enter all relevant information about the new media item. There will also be a button in this Add Item activity that will allow users to scan a barcode to auto-populate all relevant fields. Once complete, the user will either select save, in which case the item will be added to the list and the user will be returned to the Media Inventory Screen, or will select cancel, which will simply discard the new entry and return the user to the Media Inventory Activity. Lastly, located below both lists will be an “I can’t decide” button, which will launch a new activity in which the user will be allowed to select certain selection criteria if desired. The user will then select the “Pick Something” button, in which they will be presented with a randomly chosen media item, potentially based off of their selection criteria.
3.8. Scheduling:  The scheduling menu will allow users to interact with a calendar to manage their events, appointments, tasks, and to-do items. Users can add new items or edit current items by interacting with the calendar, as well as changing the current calendar view to suit their needs. As an additional feature, there will also be an area to select items from your to-do list and add them to your calendar or get suggestions for free time to schedule new items.
3.9. Notes:  The scheduling menu will allow users to interact with a calendar to manage their events, appointments, tasks, and to-do items. Users can add new items or edit current items by interacting with the calendar, as well as changing the current calendar view to suit their needs. As an additional feature, there will also be an area to select items from your to-do list and add them to your calendar or get suggestions for free time to schedule new items.
 
4. Requirements
4.1. Functional
4.1.1. Calendar:  Organize and cleanly display events to the user. The ability to import notes to this will be an additional feature. This will also provide a reminder when approaching time for event(s).
4.1.2. Notes List: Description of minor reminders and Idea that May not have Time/Date requirements.
4.1.3. Shopping List:  Dual Lists displaying items needed and items currently owned and in cabinets/refrigerator.
4.1.4. Media Inventory: Similar to Shopping List, catalogs all media in house including games, movies, music, eBooks, etc…  Also contains record of which person owns media, platform and other information about media items.
4.1.5.  Reward System: this is to give users rewards to utilizing this application through various means.
4.1.6. Inter-Communication: there will be a way of establishing a chat session for text, video, and Sticky Note communications.

4.2. Non-Functional
4.2.1. Admin User: this user(s) will set tasks and deadlines. Also, this user will be sent information to provide verification of the task to be confirmed and removed from the child’s chore list.
4.2.2. Synchronizer: this will act as the main communication between group members. This service will make sure that every user within the group will have sufficiently synchronized data.
4.2.3. Data manager: this is responsible for saving data in the correct location/category. Afterwards this will notify the synchronizer to update the other members.
4.2.4. Database: this database will be a SQL variation. It will consist of three tables, media, groceries, notes. 

5. System Requirements
5.1. Android handset with minimum SDK 5.0

5.2. Internet access

5.3. Front and back facing cameras

6. Appendix
6.1. A-Use Case Diagram

6.2. B-Class Diagram



6.3. C-Activity Diagrams

6.3.1. Chat



6.3.2. Rewards



6.3.3. Notes


6.3.4. Calendar


6.3.5. Pantry




6.3.6. Media




