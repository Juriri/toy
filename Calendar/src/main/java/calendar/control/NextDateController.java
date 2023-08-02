package calendar.control;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class NextDateController
 */
@WebServlet("/calendar/Next.do")
public class NextDateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NextDateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 	
		 	String action = request.getParameter("action");
		 	String path = "";
		 	Date nextDate = null;
		 	String json = null;
			/* 오늘 날짜 이동하기 위해 session에 저장된 이전 날짜 삭제 */
	        if (action.equals("now")) {
	        	 // Get today's date
	            java.util.Date today = new java.util.Date();
	            Date currentDate = new Date(today.getTime());

	            // Set "currentDate" attribute in the session
	            HttpSession session = request.getSession();
	            session.setAttribute("currentDate", currentDate);   
	        }
	        
	        else if (action.equals("previous") || action.equals("next") ) {
		        Date currentDate = Date.valueOf(request.getParameter("currentDate"));
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(currentDate);
	
		        int year = calendar.get(Calendar.YEAR);
		        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH는 0부터 시작하므로 1을 더해줍니다.
		        int day = calendar.get(Calendar.DAY_OF_MONTH);
		        
		        if (action.equals("previous")) {
		            // 이전 달 계산
		            if (month == 1) {
		                year = year - 1;
		                month = 12;
		            } else {
		                month = month - 1;
		            }
	
		        } else if (action.equals("next")) {
		            // 다음 달 계산
		            if (month == 12) {
		                year = year + 1;
		                month = 1;
		            } else {
		                month = month + 1;
		            }
		        }
	
		        // 가장 마지막 day 계산
		        int lastDay;
		        if (month == 2) {
		            // February has a special case, so handle it separately
		            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
		                // Leap year
		                lastDay = 29;
		            } else {
		                lastDay = 28;
		            }
		        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
		            // 30-day month
		            lastDay = 30;
		        } else {
		            // 31-day month
		            lastDay = 31;
		        }
	
		        // 날짜를 1부터 시작하도록 수정
		        day = 1;
		        // month, date가 두자리수가 아니면 0추가
		        nextDate = Date.valueOf(year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day));
		        // 전달 혹은 다음달 날짜 전달
		        HttpSession session = request.getSession();
		        session.setAttribute("currentDate", nextDate);
	        }
	        
			
	        path = request.getContextPath()+"/calendar/Cal";
			response.sendRedirect(path);
			
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}