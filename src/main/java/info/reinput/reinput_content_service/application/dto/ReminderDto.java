package info.reinput.reinput_content_service.application.dto;

import info.reinput.reinput_content_service.common.ReminderType;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderServiceType;
import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Builder
public record ReminderDto(
        Long id,
        boolean enable,
        ReminderType reminderType,
        List<Integer> reminderDays
) {
    public List<ReminderServiceType> reminderServiceTypes() {
        if (reminderDays == null || reminderDays.isEmpty()) {
            return List.of();
        }

        return switch (reminderType) {
            case MONTH -> reminderDays.stream()
                    .map(day -> {
                        try {
                            // 예: day가 1이면 "MONTH_1" enum 값을 반환
                            return ReminderServiceType.valueOf("Monthly_" + day);
                        } catch (IllegalArgumentException e) {
                            // day 값이 유효하지 않은 경우 무시
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            case WEEK -> reminderDays.stream()
                    .map(day -> switch (day) {
                        case 1 -> ReminderServiceType.Weekly_Mon;
                        case 2 -> ReminderServiceType.Weekly_Tue;
                        case 3 -> ReminderServiceType.Weekly_Wed;
                        case 4 -> ReminderServiceType.Weekly_Thu;
                        case 5 -> ReminderServiceType.Weekly_Fri;
                        case 6 -> ReminderServiceType.Weekly_Sat;
                        case 7 -> ReminderServiceType.Weekly_Sun;
                        default -> null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
            case DEFAULT -> List.of(ReminderServiceType.Recommended);
        };
    }

    public List<Integer> reminderDaysFromServiceTypes(List<ReminderServiceType> serviceTypes) {
        if (serviceTypes == null || serviceTypes.isEmpty()) {
            return List.of();
        }
        return switch (reminderType) {
            case MONTH -> serviceTypes.stream()
                    .map(serviceType -> {
                        // enum 이름은 "Monthly_1", "Monthly_2", ... 형식이라고 가정
                        String name = serviceType.name();
                        // "Monthly_" 접두사 길이만큼 substring
                        try {
                            return Integer.parseInt(name.substring("Monthly_".length()));
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            case WEEK -> serviceTypes.stream()
                    .map(serviceType -> switch (serviceType) {
                        case Weekly_Mon -> 1;
                        case Weekly_Tue -> 2;
                        case Weekly_Wed -> 3;
                        case Weekly_Thu -> 4;
                        case Weekly_Fri -> 5;
                        case Weekly_Sat -> 6;
                        case Weekly_Sun -> 7;
                        default -> null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
            case DEFAULT -> List.of(); // 기본(Default)인 경우, 정수 값이 없으므로 빈 리스트 반환
        };
    }
}
