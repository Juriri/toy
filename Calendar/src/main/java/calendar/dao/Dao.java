package calendar.dao;

import java.sql.Date;
import java.util.List;

import model.CalendarEvent;

public interface Dao {
	void insert(CalendarEvent cal);
	List<CalendarEvent> getAll();
	List<CalendarEvent> getAllBymonth(int year, int month);
	List<CalendarEvent> getAllBydate(Date date);
	CalendarEvent getById(int event_id);
	int makeNum(List<CalendarEvent> list);
	void deleteEvent(int event_id);
	void updateEvent(CalendarEvent event);
	
}
