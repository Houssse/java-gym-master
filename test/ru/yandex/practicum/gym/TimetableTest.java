package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {
    private Timetable timetable;

    @BeforeEach
    public void setUp() {
        timetable = new Timetable();
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assert mondaySessions.size() == 1;

        List<TrainingSession> tuesdaySession =  timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assert tuesdaySession.size() == 0;
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assert mondaySessions.size() == 1;

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(new TimeOfDay(13, 0), thursdaySessions.get(0).getTimeOfDay());
        Assertions.assertEquals(new TimeOfDay(20, 0), thursdaySessions.get(1).getTimeOfDay());

        List<TrainingSession> tuesdaySession =  timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assert tuesdaySession.size() == 0;
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions1 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        Assertions.assertEquals(1, mondaySessions1.size());

        List<TrainingSession> mondaySessions2 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        Assertions.assertEquals(0, mondaySessions2.size());
    }

    @Test
    void testEmptyTimetable() {

        List<TrainingSession> mondayTrainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(0, mondayTrainingSessions.size());

        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        Assertions.assertEquals(0, trainingSessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdultOld = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdultOld, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);



        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY,
                new TimeOfDay(20 , 0)).size());

    }

    @Test
    void testGetTrainingSessionsForDaySortedOrder() {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession lateSession = new TrainingSession(group, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession earlySession = new TrainingSession(group, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(10, 0));
        TrainingSession middleSession = new TrainingSession(group, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(lateSession);
        timetable.addNewTrainingSession(earlySession);
        timetable.addNewTrainingSession(middleSession);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(3, sessions.size());
        Assertions.assertEquals(new TimeOfDay(10, 0), sessions.get(0).getTimeOfDay());
        Assertions.assertEquals(new TimeOfDay(13, 0), sessions.get(1).getTimeOfDay());
        Assertions.assertEquals(new TimeOfDay(20, 0), sessions.get(2).getTimeOfDay());
    }
}
