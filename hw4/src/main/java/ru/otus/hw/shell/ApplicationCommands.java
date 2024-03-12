package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.security.LoginContext;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final LoginContext loginContext;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Anonymous User") String userName) {
        loginContext.login(userName);
        return String.format("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Start test command", key = {"t", "test", "runtest"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String runTest() {
        return "Test started";
    }

    private Availability isPublishEventCommandAvailable() {
        return loginContext.isUserLoggedIn()
                ? Availability.available()
                : Availability.unavailable("Please enter your name and last name");
    }
}
