<ol>
    <li>Download the latest Maven with the default JDK from <a href="https://phoenixnap.com/kb/install-maven-on-ubuntu">this link</a>.</li>
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
    <li>Inside the project directory, run the following command to clean and verify the Maven project:
        <pre>
            <code>
mvn clean verify
            </code>
        </pre>
    </li>
</ol>
