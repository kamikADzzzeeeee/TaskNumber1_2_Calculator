package ru.yamshikov.servlet.calculator;

import java.io.*;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.yamshikov.servlet.calculator.model.Error;
import ru.yamshikov.servlet.calculator.model.Example;
import ru.yamshikov.servlet.calculator.model.Result;

@WebServlet(name = "helloServlet", value = "/calculator")
public class CalculatorServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();                             //Получаем PrintWriter для установки ответа
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Result result = null;
        try{
            Example example = gson.fromJson(reader, Example.class);
            Double x = Double.valueOf(example.getX());
            Double y = Double.valueOf(example.getY());
            double r;
            switch (example.getOperation()){
                case "+" :
                    r = x+y;
                    result = new Result(Double.toString(r));
                    break;
                case "-" :
                    r = x - y;
                    result = new Result(Double.toString(r));
                    break;
                case "*" :
                    r = x*y;
                    result = new Result(Double.toString(r));
                    break;
                case "/" :
                    r = x/y;
                    result = new Result(Double.toString(r));
                    break;
                default :
                    Error error = Error
                            .builder()
                            .code(HttpServletResponse.SC_BAD_REQUEST)
                            .message("Неверный формат математической операции")
                            .time(LocalDateTime.now().toString())
                            .build();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println(gson.toJson(error));
                    return;
            }

        } catch (ArithmeticException exception){
            Error error = Error
                    .builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message("Деление на ноль")
                    .time(LocalDateTime.now().toString())
                    .build();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println(gson.toJson(error));
            return;
        } catch (JsonSyntaxException | JsonIOException exception){
            Error error = Error
                    .builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message("Неверный формат Json'a")
                    .time(LocalDateTime.now().toString())
                    .build();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println(gson.toJson(error));
            return;
        }

        out.println(gson.toJson(result));

    }

}