
# How does it work?

The Server listens for numPlayer many clients on the given port.
For every newly connected client a ServerClient object is created.
The ServerClient objects are all runnable and run in their own thread after the dispatch function was called.
The server also runs in its own thread.