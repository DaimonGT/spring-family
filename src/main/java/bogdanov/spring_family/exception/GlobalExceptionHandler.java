package bogdanov.spring_family.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice // для глобальной обработки исключений
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now(),
                "Дмитрий Богданов"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    // Класс Error response
    // можно ли его писать отдельно?
    public static class ErrorResponse {
        private int status;
        private String message;
        private String path;
        private LocalDateTime timestamp;
        private String author;

        // конструктор
        public ErrorResponse(int status, String message, String path, LocalDateTime timestamp, String author) {
            this.status = status;
            this.message = message;
            this.path = path;
            this.timestamp = timestamp;
            this.author = author;
        }

        // Геттеры и сеттеры
        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
