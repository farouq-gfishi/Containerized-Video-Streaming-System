# Containerized Video Streaming System

## Overview

The Containerized Video Streaming System is a distributed application that consists of multiple microservices. It allows users to upload and view MP4 videos through a set of microservices. Each service is containerized using Docker, facilitating easy deployment and scalability.

## Microservices

1. **Upload Video (Spring MVC)**

   - **Purpose**: Handles the upload of MP4 files.
   - **Functionality**: 
     - Users must validate their credentials through the Authentication Service.
     - Video names and paths are stored in the MySQL Database Service.
     - The video file is saved to file storage through the File System Service.

2. **Video Streaming (Spring MVC)**

   - **Purpose**: Allows users to view uploaded videos.
   - **Functionality**: 
     - Users must validate their credentials through the Authentication Service.
     - Retrieves video metadata (names and paths) from the MySQL Database Service.
     - Reads the video file from file storage using the File System Service.

3. **Authentication Service (Spring REST)**

   - **Purpose**: Validates user credentials.
   - **Functionality**: 
     - Provides endpoints for user authentication.

4. **File System Service (Spring REST)**

   - **Purpose**: Manages file storage operations.
   - **Functionality**: 
     - Handles file uploads and retrievals from file storage.

5. **MySQL DB**

   - **Purpose**: Stores video metadata.
   - **Functionality**: 
     - Keeps a record of video names and their corresponding paths or URLs in file storage.

## System Requirements

- **Java Development Kit (JDK)**: Version 11 or higher.
- **Docker**: For containerized deployment.
- **Docker Compose**: For orchestrating multiple containers.

## Installation and Setup

### Clone the Repository

```bash
git clone https://github.com/yourusername/your-repository.git
cd your-repository
```

### Build the Project

Use Maven to build and package the application:

```bash
mvn clean install
```

### Run the Services

Use Docker Compose to start all microservices:

```bash
docker-compose up
```
## Usage

### Uploading a Video

1. Send a POST request to the Upload Video Service with the video file and authentication credentials.
2. The video file will be saved to the file storage, and metadata will be recorded in the MySQL DB.

### Viewing a Video

1. Send a GET request to the Video Streaming Service with authentication credentials.
2. The service will fetch video metadata from MySQL DB and stream the video from file storage.

## Configuration

Configuration settings are managed through environment variables and configuration files. Refer to `docker-compose.yml` file for configuration options.


