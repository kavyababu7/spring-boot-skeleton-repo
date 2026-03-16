---
description: Add a new REST endpoint to an existing controller
---

When I am asked to "add endpoint" or "create endpoint", I should follow these standards:

1. **Naming**: Use kebab-case for URL paths (e.g., `/api/v1/resources/{id}/perform-action`).
2. **Standard Methods**: Use appropriate HTTP verbs (`GET`, `POST`, `PUT`, `DELETE`, `PATCH`).
3. **Validation**: Use `@Valid` on request bodies and `@PathVariable`/`@RequestParam` appropriately.
4. **Response**: Return `ApiResponse<T>`.
5. **Documentation**: Add `@Operation` and `@ApiResponse` annotations for Swagger.

**Example Template:**

```java
@Operation(summary = "Perform specific action", description = "Detailed description of the action")
@PostMapping("/{id}/action")
public ApiResponse<ResultDTO> performAction(@PathVariable Long id, @Valid @RequestBody ActionRequest request) {
    return ApiResponse.success(service.performAction(id, request));
}
```
