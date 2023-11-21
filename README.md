<ol>
    <li>Download the latest Maven with the default JDK from <a href="https://phoenixnap.com/kb/install-maven-on-ubuntu">this link</a>.</li>
    <li>
      Install open jdk 17
      <pre>
        <code>sudo apt install openjdk-17-jdk</code>
       </pre>
    </li>
    <li>
     Setting JAVA_HOME:to java 17
       <pre>
        <code>export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64/bin/java</code>
       </pre>
    </li>
    <li>
     Updating PATH to Include Java Binaries:
       <pre>
        <code>export PATH=$JAVA_HOME/bin:$PATH</code>
       </pre>
    </li>
    <li>Download PostgreSQL locally.</li>
        <pre>
        <code>sudo apt install postgresql</code>
                </pre>
    <li>Create a table inside the PostgreSQL database using the following SQL query:
        <pre>
            <code>
CREATE TABLE game_states(
    id serial primary key,
    snakeX int[],
    snakeY int[],
    enemyX int,
    enemyY int,
    moves int,
    score int,
    lengthOfSnake int,
    isleft boolean,
    isright boolean,
    isup boolean,
    isdown boolean
);
            </code>
        </pre>
    </li>
    <li>Update the PostgreSQL user "postgres" password to 'chandan1singh':
        <pre>
            <code>
ALTER USER postgres WITH PASSWORD 'chandan1singh';
            </code>
        </pre>
    </li>
    <li>Reboot the system
        <pre>
            <code>
        sudo reboot
            </code>
        </pre>
    </li>
    <li>Inside the project directory, run the following command to build and verify your Maven project, which includes compiling and running tests:
        <pre>
            <code>
mvn clean verify
            </code>
        </pre>
    </li>
    <li>You can find the test results in the /target/site/index.html page.</li>
</ol>
