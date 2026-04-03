package in.co.rays.proj4.controller;

/**
 * ORSView defines application-wide constants for JSP view paths and their
 * corresponding controller URL mappings. These constants are used throughout
 * the project to avoid hard-coded strings in controllers and JSPs.
 *
 * <p>
 * - {@code APP_CONTEXT} is the application context root. <br>
 * - {@code PAGE_FOLDER} is the base folder where JSP pages are located. <br>
 * - Other constants map logical view names (JSP paths) and controller URL
 * patterns used by servlets.
 * </p>
 *
 * @author Rohit Rajpoot
 * @version 1.0
 */
public interface ORSView {

	/** Application context root. */
	public String APP_CONTEXT = "/ORSProject-04";

	/** Base JSP folder. */
	public String PAGE_FOLDER = "/jsp";

	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	
	public String DEVICE_VIEW = PAGE_FOLDER + "/DeviceView.jsp";
	public String DEVICE_CTL = APP_CONTEXT + "/ctl/DeviceCtl";

	public String DEVICE_LIST_VIEW = PAGE_FOLDER + "/DeviceListView.jsp";
	public String DEVICE_LIST_CTL = APP_CONTEXT + "/ctl/DeviceListCtl";


	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimetableView.jsp";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimetableCtl";

	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimetableListView.jsp";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimetableListCtl";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";

	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String JAVA_DOC = "/ORSProject-04/doc/index.html";

	public String PATIENT_VIEW = PAGE_FOLDER + "/PatientView.jsp";
	public String PATIENT_CTL = APP_CONTEXT + "/ctl/PatientCtl";

	public String PATIENT_LIST_VIEW = PAGE_FOLDER + "/PatientListView.jsp";
	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientListCtl";
	
	public String CERTIFICATE_VIEW = PAGE_FOLDER + "/CertificateView.jsp";
	public String CERTIFICATE_CTL = APP_CONTEXT + "/ctl/CertificateCtl";

	public String CERTIFICATE_LIST_VIEW = PAGE_FOLDER + "/CertificateListView.jsp";
	public String CERTIFICATE_LIST_CTL = APP_CONTEXT + "/ctl/CertificateListCtl";
	
	public String CONTACT_VIEW = PAGE_FOLDER + "/ContactView.jsp";
	public String CONTACT_CTL = APP_CONTEXT + "/ctl/ContactCtl";

	public String CONTACT_LIST_VIEW = PAGE_FOLDER + "/ContactListView.jsp";
	public String CONTACT_LIST_CTL = APP_CONTEXT + "/ctl/ContactListCtl";
	
	public String PRIORITY_VIEW = PAGE_FOLDER + "/PriorityView.jsp";
	public String PRIORITY_CTL = APP_CONTEXT + "/ctl/PriorityCtl";

	public String PRIORITY_LIST_VIEW = PAGE_FOLDER + "/PriorityListView.jsp";
	public String PRIORITY_LIST_CTL = APP_CONTEXT + "/ctl/PriorityListCtl";
	
	public String TIMESLOT_VIEW = PAGE_FOLDER + "/TimeSlotView.jsp";
	public String TIMESLOT_CTL = APP_CONTEXT + "/ctl/TimeSlotCtl";

	public String TIMESLOT_LIST_VIEW = PAGE_FOLDER + "/TimeSlotListView.jsp";
	public String TIMESLOT_LIST_CTL = APP_CONTEXT + "/ctl/TimeSlotListCtl";
	
	public String REVIEW_VIEW = PAGE_FOLDER + "/ReviewView.jsp";
	public String REVIEW_CTL = APP_CONTEXT + "/ctl/ReviewCtl";

	public String REVIEW_LIST_VIEW = PAGE_FOLDER + "/ReviewListView.jsp";
	public String REVIEW_LIST_CTL = APP_CONTEXT + "/ctl/ReviewListCtl";
	
	
	
	

	


}
