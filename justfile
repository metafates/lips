build:
	mvn compile assembly:single

build-with-scripts:
	mvn package appassembler:assemble

install: build-with-scripts
	rm -rf ~/lips
	mkdir ~/lips
	cp -r ./target/appassembler/ ~/lips/


clean:
	mvn clean

