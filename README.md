# Containerized Video Streaming System

## The system microservices can be described as follows: 

### 1. Upload Video (Spring MVC) 
Used to upload MP4 file. Users are allowed to upload videos after validating their credentials through the Authentication Service.
Video names and paths on the filesystem are written to the MySql Service. The file itself is Written to a file storage through the File System Service. 

### 2. Video Streaming (Spring MVC) 
Used to view videos. Users are allowed to view videos after validating their credentials through the Authentication Service. 
The list of videos and their paths are taken from MySql DB service, and the video itself can be read from the file storage through the File System Service. 

### 3. The Authentication Services (Spring REST) 
A simple service to validate users credentials. 

### 4. The File System service (Spring REST)
A simple service to write and read files to/from file storage 

### 5. MySql DB 
This database will have a list of videos and their corresponding (path/URL) on file storage.  
