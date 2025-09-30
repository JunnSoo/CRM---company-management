package crm_app10.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app10.services.JobsServices;
import crm_app10.services.TaskServices;
import entity.Jobs;
import entity.Roles;
import entity.Tasks;
@WebServlet(name="jobController",urlPatterns = {"/job","/job-add","/job-edit","/job-delete","/job-detail"})
public class JobController extends HttpServlet {
	private JobsServices jobsServices = new JobsServices();
	private TaskServices taskServices = new TaskServices();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		
		try {
			switch (path) {
			case "/job": {
				List<Jobs> listJobs = jobsServices.getAllJobs();
				req.setAttribute("listJobs", listJobs);
				req.getRequestDispatcher("jobs.jsp").forward(req, resp);
				return;
			}
			case "/job-add":{
				req.getRequestDispatcher("job-add.jsp").forward(req, resp);
				return;
			}
			
			case "/job-edit": {
                String idStr = req.getParameter("id");
                if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=bad_id");
                    return;
                }
                Jobs job = jobsServices.getJobsById(id);
                if (job == null) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=not_found");
                    return;
                }
                req.setAttribute("job", job);
                req.getRequestDispatcher("job-edit.jsp").forward(req, resp);
                return;
            }
			
			case "/job-detail":{
				String idStr = req.getParameter("id");
                if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=missing_id");
                    return;
                }
                int job_id;
                try {
                    job_id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=bad_id");
                    return;
                }
                Jobs job = jobsServices.getJobsById(job_id);
                if (job == null) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=not_found");
                    return;
                }
                
                Map<String, Map<String, List<Tasks>>> buckets = taskServices.getGroupTaskByJobId(job_id);
                req.setAttribute("buckets", buckets);
                
                Map<String, Integer> percents = taskServices.getTaskPercentagesByJobId(job_id);
                req.setAttribute("percentTodo", percents.get("percentTodo"));
                req.setAttribute("percentInProgress", percents.get("percentInProgress"));
                req.setAttribute("percentDone", percents.get("percentDone"));
                
                req.getRequestDispatcher("job-detail.jsp").forward(req, resp);
				return;
			}
			
			case "/job-delete":{
				String idStr = req.getParameter("id");
                if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=bad_id");
                    return;
                }
                boolean ok = jobsServices.deleteById(id);
                resp.sendRedirect(req.getContextPath() + "/job?msg=" + (ok ? "deleted" : "delete_failed"));
                return;
			}
			default:
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println("Loi GET jobs: "+ e.getMessage());
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/jobs?msg=error");
		}
		
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		try {
			switch (path) {
			case "/job-add": {
				String name = req.getParameter("name");
				String start_date = req.getParameter("start_date");
				String end_date = req.getParameter("end_date");
				
				try {
					boolean ok  = name != null && !name.trim().isEmpty() 
							&& jobsServices.insertJobs(name.trim(), start_date.trim(), end_date.trim());
					
					resp.sendRedirect(req.getContextPath() + (ok
							? "/job-add?msg=created"
							: "/job-add?msg=failed"));
					return;
				} catch (Exception e) {
					resp.sendRedirect(req.getContextPath() + "/job-add?msg=error");
                    return;
				}
			}
			
			case "/job-edit":{
				String idStr = req.getParameter("id");
				String name = req.getParameter("name");
				String start_date = req.getParameter("start_date");
				String end_date = req.getParameter("end_date");
				
				if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/job?msg=bad_id");
                    return;
                }

                boolean ok = name != null && !name.trim().isEmpty()
                           && jobsServices.updateJobs(id, name, start_date, end_date);

                resp.sendRedirect(req.getContextPath() + (ok ? "/job?msg=updated"
                                                             : "/job-edit?id=" + id + "&msg=failed"));
                return;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + path);
			}
		} catch (Exception e) {
			System.out.println("Loi POST jobs: " + e.getMessage());
			e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/jobs?msg=error");
		}
	}
}

