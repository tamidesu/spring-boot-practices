//package kz.com.my_retro.exception;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Map;
//
//@ControllerAdvice
//public class RetroBoardResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler({CardNotFoundException.class, RetroBoardNotFoundException.class})
//    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
//
//        Map<String, Object> response = Map.of(
//                "msg", "There is an error",
//                "code", HttpStatus.NOT_FOUND.value(),
//                "time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                "errors", Map.of("msg", ex.getMessage())
//        );
//
//        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//}
