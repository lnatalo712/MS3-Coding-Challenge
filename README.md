# MS3-Coding-Challenge

# Purpose 
The purpose of this repository is to create a java application that consumes a CSV file and seperates the "good" data from the "bad"
data. The good data will then be inserted into a SQLite database and table. While the bad data will then be written to a new CSV file.
Bad data is considered any entry that is missing a data element or that does not match the column count. Finally, once all of the data
has been processed, statics of how many records were received, how many records were successful, and how many records failed will be
written to a log file.

# Setup 
In order to get this app running developers will simply need to download the java file, database file, and CSV file in this 
repository, have a java environment set up on their pc, and have SQLite installed on their pc. The program drops and creates the 
table at the beginning of each run so you don't need to worry about manually resetting the table.

# My Approach
My approach to creating this program was to first figure out how to distinguish good data from bad data. At first I tried to detect
empty cells in a row by checking to see if any of the cells had a value of null. For whatever reason this approach was not working,
so instead I checked to see if any of the cells had a length of zero. I also had to see if any of the rows had more than ten columns of
data which I acomplished by checking the entire length of the row and seperating out any rows with a length greater than or less than
ten. The next challenge I faced was being able to insert the good data into the SQLite database and table. In order to complete this
part of the task I made a few different functions. First, I made a connect function which I used multiple times to establish a
connection to the database file I had created. Next, I had functions to create and drop the table where the data was to be inserted.
These two functions were run at the beginning of the program everytime in order to start with a blank table at the beginning of each
run. This design was created to ensure that the program could be rerun without having to manually empty out the table or create a brand
new table before every run. The last function I had was an insert function which I used to insert all of the good data into the SQLite
table. One design choice I ran into while designing the create table function was deciding to make column C the primary key for the
table. I originally made columns A and B the primary key but there happened to be a good mount of repeat first and last name
combinations, so I switched the primary key to column C which ended up having only one repeat value. If I were allowed to create an
extra column in the table I would have made an ID column to give each good entry its own unique ID number. I also gave each column the
"not null" constraint because none of the good entries should have a null column. Finally, I have no experience with log files, so I did
some research into how to create one and populate it with certain statistics but I was unsuccesful in getting that part of the project
working. Instead I used three counters to keep track of the total entries, the total good entries, and the total bad entries and 
printed them out at the end of the program. This CSV file takes about 20 to 22 minutes to run.
