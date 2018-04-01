package com.g_manager.utils;

import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 21.12.2017.
 */
@Component
public class ValidationManager {

    private final ResourceBundle resourceBundle;

    public ValidationManager(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ValidationResult checkName(Control control, String content) {
        Integer maxLength = 20;
        String bundleKey = "client.name";

        return validateStringField(content, control, maxLength, bundleKey);
    }

    public ValidationResult checkSurname(Control control, String content) {
        Integer maxLength = 25;
        String bundleKey = "client.surname";

        return validateStringField(content, control, maxLength, bundleKey);
    }

    public ValidationResult checkWorkHoursPerWeek(Control control, String content) {
        Integer maxValue = 168; // 168 hours in week
        String messageText = "";
        boolean condition = false;
        String bundleKey = "workHoursPerWeek";
        String pattern = "^[0-9]{1,3}(.[0-9]{2})?";

        if (content == null || content.trim().isEmpty()) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.field.fill");
            condition = true;
        } else if (!content.trim().matches(pattern) || Double.parseDouble(content) > maxValue) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.workHoursPerWeek.notCorrect");
            condition = true;
        }
        return ValidationResult.fromErrorIf(control, messageText, condition);
    }

    public ValidationResult checkPhone(Control control, String content) {
        String messageText = "";
        boolean condition = false;
        Integer maxLength = 15;
        String bundleKey = "phone";
        String pattern = "^\\(?[0-9]{3}\\)?[ -]?[0-9]{3}[ -]?[0-9]{2}[ -]?[0-9]{2}";

        if (content == null || content.trim().isEmpty()) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.field.fill");
            condition = true;
        } else if (content.length() > maxLength) {
            messageText = resourceBundle.getString(bundleKey) + ": " +
                    resourceBundle.getString("validate.field.longer") + " " + maxLength.toString() +
                    resourceBundle.getString("validate.characters");
            condition = true;
        } else if (!content.trim().matches(pattern)) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.phone.notcorrect");
            condition = true;
        }
        return ValidationResult.fromErrorIf(control, messageText, condition);
    }

    public ValidationResult checkEmail(Control control, String content) {
        String messageText = "";
        boolean condition = false;
        String bundleKey = "email";
        String pattern = "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";

        if (!content.trim().matches(pattern)) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.email.notcorrect");
            condition = true;
        }
        return ValidationResult.fromErrorIf(control, messageText, condition);
    }

    public ValidationResult checkRate(Control control, String content) {
        String messageText = "";
        boolean condition = false;
        Integer maxLength = 10;
        String bundleKey = "salary.rate";
        String pattern = "(\\d+([.,])?\\d{2})";
        if (content == null || content.trim().isEmpty()) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.field.fill");
            condition = true;
        } else if (content.length() > maxLength) {
            messageText = resourceBundle.getString(bundleKey) + ": " +
                    resourceBundle.getString("validate.field.longer") + " " + maxLength.toString() +
                    resourceBundle.getString("validate.characters");
            condition = true;
        } else if (!content.trim().matches(pattern)) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.rate.notcorrect");
            condition = true;
        }
        return ValidationResult.fromErrorIf(control, messageText, condition);
    }

    private ValidationResult validateStringField(String content, Control control, Integer maxLength, String bundleKey) {
        String messageText = "";
        boolean condition = false;

        if (content == null || content.trim().isEmpty()) {
            messageText = resourceBundle.getString(bundleKey) + ": " + resourceBundle.getString("validate.field.fill");
            condition = true;
        } else if (content.length() > maxLength) {
            messageText = resourceBundle.getString(bundleKey) + ": " +
                    resourceBundle.getString("validate.field.longer") + " " + maxLength.toString() +
                    resourceBundle.getString("validate.characters");
            condition = true;
        }
        return ValidationResult.fromErrorIf(control, messageText, condition);
    }
}
