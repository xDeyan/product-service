package bg.dyurukov.product.api;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExeptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDetails handleNotFoundException(EntityNotFoundException ex, HandlerMethod handler) {
    return handle(ex, handler);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorDetails handle(Exception ex, HandlerMethod handler) {
    logException(ex, handler);
    return ErrorDetails.builder().message(ex.getMessage()).build();
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDetails handle(BindException ex, HandlerMethod handler) {
    if (!ex.getBindingResult().hasFieldErrors()) {
      return handle((Exception) ex, handler);
    }

    logException(ex, handler);
    return buildErrorDetails(ex);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDetails handleBadRequest(BadRequestException ex, HandlerMethod handler) {
    return handle(ex, handler);
  }

  private ErrorDetails buildErrorDetails(BindException ex) {
    var fieldError = ex.getBindingResult().getFieldError();
    var fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(fe -> new FieldError(fe.getField(), fe.getDefaultMessage()))
            .toArray(FieldError[]::new);
    var message =
        MessageFormat.format("[{0}] {1}", fieldError.getField(), fieldError.getDefaultMessage());
    return ErrorDetails.builder().message(message).fieldErrors(fieldErrors).build();
  }

  private void logException(Exception ex, HandlerMethod handler) {
    log.error("Error in controller: {}", handler.getShortLogMessage(), ex);
  }

  @Getter
  @Builder
  @JsonInclude(Include.NON_NULL)
  public static final class ErrorDetails {

    private final String message;

    private final FieldError[] fieldErrors;
  }

  @Getter
  @RequiredArgsConstructor
  public static final class FieldError {

    private final String name;

    private final String message;
  }

  public static final class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6845056268793721123L;

    public EntityNotFoundException(Class<?> clazz, Object id) {
      super(MessageFormat.format("{0} with id {1} cannot be found.", clazz.getSimpleName(), id));
    }
  }

  public static final class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -6845056268793721123L;

    public BadRequestException(String message) {
      super(message);
    }

    public BadRequestException(String message, Throwable t) {
      super(message, t);
    }
  }
}
