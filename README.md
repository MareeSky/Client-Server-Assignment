# Client-Server-Assignment
Client/Server Assignment in Java

1. (10 points) Write a server program and a client program. The client sends the weight 
and height for a person to the server. The server computes BMI (Body Mass Index) and 
sends back to the client a string that reports the BMI. You can use the following formula 
for computing BMI:  
  bmi = weightInKilograms / (heightInMeters * heightInMeters) 
 
2. (8 points) Revise the server program in Question-1 using threads to allow multiple 
clients. 

3. Implement the following generic method for linear search (return the first 
index value if the key is found in the list, return -1 otherwise). 
    public static <E extends Comparable<E>> int linearSearch(E[] list, E key) 
