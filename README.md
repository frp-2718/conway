#Conway
Simple and simplistic implementation of Conway's game of life. Kind of homework.

Non-toric board and naive algorithm.

##Compile
    :::console
    mkdir bin
    javac -d bin -sourcepath src src/conway/ui/Conway.java

##Run
    :::console
    java -classpath bin conway.ui.Conway

##Generate javadoc
    :::console
    javadoc -d doc -sourcepath src -subpackages conway

##Create an executable jar
In the subdirectory bin : 
    
    :::console
    jar -cvfm ../Conway.jar ../META-INF/MANIFEST.MF .

###License
This program is published under [MIT License](http://opensource.org/licenses/MIT). Please read the LICENSE file for further information.

