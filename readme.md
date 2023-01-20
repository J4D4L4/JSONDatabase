JSON database is a single file database that stores information in the form of JSON. It is a remote database, so it's
usually accessed through the Internet.

- Stage 1: In this stage, you need to simulate a database that can store text information in an array of the size 100. From the
start of the database, every cell contains an empty string. Users can save strings in the cells, read the information
from these cells, and delete that information if needed. After a string has been deleted, that cell should contain an
empty string.
  - Started Implementation using DTO pattern 

Inspired and used https://github.com/Zeruxky/Client-Server-Pattern/tree/master/ClientServerPattern/Server for Client Server Pattern

- Stage 2 We will be using a socket to connect to the database. A socket is an interface to send and receive data between different processes. These processes can be on the same computer or different computers connected through the Internet.