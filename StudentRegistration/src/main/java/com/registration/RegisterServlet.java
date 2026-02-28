package com.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private String url = "jdbc:postgresql://dpg-d6ha220gjchc73ckcs60-a.oregon-postgres.render.com/student_db_dyxk?sslmode=require";
    private String user = "student_db_dyxk_user";
    private String password = "qSwdpwdheERm6JQg7SWfUV0LUMWGHJHO";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String course = request.getParameter("course");

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            String createTable = "CREATE TABLE IF NOT EXISTS students (id SERIAL PRIMARY KEY, name VARCHAR(100), email VARCHAR(100), phone VARCHAR(20), course VARCHAR(100))";
            con.createStatement().execute(createTable);

            PreparedStatement ps = con.prepareStatement("INSERT INTO students(name, email, phone, course) VALUES(?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, course);

            int rowCount = ps.executeUpdate();

            out.println("<html><head><style>");
            out.println("body { font-family: 'Segoe UI', sans-serif; background: #f4f7f6; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.println(".res-card { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); text-align: center; width: 400px; }");
            out.println("h1 { color: #2a5298; margin-bottom: 10px; }");
            out.println("p { color: #555; margin-bottom: 25px; }");
            out.println(".link-box { display: flex; justify-content: center; gap: 15px; }");
            out.println("a { text-decoration: none; color: #ff4b2b; font-weight: 600; }");
            out.println("a:hover { color: #1e3c72; }");
            out.println("</style></head><body>");
            out.println("<div class='res-card'>");

            if (rowCount > 0) {
                out.println("<h1>Registration Success</h1>");
                out.println("<p>Student " + name + " has been registered.</p>");
            } else {
                out.println("<h1 style='color:red;'>Registration Failed</h1>");
                out.println("<p>Could not save data to the cloud.</p>");
            }

            out.println("<div class='link-box'>");
            out.println("<a href='index.html'>Add New</a>");
            out.println("<a href='home.html'>Home</a>");
            out.println("</div></div></body></html>");

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}