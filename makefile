# Compile Java files into the executable directory
all: clean
	javac -d ./executable src/*.java

# Run the program from the executable directory
run: all
	java -cp ./executable Main

clean:
	rm -rf ./executable/*.class
	clear
