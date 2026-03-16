---
description: Add standardized SLF4J logging with MDC correlation
---

When I am asked to "add logging" or "log this", I should follow these standards:

1. **Annotation**: Use Lombok's `@Slf4j` annotation on the class.
2. **Correlation**: Ensure that important business identifiers (like `userId`, `orderId`) are included in the logs.
3. **Levels**:
   - `INFO`: For significant lifecycle events or successful business operations.
   - `WARN`: For unexpected but recoverable situations.
   - `ERROR`: For critical failures that need investigation.
   - `DEBUG`: For detailed execution flow helpful during development.
4. **Consistency**: Use a consistent format: `[Action] [Entity] [ID] - [Status/Message]`.
5. **Security**: NEVER log sensitive data (passwords, tokens, PII).

**Example:**

```java
@Slf4j
@Service
public class MyService {
    public void process(String id) {
        log.info("Processing entity with ID: {}", id);
        try {
            // business logic
        } catch (Exception e) {
            log.error("Failed to process entity [{}]: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
```
