package nextstep.subway.path.domain;

import java.util.Arrays;
import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.line.domain.Line;
import nextstep.subway.path.PathTestUtils;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PathTest {

    private DijkstraShortestPathFinder dijkstraShortestPathFinder;
    private Line 신분당선;
    private Line 이호선;
    private Line 삼호선;
    private Station 강남역;
    private Station 양재역;
    private Station 교대역;
    private Station 남부터미널역;
    private LoginMember loginMember;
    private Path path;

    /**
     * 교대역    --- *2호선*(10) ---  강남역
     * |                            |
     * *3호선*(5)                       *신분당선*(5)
     * |                            |
     * 남부터미널역  --- *3호선*(15) --- 양재역
     */
    @BeforeEach
    public void setUp() {
        //given
        강남역 = new Station("강남역");
        양재역 = new Station("양재역");
        교대역 = new Station("교대역");
        남부터미널역 = new Station("남부터미널역");
        신분당선 = new Line("신분당선", "red", 강남역, 양재역, 5, 1000);
        이호선 = new Line("이호선", "green", 교대역, 강남역, 10, 500);
        삼호선 = new Line("삼호선", "orange", 교대역, 양재역, 20, 0);
        삼호선.addSection(교대역, 남부터미널역, 5);

        dijkstraShortestPathFinder = new DijkstraShortestPathFinder(Arrays.asList(신분당선, 이호선, 삼호선));
        loginMember = new LoginMember(1L, "test@email.com", 20);
    }

    @DisplayName("교대역-양재역의 최단경로의 이용요금을 조회하면, 이용요금이 계산된다.")
    @Test
    void calculateFare() {
        //given
        path = DijkstraShortestPathFinderTest.최단_경로_조회함(dijkstraShortestPathFinder, 교대역, 양재역);

        //when
        path.calculateFare(loginMember);

        //then
        PathTestUtils.이용요금_확인(path, 2750); //나이 할인 없는 경우
    }
}
