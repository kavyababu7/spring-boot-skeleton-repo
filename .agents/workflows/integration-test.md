---
description: Create a Spring Boot integration test
---

When I am asked to "write integration test" or "test with database", I should follow these standards:

1. **Frameworks**: Use `@SpringBootTest` and `MockMvc`.
2. **Configuration**: 
   - Use `@ActiveProfiles("dev")` or a specific `test` profile.
   - Use `@AutoConfigureMockMvc`.
   - Ensure the database is handled (using Testcontainers or a H2/embedded DB configured in the profile).
3. **Focus**: Test the full flow from Controller to Database.
4. **Assertions**: Verify both the HTTP response and the side effects in the database.

**Example Template:**

```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class MyControllerIT {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private MyRepository repository;
    
    @Test
    void shouldCreateUser_andPersistInDatabase() throws Exception {
        // Given
        String userJson = "{\"username\": \"testuser\", \"email\": \"test@example.com\"}";
        
        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("testuser"));
                
        assertThat(repository.findByUsername("testuser")).isPresent();
    }
}
```
