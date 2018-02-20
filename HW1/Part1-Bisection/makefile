all: main

main: main.o
	gcc main.o -o exe -lm
    
main.o: main.c
	gcc -c main.c
    
clean: 
	rm *.o exe
