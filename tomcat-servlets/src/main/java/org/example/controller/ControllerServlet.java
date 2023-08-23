package org.example.controller;

import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    public ControllerServlet() {
        new JdbcStudentRepository().createTableIfNotExists();
        commands.put("add-point", new AddPointsCommand());
        commands.put("students", new StartPageCommand());
        commands.put("average", new AverageMarkCommand());
        commands.put("create-pair", new ChoosePairCommand());
        commands.put("delete-student", new DeleteStudentCommand());
        commands.put("stat", new FullStatisticCommand());
        commands.put("steal-point", new StealPointCommand());
        commands.put("save-changes", new SaveChangesCommand());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandKey = request.getParameter("command");
        Command command = commands.get(commandKey);

        if (command != null) {
            command.execute(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


}
