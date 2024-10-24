
# Dynamic PDF Generator Service

This project is a Spring Boot application that dynamically generates and serves PDF invoices based on incoming request data. The PDFs are created using the iText library, with customizable content for sellers, buyers, and item details.

## Features
- **Dynamic Invoice Generation**: Creates a PDF invoice based on user-supplied data for sellers, buyers, and items.
- **Customizable Filename**: The generated PDF is named using the format `invoice<TIMESTAMP>.pdf`, where `<TIMESTAMP>` is the current time in milliseconds.
- **Storage Location**: The PDFs are stored in a location specified in the `application.properties` file.

## Technologies Used
- **Spring Boot**: For building the REST API to handle the requests.
- **iText Library**: For generating the PDF documents.
- **Java**: The primary programming language used for the backend.

## How It Works
1. The user submits an invoice request with seller, buyer, and item information via a POST request to `/generate`.
2. A PDF invoice is generated and saved to the location specified by `pdf.storage.location` in `application.properties`.
3. The user can download the PDF by making a GET request to `/download/{filename}`, where `{filename}` is the name of the generated PDF.

## Endpoints

### POST `/generate`
Generates a new PDF invoice based on the request data.

**Request Body** (JSON):
```json
{
    "seller": "Seller Name",
    "sellerGstin": "Seller GSTIN",
    "sellerAddress": "Seller Address",
    "buyer": "Buyer Name",
    "buyerGstin": "Buyer GSTIN",
    "buyerAddress": "Buyer Address",
    "items": [
        {
            "name": "Item 1",
            "quantity": "2",
            "rate": 100.0,
            "amount": 200.0
        },
        {
            "name": "Item 2",
            "quantity": "1",
            "rate": 50.0,
            "amount": 50.0
        }
    ]
}
```

**Response**:
- 200 OK: Returns the filename of the generated PDF.
- 500 Internal Server Error: If there's an issue generating the PDF.
- 
## Configuration

In the `application.properties` file, set the storage location for the PDF files:
```properties
pdf.storage.location=C:/path/to/store/pdfs/
```

## Setup Instructions

1. Clone the repository.
2. Open the project in your preferred IDE.
3. Add the necessary dependencies (iText, Spring Boot) to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.2</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
4. Configure the storage path in the `application.properties` file.
5. Run the Spring Boot application.
6. Use Postman or curl to test the endpoints.

## Example Usage

1. **Generate PDF**:
   - POST to `/generate` with JSON body.
   - Response: `invoice1729799869969.pdf`.

## License
This project is licensed under the MIT License.
