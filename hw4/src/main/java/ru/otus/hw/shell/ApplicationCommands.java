package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.security.LoginContext;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final LoginContext loginContext;

    private final TestRunnerService testRunnerService;

    private final StudentService studentService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login() {
        var student = studentService.determineCurrentStudent();
        loginContext.login(student);
        return String.format("Добро пожаловать: %s", student.getFullName());
    }

    @ShellMethod(value = "Start test command", key = {"t", "test", "runtest"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void runTest() {
        testRunnerService.run(loginContext.getStudent());
    }

    private Availability isPublishEventCommandAvailable() {
        return loginContext.studentHasLastNameAndFirstName()
                ? Availability.available()
                : Availability.unavailable("Please enter your name and last name");
    }
}
