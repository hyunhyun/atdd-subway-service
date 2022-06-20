package nextstep.subway.favorite.dto;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.favorite.domain.Favorite;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.dto.StationResponse;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

    public FavoriteResponse() {
    }

    public FavoriteResponse(Long id, StationResponse source, StationResponse target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public static FavoriteResponse of(Long id, Station source, Station target) {
        return new FavoriteResponse(id, StationResponse.from(source), StationResponse.from(target));
    }

    public static FavoriteResponse from(Favorite favorite) {
        return new FavoriteResponse(favorite.getId(), StationResponse.from(favorite.getSource()), StationResponse.from(favorite.getTarget()));
    }

    public static List<FavoriteResponse> toList(List<Favorite> favorites) {
        return favorites.stream()
            .map(FavoriteResponse::from)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public StationResponse getSource() {
        return source;
    }

    public StationResponse getTarget() {
        return target;
    }
}
