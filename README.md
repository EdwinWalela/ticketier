# Ticketier
Producing cryptograhically secured tickets. 

## Depandancies (libraries)
1. javase-3.3.3
2. mysql-connector-java-8.0.12
3. zxing-core-3.3.0
4. webcam-capture-0.3.10-dist

## Classes 
1. Util
2. Query
3. Event
4. Ticket
5. User

## 1. Query
Handles all database queries. Constructor expects a connection object

```java
Query query = new Query(Connection);
```
### Methods
#### Register a new user
```java
public boolean createUser(User) throws NoSuchAlgorithmException,SQLException
```
#### User Login
```java
public boolean authorizeUser(int id,String pass) throws NoSuchAlgorithmException,SQLException
// True -> passwords match
// False -> password mismatch
```
#### Event Creation
```java
public boolean createEvent(Event) throws SQLException
```
#### Fetch all Events
```java
public Event[] getEvents() throws SQLException
```
#### Fetch an event by id
```java
public Event getEvent(int) throws SQLException
```
#### Ticket Creation
```java
public boolean createTicket(Ticket) throws SQLException
```
#### Find Ticket by digest
```java
public boolean getTicket(String) throws SQLException
```

## 2. Util
Utility class which performs key processes which include hashing,encryption,qr generation and scanning and interpratation. 
Encryption uses `AES/CBC/PKCS5PADDIN` 
Hashing uses `SHA-256` algorithim

All methods in this class are static, hence can be accessed without creating an instance of the class.

### Methods

#### Encrypt 
```java
public static String encrypt(String) throws Exception
```

#### Decrypt
```java
public static String decrypt(String) throws Exception
```

#### Generate QR code
Generates a QR-Code png at the specified file path
```java
  public static void generateQR(String text,String filePath) throws WriterException,IOException
```

#### Read QR code
Interprates contents of the QR-Code png contained in the specified path
```java
  public static String readQRCode(String) throws IOException,NotFoundException
```

#### Take Picture
Takes a photo from the webcam and saves the image as `ticket.png` in the current working directory
```java
 public static void takePic() throws IOException
```

#### SHA-256 Hash function
```java
public static String sha256(String) throws NoSuchAlgorithmException
```

