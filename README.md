# Containerized Video Streaming System

## The system microservices can be described as follows: 

### Upload Video (Web app) 
Used to upload MP4 file. Users are allowed to upload videos after validating their credentials through the Authentication Service.
Video names and paths on the filesystem are written to the MySql Service. The file itself is Written to a file storage through the File System Service. 

### Video Streaming (Web app) 
Used to view videos. Users are allowed to view videos after validating their credentials through the Authentication Service. 
The list of videos and their paths are taken from MySql DB service, and the video itself can be read from the file storage through the File System Service. 

### The Authentication Services 
A simple service to validate users credentials. 

### The File System service 
A simple service to write and read files to/from file storage 

### MySql DB 
This database will have a list of videos and their corresponding (path/URL) on file storage.  
