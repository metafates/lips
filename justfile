run: build
	java -jar ./target/lips-1.0-SNAPSHOT-jar-with-dependencies.jar

build: clean
	mvn compile assembly:single

build-with-scripts:
	mvn package appassembler:assemble

install: build-with-scripts
	rm -rf ~/lips
	mkdir ~/lips
	cp -r ./target/appassembler/ ~/lips/

clean:
	mvn clean

