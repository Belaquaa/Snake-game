package belaquaa.demosnake.controller;

import belaquaa.demosnake.enums.Direction;
import belaquaa.demosnake.model.Apple;
import belaquaa.demosnake.model.Point;
import belaquaa.demosnake.model.Snake;
import belaquaa.demosnake.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGameState() throws Exception {
        Snake snake = new Snake(new Point(5, 5), 3);
        Apple apple = new Apple(new Point(10, 10));
        when(gameService.getSnake()).thenReturn(snake);
        when(gameService.getApple()).thenReturn(apple);

        mockMvc.perform(get("/api/game/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.snake.body[0].x").value(5))
                .andExpect(jsonPath("$.snake.body[0].y").value(5))
                .andExpect(jsonPath("$.apple.position.x").value(10))
                .andExpect(jsonPath("$.apple.position.y").value(10));
    }

    @Test
    public void testSetDirection() throws Exception {
        doNothing().when(gameService).updateDirection(Direction.UP);

        mockMvc.perform(post("/api/game/direction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"UP\"")) // Note the double quotes to represent a JSON string
                .andExpect(status().isOk());

        verify(gameService, times(1)).updateDirection(Direction.UP);
    }


    @Test
    public void testUpdateGame() throws Exception {
        when(gameService.updateGame()).thenReturn(true);

        mockMvc.perform(post("/api/game/update"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(gameService, times(1)).updateGame();
    }

    @Test
    public void testResetGame() throws Exception {
        doNothing().when(gameService).resetGame();

        mockMvc.perform(post("/api/game/reset"))
                .andExpect(status().isOk());

        verify(gameService, times(1)).resetGame();
    }
}
