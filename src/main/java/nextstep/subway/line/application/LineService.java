package nextstep.subway.line.application;

import nextstep.subway.exception.NotFoundLineException;
import nextstep.subway.exception.NotFoundStationException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.line.dto.SectionRequest;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LineService {
    private final LineRepository lineRepository;
    private final StationService stationService;

    public LineService(LineRepository lineRepository, StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    public LineResponse saveLine(LineRequest request) {
        Station upStation = stationService.findById(request.getUpStationId());
        Station downStation = stationService.findById(request.getDownStationId());
        Line persistLine = lineRepository.save(new Line(request.getName(), request.getColor(), upStation, downStation, request.getDistance()));
        return LineResponse.of(persistLine);
    }

    @Transactional(readOnly = true)
    public List<LineResponse> findLines() {
        List<Line> persistLines = lineRepository.findAll();
        return persistLines.stream()
                .map(LineResponse::of)
                .collect(Collectors.toList());
    }

    private Line findLineById(Long id) throws NotFoundLineException {
        return lineRepository.findById(id).orElseThrow(() -> new NotFoundLineException(id));
    }


    @Transactional(readOnly = true)
    public LineResponse findLineResponseById(Long id) throws NotFoundLineException {
        Line persistLine = findLineById(id);
        return LineResponse.of(persistLine);
    }

    public void updateLine(Long id, LineRequest lineUpdateRequest) throws NotFoundLineException {
        Line persistLine = findLineById(id);
        persistLine.update(new Line(lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    public void addLineStation(Long lineId, SectionRequest request) throws NotFoundLineException {
        Line line = findLineById(lineId);
        Station upStation = stationService.findStationById(request.getUpStationId());
        Station downStation = stationService.findStationById(request.getDownStationId());
        line.addSection(new Section(line, upStation, downStation, request.getDistance()));
    }

    public void removeLineStation(Long lineId, Long stationId) throws NotFoundStationException, NotFoundLineException {
        Line line = findLineById(lineId);
        Station station = stationService.findStationById(stationId);
        line.deleteSection(station);
    }
}
