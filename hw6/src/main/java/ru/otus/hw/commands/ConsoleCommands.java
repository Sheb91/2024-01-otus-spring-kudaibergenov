package ru.otus.hw.commands;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
public class ConsoleCommands {

    @ShellMethod(value = "Run h2-console", key = "h2-console")
    public void runH2Console() throws SQLException {
        Console.main();
    }
}
