package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Training;
import com.elife.MiniProject.dao.enums.TrainingStatus;

import java.util.List;

public interface TrainingService {
    Training getTrainingById(Long id);
    List<Training> getAllTrainings();
    Training saveTraining(Training training);
    void deleteTraining(Long id);
    Training updateTraining(Long id, Training training);
    void activateTraining(Long trainingId);
    void deactivateTraining(Long trainingId);

}
