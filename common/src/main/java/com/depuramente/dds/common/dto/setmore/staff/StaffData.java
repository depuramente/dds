package com.depuramente.dds.common.dto.setmore.staff;

import java.util.List;

public record StaffData(String cursor, List<StaffDto> staffs) {
}
