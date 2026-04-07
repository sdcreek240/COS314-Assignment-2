# Compile Java files into the executable directory
all: clean
	javac -d ./executable src/*.java

# Run the program from the executable directory
run: all
	java -cp ./executable Main

clean:
	rm -rf ./executable/*.class
	clear

cleanData:
	rm -rf ./data/*
	7z x "Knapsack Instances.7z" -o./data -y