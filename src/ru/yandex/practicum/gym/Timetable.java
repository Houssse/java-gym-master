package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetabl;

    public Timetable() {
        timetabl = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (timetabl.containsKey(trainingSession.getDayOfWeek())) {
            if (timetabl.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
                timetabl.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
            } else {
                timetabl.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), new ArrayList<>());
                timetabl.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
            }
        } else  {
            timetabl.put(trainingSession.getDayOfWeek(), new TreeMap<>());
            timetabl.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), new ArrayList<>());
            timetabl.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
        if (timetabl.containsKey(dayOfWeek)) {
            for (TimeOfDay timeOfDay : timetabl.get(dayOfWeek).keySet()) {
                trainingSessions.addAll(timetabl.get(dayOfWeek).get(timeOfDay));
            }
        }

        return trainingSessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetabl.containsKey(dayOfWeek)) {
            if (timetabl.get(dayOfWeek).containsKey(timeOfDay)) {
                return timetabl.get(dayOfWeek).get(timeOfDay);
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }
}
