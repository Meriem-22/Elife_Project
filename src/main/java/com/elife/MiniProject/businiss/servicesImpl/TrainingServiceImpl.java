package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.TrainingService;
import com.elife.MiniProject.dao.entities.Training;
import com.elife.MiniProject.dao.enums.TrainingStatus;
import com.elife.MiniProject.dao.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Training getTrainingById(Long id) {
        Optional<Training> training = trainingRepository.findById(id);
        return training.orElse(null);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        if (trainingRepository.existsById(id)) {
            training.setId(id);
            return trainingRepository.save(training);
        }
        return null;
    }

    @Override
    public void activateTraining(Long id) {
        Optional<Training> trainingOpt = trainingRepository.findById(id);
        if (trainingOpt.isPresent()) {
            Training training = trainingOpt.get();
            training.setStatus(TrainingStatus.ACTIVATED);
            trainingRepository.save(training);
        }
    }

    @Override
    public void deactivateTraining(Long id) {
        Optional<Training> trainingOpt = trainingRepository.findById(id);
        if (trainingOpt.isPresent()) {
            Training training = trainingOpt.get();
            training.setStatus(TrainingStatus.DEACTIVATED);
            trainingRepository.save(training);
        }
    }

    
}
