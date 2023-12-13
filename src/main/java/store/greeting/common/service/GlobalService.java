package store.greeting.common.service;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import java.util.Map;

public interface GlobalService {


    Map<String, String> validateHandling(Errors errors);

    void messageHandling(Errors errors, Model model);

}
