package crm_app10.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app10.services.JobsServices;
import crm_app10.services.StatusServices;
import crm_app10.services.TaskServices;
import crm_app10.services.UserServices;
import entity.Tasks;
import entity.Users;

@WebServlet(name="taskController", urlPatterns = {"/task","/task-add","/task-edit","/task-delete"})
public class TaskController extends HttpServlet{
	private JobsServices jobsServices = new JobsServices();
	private UserServices userServices = new UserServices();
	private TaskServices taskServices = new TaskServices();
	private StatusServices statusServices = new StatusServices();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		try {
			switch (path) {
			case "/task": {
				List<Tasks> listTask = taskServices.getAllTask();
				req.setAttribute("listTask", listTask);
				req.getRequestDispatcher("task.jsp").forward(req, resp);
				return;
			}
			case "/task-add":{
				req.setAttribute("users",userServices.getAllUser());
				req.setAttribute("jobs", jobsServices.getAllJobs());
				req.getRequestDispatcher("task-add.jsp").forward(req, resp);
				return;
			}
			case "/task-edit":{
				String idStr = req.getParameter("id");
                if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/task?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/task?msg=bad_id");
                    return;
                }
                Tasks task = taskServices.getTaskById(id);
                if (task == null) {
                    resp.sendRedirect(req.getContextPath() + "/task?msg=not_found");
                    return;
                }
                req.setAttribute("users",userServices.getAllUser());
				req.setAttribute("jobs", jobsServices.getAllJobs());
				req.setAttribute("status", statusServices.getAllStatus());
                req.setAttribute("task", task);
                req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
                return;
			}
			case "/task-delete":{
				String idStr = req.getParameter("id");
				if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/task?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/task?msg=bad_id");
                    return;
                }
                boolean ok = taskServices.deleteById(id);
                resp.sendRedirect(req.getContextPath() + "/task?msg=" + (ok ? "deleted" : "delete_failed"));
                return;
			}
			default:
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi GET task: "+e.getMessage());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		
		try {
			switch(path) {
			case "/task-add":{
				String name = req.getParameter("name");
				String start_date = req.getParameter("start_date");
				String end_date = req.getParameter("end_date");
				String userIdStr = req.getParameter("user_id");
				String jobIdStr = req.getParameter("job_id");
				
				//đang vội nên gán thẳng luôn nhưng khi bên ngoài cần một bước try-catch để xem id có hợp lệ để parse không
				int user_id = Integer.parseInt(userIdStr);
				int job_id = Integer.parseInt(jobIdStr);
				
				try {
					boolean ok = taskServices.insertTask(name, start_date, end_date, user_id, job_id,1);
					resp.sendRedirect(req.getContextPath()+ (ok ? "/task-add?msg=created" : "/task-add?msg=failed"));
					return;
				} catch (Exception e) {
					e.printStackTrace();
					resp.sendRedirect(req.getContextPath() + "/task-add?msg=error");
                    return;
				}
			}
			case "/task-edit":{
				String idStr = req.getParameter("id");
				String name = req.getParameter("name");
				String start_date = req.getParameter("start_date");
				String end_date = req.getParameter("end_date");
				String userIdStr = req.getParameter("user_id");
				String jobIdStr = req.getParameter("job_id");
				String statusIdStr = req.getParameter("status_id");
				if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/task?msg=missing_id");
                    return;
                }
                int id, user_id, job_id, status_id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/task?msg=bad_id");
                    return;
                }
                user_id = Integer.parseInt(userIdStr);
                job_id = Integer.parseInt(jobIdStr);
                status_id = Integer.parseInt(statusIdStr);

                boolean ok = taskServices.updateTaskByID(id, name, start_date, end_date, user_id, job_id, status_id);
                resp.sendRedirect(req.getContextPath() + (ok ? "/task?msg=updated"
                                                             : "/task-edit?id=" + id + "&msg=failed"));
                return;
			}
			
			
			default:
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println("Loi POST task: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
