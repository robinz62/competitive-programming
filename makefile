FLAGS := -O2 -Wall -Wextra -pedantic -Wshadow -Wformat=2 -Wfloat-equal -Wconversion -Wlogical-op -Wshift-overflow=2 -Wduplicated-cond -Wcast-qual -Wcast-align -Wno-unused-result -Wno-sign-conversion
DEBUG_FLAGS := -g -fsanitize=address -fsanitize=undefined -fsanitize=float-divide-by-zero -fsanitize=float-cast-overflow -fno-sanitize-recover=all -fstack-protector-all -D_FORTIFY_SOURCE=2

# Below flags add some error reporting for e.g. stl containers, but they also
# suppress the sanitize output which crucially reports line numbers, so I
# prefer not to use these.
# DEBUG_FLAGS += -D_GLIBCXX_DEBUG -D_GLIBCXX_DEBUG_PEDANTIC

build: main.cpp
	g++ -o main -std=c++2a $(FLAGS) $(DEBUG_FLAGS) main.cpp

build-fast: main.cpp
	g++ -o main-fast -std=c++2a $(FLAGS) main.cpp

build-17: main.cpp
	g++ -o main -std=c++17 $(FLAGS) $(DEBUG_FLAGS) main.cpp

build-fast-17: main.cpp
	g++ -o main-fast -std=c++17 $(FLAGS) main.cpp

clean:
	rm -f main main-fast
.PHONY: clean