Android Based Encrypted SMS System

Abstract:-
This is an advanced Encryption and decryption System targeting the SMS for Android Users both go and fro. The User can send an Encrypted message while he can decrypt an encrypted message. The System makes use of the SMS that you see in the inbox, but this system filters out the one which are encrypted and shows it in their Personal Inbox in the Application. The Shared private Key is already defined in the application and one has not to insert anything but the user id which is by default encrypted in the message. So whenever the user is sending a message he should know the receiver’s id as the id is also appended to the message so that while the receiver logins to the system the message is already decrypted if he is the desired recipient. The Id is Auto generated and cannot be changed but for the users ease the system allows the user to save the recipient’s id in a separate column as Favorites saving the his Id, Name and Mobile No. The Login is necessary here as a single user can have multiple accounts with different ids so that he can maintain each account for different purposes and not clubbing them. This System makes use of AES Encryption Algorithm to encrypt and decrypt the messages. 

Introduction:
Securing data encryption and decryption using Cryptography and Steganography techniques. Due to recent developments in steganography analysis, providing security to personal contents, messages, or digital images using steganography has become difficult. By using steganography analysis, one can easily reveal existence of hidden information in carrier files. This project introduces a novel steganography approach for communication between two private parties. The approach introduced in this project makes use of both steganography as well as cryptographic techniques. In Cryptography we are using RSA. In Steganography we are using Image Steganography for hiding the data. And we also use Mutual Authentication process to satisfy all services in Cryptography i.e., Access Control, Confidentiality, Integrity, Authentication. In this way we can maintain the data more securely. Since we use RSA algorithm for securing the data and again on this we perform Steganography to hide the data in an image. Such that any other person in the network cannot access the data present in the network. Only the sender and receiver can retrieve the message from the data.


Existing System:- 

The problem with the existing System is that mailing or messaging is done through browser by using services like Hotmail, Yahoo, Google, etc. These systems use HTTP port 80 to access the emails, and the overall procedure here is not safe to send confidential messages. This existing system can be easily hacked by hackers, some data may be modified or even lost.
Disadvantages:-
	The user has to login from his phone to see decrypt the messages.
	If the user deletes the message from his phone’s default app or inbox, it will be reflected on the current system also.
	Messages can’t be saved.


Proposed System:-
In this project, the text entered in the edit text is encrypted using the app instance and the encrypted text is shown. On clicking decrypt button the encrypted text is decrypted and real text is shown. Users communicate over all social media, but messages aren't secured when it passes through network. Intruder can access user’s message easily. We want to secure users communication over all social media. So here we proposed a system where user will enter the plain text and choose the algorithm type from AES and supply the key, a chipper text are going to be formed which will be sent via any communication application and user can decrypt the text by selecting an equivalent algorithm type and must enter an equivalent sender secret key. User can use our application and may enter the plain text and must select the algorithm type and must enter the key  to encrypt the message and receiver can decrypt the message by specifying an equivalent algorithm used for encryption and must use an equivalent secret key employed by sender. Intruder will find difficult to decrypt the message. By using this method you'll double make sure that your secret message is shipped securely without outside interference of hackers or crackers. 


Advantages:- 
	The user has to login keeping the data secure.
	The Messages is already decrypted for you. 
	The system also allows you to save recipients details which can be accessed only by the user.
	The messages are simple SMS’s but this app filters out all others and just keeps the encrypted ones.
	Fast and Easy to use.
	No Internet Needed.
	It is highly reliable and secure.
	Since only encrypted messages are shown old messages are also loaded quickly saving a lot of time which would go to search messages.








•	Server-side Script             	: PHP
•	IDE				: Android Studio.
•	SDK				: Android 
•	Libraries Used			: Volley, Material design.	

