
# Spring AI Image Generator and Vision API

## Overview
This project demonstrates the integration of OpenAI's powerful image generation and vision capabilities using Spring AI. It provides two main features:
1. Generation of realistic images based on text descriptions using DALL-E 3
2. Analysis and description of uploaded images using GPT-4 Vision

## Technologies
- Java 21
- Spring Boot 3.5.3
- Spring AI 1.0.0
- Spring Web
- Project Lombok
- OpenAI Integration (DALL-E 3 and GPT-4 Vision)

## Prerequisites
- JDK 21
- Maven
- OpenAI API Key

## Configuration
1. Create an environment variable with your OpenAI API key:
   ```
   export OPENAI_API_KEY=your_api_key_here
   ```

2. The application configuration is managed through `application.yml`:
   ```yaml
   spring:
     ai:
       openai:
         api-key: ${OPENAI_API_KEY}
   ```

## API Endpoints

### 1. Generate Image
Generate a realistic image based on a text description.

- **Endpoint**: POST `/image`
- **Content-Type**: `application/json`
- **Produces**: `image/png`
- **Request Body**:
  ```json
  {
      "question": "Your image description here"
  }
  ```

### 2. Analyze Image
Upload an image to get an AI-generated description of its contents.

- **Endpoint**: POST `/vision`
- **Content-Type**: `multipart/form-data`
- **Produces**: `application/json`
- **Parameters**:
    - `file`: The image file to analyze (JPEG format)
    - `name`: Name/identifier for the upload

## Features
- High-quality image generation with DALL-E 3
- Customizable image parameters (1024x1024 resolution, HD quality)
- Natural style image generation
- Detailed image analysis using GPT-4 Vision
- RESTful API design
- Exception handling and proper response formatting

## Building and Running
1. Clone the repository
2. Build the project:
   ```
   mvn clean install
   ```
3. Run the application:
   ```
   mvn spring-boot:run
   ```

## Usage Examples

### Generate an Image
```bash
curl -X POST http://localhost:8080/image
-H "Content-Type: application/json"
-d '{"question":"A serene landscape with mountains and a lake at sunset"}'
```
### Analyze an Image
```bash
curl -X POST http://localhost:8080/vision
-F "file=@path/to/your/image.jpg"
-F "name=scenic_view"
```

## Security Considerations
- The API key is stored as an environment variable for security
- Make sure to implement appropriate authentication and authorization in production
- Consider rate limiting for production use

## Contributing
Feel free to submit issues, fork the repository, and create pull requests for any improvements.

## License
This project is open source and available under standard terms.

---

**Maintainer:** [Intercont](https://github.com/Intercont)

*Built with [Spring Boot](https://spring.io/projects/spring-boot) and [Spring AI](https://github.com/spring-projects/spring-ai).*

**Subscribe at [igorfragadev.com](https://igorfragadev.com) for more**