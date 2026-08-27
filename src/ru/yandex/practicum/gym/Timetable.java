package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            if (timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
                timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
            } else {
                timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), new ArrayList<>());
                timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
            }
        } else {
            timetable.put(trainingSession.getDayOfWeek(), new TreeMap<>());
            timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), new ArrayList<>());
            timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
        if (timetable.containsKey(dayOfWeek)) {
            for (TimeOfDay timeOfDay : timetable.get(dayOfWeek).keySet()) {
                trainingSessions.addAll(timetable.get(dayOfWeek).get(timeOfDay));
            }
        }

        return trainingSessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek)) {
            if (timetable.get(dayOfWeek).containsKey(timeOfDay)) {
                return timetable.get(dayOfWeek).get(timeOfDay);
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> countByCoaches = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> treeMap : timetable.values()) {
            for (List<TrainingSession> trainings : treeMap.values()) {
                for (TrainingSession trainingSession : trainings) {
                    Coach coach = trainingSession.getCoach();
                    countByCoaches.put(coach, countByCoaches.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : countByCoaches.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(result, new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings a, CounterOfTrainings b) {
                return Integer.compare(b.getCount(), a.getCount());
            }
        });

        return result;
    }
}
