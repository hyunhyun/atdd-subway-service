package nextstep.subway.path.factory;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.line.acceptance.LineAcceptanceTest;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.station.StationAcceptanceTest;
import nextstep.subway.station.dto.StationResponse;

import static nextstep.subway.line.acceptance.LineSectionAcceptanceTest.지하철_노선에_지하철역_등록_요청;

public class PathAcceptanceFactory {

    public static StationResponse 지하철역_등록되어_있음(String name) {
        return StationAcceptanceTest.지하철역_등록되어_있음(name).as(StationResponse.class);
    }

    public static LineResponse 지하철_노선_등록되어_있음(
            String name,
            String color,
            StationResponse upStation,
            StationResponse downStation,
            int distance
    ) {
        LineRequest lineRequest = new LineRequest(name, color, upStation.getId(), downStation.getId(), distance);
        return LineAcceptanceTest.지하철_노선_등록되어_있음(lineRequest).as(LineResponse.class);
    }

    public static ExtractableResponse<Response> 지하철_노선에_지하철역_등록되어_있음(
            LineResponse line,
            StationResponse upStation,
            StationResponse downStation,
            int distance
    ) {
        return 지하철_노선에_지하철역_등록_요청(line, upStation, downStation, distance);
    }
}
