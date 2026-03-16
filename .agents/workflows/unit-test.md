---
description: Create a comprehensive JUnit 5 unit test for a Java class
---

When I am asked to "write test" or "write unit test" for a class, I should follow these elite Spring Boot testing standards:

1. **Frameworks**: Use **JUnit 5**, **Mockito**, and **AssertJ**.
2. **Strategy**: 
   - Use `@ExtendWith(MockitoExtension.class)`.
   - Mock all dependencies using `@Mock`.
   - Inject the mocks into the system under test using `@InjectMocks`.
3. **Structure**: Follow the **Given-When-Then** pattern.
4. **Coverage**: 
   - Test happy path scenarios.
   - Test edge cases (nulls, empty collections, etc.).
   - Test exception handling and error codes.
5. **Aesthetics**: Use descriptive test names (e.g., `shouldReturnUserDto_whenUserIdExists`).
6. **Assertions**: Use AssertJ `assertThat()` for fluent and readable assertions.

**Example Template:**

```java
@ExtendWith(MockitoExtension.class)
class MyServiceTest {
    @Mock
    private MyRepository repository;
    
    @InjectMocks
    private MyService service;
    
    @Test
    @DisplayName("Should successfuly do something when input is valid")
    void shouldDoSomething_whenInputIsValid() {
        // Given
        given(repository.find(any())).willReturn(Optional.of(new Entity()));
        
        // When
        var result = service.execute("input");
        
        // Then
        assertThat(result).isNotNull();
        verify(repository).save(any());
    }
}
```
