package bg.dyurukov.product.api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductApiCRUDTest {

  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void whenProductIsCreated_thenStatus201IsReturned() throws Exception {

    var body =
        """
            {
              "name": "AOC 24G2U",
              "category": "Monitor",
              "description": "Nice monitor",
              "quantity": 5
            }
            """;

    mockMvc
        .perform(post("/api/v1/products").content(body).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  void whenProductIsCreated_andQuantityIsNegative_thenStatus400IsReturned() throws Exception {

    var body =
        """
            {
              "name": "AOC 24G2U",
              "category": "Monitor",
              "description": "Nice monitor",
              "quantity": -5
            }
            """;

    mockMvc
        .perform(post("/api/v1/products").content(body).contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("$.message")
                .value(containsString("[quantity] must be greater than or equal to 0")))
        .andExpect(jsonPath("$.fieldErrors.size()").value(1))
        .andExpect(jsonPath("$.fieldErrors.[0].name").value("quantity"))
        .andExpect(
            jsonPath("$.fieldErrors.[0].message").value(is("must be greater than or equal to 0")))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenOrderQuantityExceedsProductsQuantity_thenStatusIs400_andMessageIsPresent()
      throws Exception {

    var body =
        """
                {
                  "quantity": 1000
                }
                """;

    mockMvc
        .perform(
            post("/api/v1/products/{id}/quantity", 1)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("$.message").value(is("Order quantity cannot exceed the available quantity.")))
        .andExpect(status().isBadRequest());
  }
}
