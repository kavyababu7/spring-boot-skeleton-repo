---
description: Create a new REST controller following project standards
---

When I am asked to "add controller" or "create controller", I should follow these standards:

1. **Package**: Place in `com.nissan.template.controller`.
2. **Annotations**:
   - `@RestController`
   - `@RequestMapping("/api/v1/resource-name")`
   - `@RequiredArgsConstructor` (for dependency injection)
   - `@Tag` (for Swagger documentation)
3. **Response Wrapper**: Always return results wrapped in `ApiResponse<T>`.
4. **Operations**:
   - GET (all): Use pagination and sorting.
   - GET (by ID): Return 404 if not found.
   - POST: Use `@Valid` for request body.
5. **Business Logic**: Delegate all business logic to a Service.

**Example Template:**

```java
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Item Management", description = "Endpoints for managing items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ApiResponse<Page<ItemDTO>> getAllItems(Pageable pageable) {
        return ApiResponse.success(itemService.getAllItems(pageable));
    }

    @PostMapping
    public ApiResponse<ItemDTO> createItem(@Valid @RequestBody ItemRequest request) {
        return ApiResponse.success(itemService.createItem(request));
    }
}
```
