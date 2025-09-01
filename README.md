# Poem-of-the-Day-
This project contains two programs that demonstrate different approaches to client-server communication in Java:

 Poem of the Day (PoD) Server
A simple text-based server that shares poems with clients.
The server:
-Loads poems from a text file at startup.
-Waits for a client (via Telnet) to connect.
-Sends a welcome message, a list of available poems, and instructions.
-Accepts the client’s poem selection.
-Responds with the chosen poem (or an error message if input is invalid).
-Ends the session and waits for the next client.
-The protocol for this server is defined as a finite-state machine (FSM) for clarity and structure.

