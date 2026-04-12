# Compile Java files into the executable directory
all: clean
	javac -d ./executable src/*.java src/GA/*.java

# Run the program from the executable directory
run: all
	java -cp ./executable Main

clean:
	rm -rf ./executable/*.class
	clear

cleanData:
	rm -rf ./data/*
	rm -rf ./temp_extract
	mkdir -p ./temp_extract
	7z x "Knapsack Instances.7z" -o./temp_extract -y
	mv ./temp_extract/*/*/* ./data/
	rm -rf ./temp_extract