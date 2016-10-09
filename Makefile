all:
	javac -d . -classpath src/ src/pl/parser/nbp/MainClass.java
run:
	java pl.parser.nbp.MainClass EUR 2013-01-28 2013-01-31
clean:
	rm -rf pl/
	rm *log
