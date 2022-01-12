package bg.dyurukov.product.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import bg.dyurukov.product.ProductServiceApplication;

@Tag("integration")
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = ProductServiceApplication.class)
@TestPropertySource(locations = "classpath:application.yml")
class ProductApiPaginationTest {

  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void whenFiltersAreUsed_thenContentIsFiltered() throws Exception {
    mockMvc
        .perform(get("/api/v1/products").queryParam("id", "1"))
        .andExpect(jsonPath("$.content.size()").value(1))
        .andExpect(jsonPath("$.content.[0].id").value(1))
        .andExpect(jsonPath("$.content.[0].name").value("Dell 5401"))
        .andExpect(jsonPath("$.content.[0].category").value("Laptop"))
        .andExpect(jsonPath("$.content.[0].description").value("Dell description"))
        .andExpect(jsonPath("$.content.[0].quantity").value(12))
        .andExpect(jsonPath("$.content.[0].createdOn").value("2020-05-20"))
        .andExpect(jsonPath("$.content.[0].modifiedOn").value("2020-06-30"))
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  void testResponseHasProperPaginationFields() throws Exception {
    mockMvc
        .perform(get("/api/v1/products").queryParam("name", "Samsung"))
        .andExpect(jsonPath("$.totalPages").value(1))
        .andExpect(jsonPath("$.number").value(0))
        .andExpect(jsonPath("$.totalElements").value(1))
        .andExpect(jsonPath("$.empty").value(false))
        .andExpect(jsonPath("$.content.size()").value(1))
        .andExpect(jsonPath("$.content.[0].id").value(3))
        .andExpect(jsonPath("$.content.[0].name").value("Samsung"))
        .andExpect(jsonPath("$.content.[0].category").value("Monitor"))
        .andExpect(jsonPath("$.content.[0].description").value("Samsung monitor"))
        .andExpect(jsonPath("$.content.[0].quantity").value(9))
        .andExpect(jsonPath("$.content.[0].createdOn").value("2020-06-30"))
        .andExpect(jsonPath("$.content.[0].modifiedOn").value("2020-07-15"))
        .andExpect(status().is(HttpStatus.OK.value()));
  }
}
