package nextstep.subway.line.domain.section;

import nextstep.subway.exception.error.ErrorCode;
import nextstep.subway.exception.SectionException;

import javax.persistence.Embeddable;

import static nextstep.subway.utils.ValidationUtils.isNull;

@Embeddable
public class Distance {

    private static final int DISTANCE_ZERO = 0;

    private int distance;

    protected Distance() {
    }

    public Distance(int distance) {
        validation(distance);
        this.distance = distance;
    }

    public static int concat(Distance a, Distance b) {
        return a.distance + b.distance;
    }

    public void validation(int distance) {
        if (isNull(distance) || distance == DISTANCE_ZERO) {
            throw new SectionException(ErrorCode.BAD_ARGUMENT, "구간의 길이가 없습니다.");
        }
    }

    public void minus(Distance distance) {
        if (this.distance <= distance.distance) {
            throw new SectionException(ErrorCode.BAD_ARGUMENT, "구간의 길이가 잘못되었습니다.");
        }
        this.distance = this.distance - distance.distance;
    }

}