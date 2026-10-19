package sfera.crmsfera.dto;


import sfera.crmsfera.entity.enums.WeekDays;

import java.util.List;


public record DayStat(
        WeekDays day,
        List<TimeInterval> busy,
        List<TimeInterval> free
) {}
