# competitive-programming

My solutions to problems from various websites.

All code committed to this repo has received AC verdict, though not necessarily
optimal (wrt theoretical running time). Code submitted to contests with partial
scoring (i.e. Codechef) may also not have received full points.

## Java setup/workflow

Make a copy of `Template.java` and rename to `Main.java`. Compile and run with
`javac Main.java ; java Main < in` where `in` is a file containing the
problem's inputs. For running locally with very large inputs, be sure to
increase the memory (`-Xmx512m`) and/or stack memory (`-Xss512m`) as necessary.
Upon receiving AC verdict, I rename the file to the problem id and archive it
for keepsake.

## C++ setup/workflow

Make a copy of `template.cpp` and rename to `main.cpp`. Compile and run with
`make build ; ./main < in`. This is set up to compile with maximum
debuggability. If a fast binary is needed for some reason, use
`make build-fast`. If needed, `debug.h` can be included to print container
types. Just make sure to comment out the `#include` and any print statements
before submitting.
