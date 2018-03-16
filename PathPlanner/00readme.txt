Compile using the "make" shell script, which should be executed from
the directory containing "make" (the top level of this distribution).
This puts compiled code in "PathPanel.jar" and documentation in the
"javadoc/" directory (at the same level as "make").

For an interactive demo, start the Java interface with "java -jar
PathPanel.jar".  Then use the following keys:

button1: place start
button2: place obstacle
button3: place goal
q: quit
s: toggle showing priority queue
RET: plan a few steps more and update queue
SPACE: follow one step of the path, if computed

There is also a Matlab interface, shortestpaths.m.  Calling
conventions and an example are in that file.  You will need to put the
PathPanel.jar file on Matlab's classpath (e.g., you can edit the file
shown by typing "which classpath.txt" from Matlab's prompt).  Sample
output is in demovalues.png.

Additional matlab support: getpath.m (for getting paths using the plan
computed by shortestpaths.m) and pathlen.m (for computing path costs).
